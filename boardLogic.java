import java.awt.event.*;
import java.awt.*;

public class boardLogic{

	protected String[] board;
	protected String selected;  
	protected int p1Score;
	protected int p2Score;
	protected scrabbleAI ai;
	//add scores for p1 and p2
	
	public boardLogic() {
		board = new String[100];
		selected = null;
		ai = new scrabbleAI(this);
		p1Score = 0;
		p2Score = 0;
	}

	public void placeTile(tile t){
		t.setLabel(selected);
		board[t.index] = selected;
		selected = null;
	}

	public void selectTile(playTile t) {
		selected = t.letterVal;
	}
}




