import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class OvalButton extends JButton {

    Border thickBorder = new LineBorder(Color.BLACK, 6);
	
	public OvalButton(String text){
		super.setText(text);
		//super(label);
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setOpaque(false);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//super.paintComponent(g);
		//super.paint(g);
		//g.drawRect(0, 0, getWidth(), getHeight());
		//System.out.println("PAINTING");
		//g2.setBackground(new Color(20, 200, 10, 200));
		//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		//g.fillRect(0, 0, getWidth(), getHeight());
		//g.setColor(Color.BLACK);
		
		
		/**
		 if (!isOpaque() && getBackground().getAlpha() < 255) {
             
             
             System.out.println("DOING THIS");
         }
         **/
		//g2.setColor(getBackground());
		g2.setColor(Color.red.brighter());
        g2.fillOval(0, 0, getWidth(), getHeight());
		 //super.setBorderPainted(true);
         super.setBorder(thickBorder);
         super.paintComponent(g);
         
		
		
		
	}
	

}
