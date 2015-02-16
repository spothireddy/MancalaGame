import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.*;
public class MancalaBoard extends JFrame implements ChangeListener {
  MancalaModel model;
	JButton[][] view;
	JButton left,right;
	JPanel panel;
	JLabel leftScore,rightScore;
	ButtonFormat format;
	ActionListener listener;
	ActionListener topListener, bottomListener;
	JButton undoButton;
	/**
	 * @param args
	 */
	public MancalaBoard(final MancalaModel model, ButtonFormat format) {
		this.format = format;
		this.model = model;
		model.attach(this);
		undoButton = new JButton("Undo");
		this.panel = new JPanel(new GridLayout(2,6));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,650);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		view = new JButton[2][6];
		JButton left = format.getButtonShape("Player 1");
		//left = format.getButtonShape("player1");
		left.setPreferredSize(new Dimension(175, 650));
		leftScore = new JLabel("Score: " + model.getMancalas()[1] + " ");
		rightScore = new JLabel("Score: " + model.getMancalas()[0] + " ");
		left.add(leftScore);
		topListener = new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getSource());
				System.out.println( e.getActionCommand());
				String s = e.getActionCommand();
				System.out.println("Moving from TOP: " + s);
				model.move(1, Integer.parseInt(s));
			}
			
		};
		bottomListener = new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getSource());
				System.out.println( e.getActionCommand());
				String s = e.getActionCommand();
				System.out.println("Moving from BOTTOM: " + s);
				model.move(0,Integer.parseInt(s));
			}
			
		};
		
		right = format.getButtonShape("Player 0");
		//right = format.getButtonShape("Player 0");
		right.setPreferredSize(new Dimension(175, 650));
		right.add(rightScore);
		drawPits();
		
		undoButton.addActionListener(new ActionListener(){


			public void actionPerformed(ActionEvent event) {
				model.undo();
				
			}
			
		});
		this.add(left,BorderLayout.WEST);
		this.add(right,BorderLayout.EAST);
		this.add(undoButton, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		updatePits();
		System.out.println("Player 0 score: " + model.getMancalas()[0]);
		System.out.println("Player 1 score: " + model.getMancalas()[1]);
		leftScore.setText( Integer.toString(model.getMancalas()[1]));
		rightScore.setText( Integer.toString(model.getMancalas()[0]));
		System.out.println("It is now player: " + model.getPlayer() + "'s turn");
		
	}
	private void drawPits() {
		for(int i = 1; i >= 0; i--) {
			if(i == 0) {
				for(int j = 0; j < 6; j++) {
					String num1 = Integer.toString(i);
					num1 += "," + Integer.toString(j);
					int val = model.getPits()[i][j];
					num1 += ": " + Integer.toString(val);
					//view[i][j] = new JButton(num1);
					view[i][j] = format.getButtonShape(num1);
					view[i][j].setActionCommand(Integer.toString(j));
					view[i][j].addActionListener(bottomListener);
					panel.add(view[i][j]);
				}
			}
			else if (i==1) {	//top
				for(int j = 5; j >= 0; j--) {
					String num1 = Integer.toString(i);
					num1 += "," + Integer.toString(j);
					int val = model.getPits()[i][j];
					num1 += ": " + Integer.toString(val);
					view[i][j] = format.getButtonShape(num1);
					//view[i][j] = new JButton(num1);
					view[i][j].setActionCommand(Integer.toString(j));
					view[i][j].addActionListener(topListener);
					panel.add(view[i][j]);
				}
			}
		}
	}
	private void updatePits() {
		for(int i = 1; i >= 0; i--) {
			if(i == 0) {
				for(int j = 0; j < 6; j++) {
					String num1 = Integer.toString(i);
					num1 += "," + Integer.toString(j);
					int val = model.getPits()[i][j];
					num1 += ": " + Integer.toString(val);
					//view[i][j] = format.getButtonShape(num1);
					view[i][j].setText(num1);
					System.out.println("i=0: j=" + j + " count: " + val);
				}
			}
			else if (i==1) {	//top
				for(int j = 5; j >= 0; j--) {
					String num1 = Integer.toString(i);
					num1 += "," + Integer.toString(j);
					int val = model.getPits()[i][j];
					num1 += ": " + Integer.toString(val);
					//view[i][j] = format.getButtonShape(num1);	
					view[i][j].setText(num1);
					System.out.println("i=1: j=" + j + " count: " + val);
				}
			}
		}
	}
}