import java.awt.*;

public class playTile extends Button 
{

	protected int numVal;
	protected String letterVal;

	public playTile(String s) {
		letterVal = s;
		numVal = 0;
		this.setLabel(s);
		this.setBackground(Color.black);
		this.setForeground(Color.white);
	}

}


