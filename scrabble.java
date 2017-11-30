//Scrabbe.java 
//
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class scrabble extends Applet  
{
	boardCanvas board;
	playerTileCanvas board;
	directionsCanvas directions;

	protected boardLogic logic;


	public void init() {
		setFont(new Font("TimesRoman", Font.BOLD, 12));
		logic = new boardLogic(this, 225);
		setSize(800,1000);
		setLayout(new BorderLayout());
		board = new boardCanvas(logic);
		board.addMouseListener(board);
		add("Center", board);
		playerTiles = new playerTileCanvas(logic);
		playerTiles.addMouseListener(playerTiles);
		add("South", playerTiles);
		directions = new directionsCanvas(logic);
		directions.addMouseListener(directions);
		add("North", directions);
		logic.play();
	}

}








