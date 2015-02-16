import javax.swing.JButton;


public class RectangularFormat implements ButtonFormat{


	public JButton getButtonShape(String text) {
		return new RectangularButton(text);
	}




}
