//Scrabbe.java 
//
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class scrabble extends Applet  
{
	protected boardLogic logic;

	public void init() {
		setFont(new Font("TimesRoman", Font.BOLD, 10));
		logic = new boardLogic();
		setLayout(new BorderLayout());
		add("Center", createBoard());
		setSize(600,600);
		Button b = new Button("test");
		add("North", b);
		add("South", createTiles());
	}

	protected Panel createTiles() {
		//create playable tiles at the bottom of the screen
		Panel tiles = new Panel();
		tiles.setLayout(new FlowLayout());
		for (int i=0;i<7;i++) {
			playTile t = new playTile("A");
			playTileListener listener = new playTileListener(t, this.logic);
			t.addActionListener(listener);
			tiles.add(t);
		}
		return tiles;
	}

	protected Panel createBoard() {
		//create board of 10x10 tiles
		Panel board = new Panel();
		board.setLayout(new GridLayout(10,10));
		for (int i=0;i<100;i++) {
			tile t = new tile();
			t.index = i;
			tileListener listener = new tileListener(t, logic);
			t.addActionListener(listener);
			board.add(t);
		}
		return board;
	}
}








