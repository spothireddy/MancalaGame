import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
public class MancalaTester {
  /**
	 * @param args
	 */
	public static void main(String[] args) {
		

		String[] options = new String[] {"1", "3", "4"};
	    int stonesNum = JOptionPane.showOptionDialog(null, "Choose the number of stones for each pit: ", "Number of Stones", 
	        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	    int numOfStones = Integer.parseInt(options[stonesNum]);
	    
	    ButtonFormat[] formats = new ButtonFormat[] {new RectangularFormat(), new OvalFormat()};
	    String[] nameFormats = new String[]{"Rectangular Format", "Oval Format"};
	    int formatNum = JOptionPane.showOptionDialog(null, "Choose the Layout", "Layout", 
		        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		        null, nameFormats, nameFormats[0]);
		
		MancalaModel mm = new MancalaModel();
		mm.setNumberOfStones(numOfStones);
		//mm.move(0, 3);
		//mm.move(1, 2);
		///mm.move(0, 4);
		//mm.move(0, 5);
		//mm.move(1, 1);
		MancalaBoard mb = new MancalaBoard(mm, formats[formatNum]);
		mb.setVisible(true);
	}

}