import java.awt.event.*;

public class playTileListener implements ActionListener {

	protected playTile t;
	protected boardLogic logic;

	public playTileListener(playTile t, boardLogic logic) {
		this.t = t;
		this.logic = logic;
	}
	public void actionPerformed(ActionEvent e) {
		logic.selectTile(t);
	}
}
