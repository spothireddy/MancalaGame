import javax.swing.JButton;


public class OvalFormat implements ButtonFormat{

    
	

	public JButton getButtonShape(String text) {
		return new OvalButton(text);
	}

}
