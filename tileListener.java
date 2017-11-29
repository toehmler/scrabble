import java.awt.event.*;

public class tileListener implements ActionListener 
{
	protected tile t;
	protected boardLogic logic;
	
	public tileListener(tile t, boardLogic logic){
		this.t = t;
		this.logic = logic;
	}

	public void actionPerformed(ActionEvent e){
		logic.placeTile(t);
	}

}

