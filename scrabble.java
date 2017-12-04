//Scrabble.java

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class scrabble extends Applet {
	
	private static final long serialVersionUID = 1L; 

	public boardCanvas boardDisplay;
	public playerTileCanvas playerTileDisplay;
	public directionsCanvas directionsDisplay;
	public scoreCanvas scoreDisplay;

//	protected boardLogic logic;

	public void init() {

		//set various display properties
		setFont(new Font("TimesRoman", Font.BOLD, 12));
		setSize(496,660);

		//init a new logic object
//		logic = new boardLogic(this);
		
		//init canvas objects
		boardDisplay = new boardCanvas();
		playerTileDisplay = new playerTileCanvas();
		directionsDisplay = new directionsCanvas();
		scoreDisplay = new scoreCanvas();
		//add mouse listeners
		
		boardDisplay.addMouseListener(boardDisplay);
		playerTileDisplay.addMouseListener(playerTileDisplay);
		directionsDisplay.addMouseListener(directionsDisplay);
		

		//put the players tiles and directions into panel
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add("North", directionsDisplay);
		buttonPanel.add("South", playerTileDisplay);

		//add the canvases and panel to the applet
		//add("Center", boardDisplay);
		//add("South", playerTileDisplay);
		//add("South", buttonPanel);

		scoreDisplay.setSize(0,50);
		playerTileDisplay.setSize(0,62);
		directionsDisplay.setSize(0,62);
		setLayout(new BorderLayout());
		add("Center", boardDisplay);
		add("South", buttonPanel);
		add("North", scoreDisplay);


	}
}







