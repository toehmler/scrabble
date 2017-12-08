//scrabbleCanvas.java

import java.awt.*;
import java.awt.event.*;

public class scrabbleCanvas extends Canvas implements MouseListener {

	protected int scoreX;
	protected int p1X;
	protected int cmpX;
	protected int cwsX;
	protected int plX;
	protected int scoreHeight;
	
	protected int boardWidth;
	protected boardLogic logic; 
	protected int boardTileSize;
	protected int numTiles;
	protected int numPerRow;
	protected int borderWidth;
	protected int boardHeight;

	protected int playerTileSize;
	boolean [] clicked = {false, false, false, false, false, false, false, false};
	boolean submitClicked; 
	protected int playerTileHeight;



	protected int directionsX;
	protected int shuffleX;
	protected int skipX;
	protected int exchangeX;
	boolean shuffleClicked; 
	boolean skipClicked; 
	boolean exchangeClicked; 
	protected int directionsHeight;



	

	public static final Color LIGHTyellow = new Color(255, 255, 153);
	public static final Color LIGHTred = new Color(255,102,102);
	public static final Color DARKgreen = new Color(0, 102, 0);
	public static final Color LIGHTblue = new Color(0, 204, 255);
	public static final Color DARKblue = new Color(0, 75, 205); 
	public static final Color DARKred = new Color(153, 0, 0); 
	public static final Color DARKyellow = new Color(255, 204, 0); 
	public static final Color DARKERyellow = new Color(153, 102, 0); 
	public static final Color GREEN = new Color(0, 180, 0);




	public scrabbleCanvas(boardLogic logic) {
		this.scoreX = 15;
		this.p1X = 70;
		this.cmpX = 150;
		this.cwsX = 240;
		this.plX = 390;
		this.scoreHeight = 50;
		this.boardWidth = 496;
		this.logic = logic; 
		this.boardTileSize = 30;
		this.numTiles = 225;
		this.numPerRow = 15;
		this.borderWidth = 23; 
		this.boardHeight = 496;
		this.playerTileSize = 62;
		this.submitClicked = false;
		this.directionsX = 3;
		this.shuffleX = 270;
		this.skipX = 345;
		this.exchangeX = 428;
		shuffleClicked = false; 
		skipClicked = false; 
		exchangeClicked = false; 
		this.directionsHeight = 60;
		this.playerTileHeight = 62;

	}

	public void paint(Graphics g) {

		// ---------- painting score info -------------
		
		g.setFont(new Font("Impact", Font.BOLD, 12));
		g.setColor(LIGHTyellow); 
		g.fillRect(0,0,boardWidth,scoreHeight);
		g.setColor(Color.black);
		g.drawRect(0,0,boardWidth,scoreHeight);
		g.drawString("SCORE", scoreX, scoreHeight-20);
		g.drawRoundRect(p1X + 47, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(p1X + 47, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Player 1      "+String.valueOf(logic.p1score), p1X, scoreHeight-20);
		g.drawRoundRect(cmpX + 58, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(cmpX + 58, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Computer      "+String.valueOf(logic.p2score), cmpX, scoreHeight-20);
		g.drawRoundRect(cwsX + 100, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(cwsX + 100, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Current Word Score    0", cwsX, scoreHeight-20);
		g.drawRoundRect(plX + 55, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(plX + 55, scoreHeight-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Pieces left   0", plX, scoreHeight-20);
		g.drawRect(0, 0, 235, 50);


		// ---------- painting the board ---------------

		g.drawRect(0, scoreHeight, 496, 600);
		for (int i=0;i<numTiles;i++) {
			//draw a grid of 100 boxes 
			int x = borderWidth + ((i%15)*boardTileSize);
			int y = borderWidth + ((i/numPerRow)*boardTileSize) + scoreHeight;
			if(i == 112) {
				g.setColor(LIGHTred);
				g.fillRect(x, y, boardTileSize, boardTileSize);
				g.setColor(Color.black);
				g.drawRect(x, y, boardTileSize, boardTileSize);
			}else {
			g.setColor(LIGHTyellow);
			g.fillRect(x, y, boardTileSize, boardTileSize);
			g.setColor(Color.black);
			g.drawRect(x, y, boardTileSize, boardTileSize);
			}
			if (logic.board[i].letterVal != "") 
				g.drawString(logic.board[i].letterVal, x+13, y+20);
		}

		// ------- painting first row of buttons ----------
			
			g.setFont(new Font("Impact", Font.BOLD, 12));
			g.setColor(LIGHTyellow);
			g.fillRoundRect(0,scoreHeight+boardHeight,248,62,10,10);
			g.setColor(Color.black);
			g.drawRoundRect(0,scoreHeight+boardHeight,248,62,10,10);
			if(shuffleClicked) 
				g.setColor(DARKERyellow);
			else 
				g.setColor(DARKyellow);
			g.fillRoundRect(248,scoreHeight+boardHeight,82,62,10,10);
			g.setColor(Color.black);
			g.drawRoundRect(248,scoreHeight+boardHeight,82,62,10,10);
			if(skipClicked) 
				g.setColor(DARKblue);
			else 
				g.setColor(LIGHTblue);
			g.fillRoundRect(330,scoreHeight+boardHeight,83,62,10,10);
			g.setColor(Color.black);
			g.drawRoundRect(330,scoreHeight+boardHeight,83,62,10,10);
			if(exchangeClicked) 
				g.setColor(DARKred);
			else 
				g.setColor(LIGHTred);
			g.fillRoundRect(413,scoreHeight+boardHeight,83,62,10,10);
			g.setColor(Color.BLACK);
			g.drawRoundRect(413,scoreHeight+boardHeight,83,62,10,10);
			g.drawString("Shuffle", shuffleX, 35+scoreHeight+boardHeight);
			g.drawString("Skip Turn", skipX, 35+scoreHeight+boardHeight);
			g.drawString("Exchange", exchangeX, 35+scoreHeight+boardHeight);
			g.setFont(new Font("Impact", Font.PLAIN, 10));
			g.drawString(logic.strDir, directionsX, 35+scoreHeight+boardHeight);
		



		// ------- painting second row of buttons ---------

		for (int i=0;i<7;i++) {	
			int x = playerTileSize*i;
			g.setColor(Color.black);
			g.drawRoundRect(x,scoreHeight+boardHeight+directionsHeight+3,playerTileSize,playerTileSize, 10, 10);
			if(clicked[i]) {
				g.setColor(Color.LIGHT_GRAY);
			}else {
				g.setColor(Color.WHITE);
			}
			g.fillRoundRect(x, scoreHeight+boardHeight+directionsHeight+3, playerTileSize, playerTileSize, 10, 10);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(logic.p1tiles[i].letterVal, x + 25, 40+scoreHeight+boardHeight+directionsHeight);
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.drawString(String.valueOf(logic.p1tiles[i].numVal), x + 55, 10+scoreHeight+boardHeight+directionsHeight);
		}
		g.drawRoundRect((7*playerTileSize),scoreHeight+boardHeight+directionsHeight+3, playerTileSize, playerTileSize, 10, 10);
		if(submitClicked)
			g.setColor(DARKgreen);
		else
			g.setColor(GREEN);
		g.fillRoundRect((7*playerTileSize),scoreHeight+boardHeight+directionsHeight+3, playerTileSize, playerTileSize, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Impact", Font.BOLD, 10));
		g.drawString("Submit Move!", (7*playerTileSize) + 2, 35+scoreHeight+boardHeight+directionsHeight+3);
	}

	public void mousePressed(MouseEvent event) {
		//handle events for when board is pressed
		Point p = event.getPoint();
		System.out.println(String.valueOf(p.y));
		if (p.y < scoreHeight) { 
		} else if (p.y > scoreHeight && p.y < scoreHeight+boardHeight) {

			int x = p.x - borderWidth;
			int y = p.y - (borderWidth+scoreHeight);
			if (x>=0 && x<(numPerRow*boardTileSize) && y>= 0 && y<((numTiles/numPerRow)*boardTileSize)) {
				int rowNum = (y/boardTileSize); //starting at row 0
				int colNum = (x/boardTileSize); //starting at row 0
				int tileIndex = (15*rowNum) + colNum; //indicies range from 0-224
				logic.placeTile(tileIndex);
				repaint();
		//		logic.placeTile(tileIndex);
		//		g.drawString(logic.board[tileIndex].letterVal, x, y);
			}
		} else if (p.y > scoreHeight + boardHeight && p.y < scoreHeight+boardHeight+directionsHeight) {
			int y = p.y - (boardHeight+scoreHeight);
			if (p.x>=directionsX && p.x<shuffleX && y>=0 && y<directionsHeight) {
				//System.out.println("logic.play()");
				if (logic.gameInProgress == false) {
					logic.play();
					repaint();
				}
			} else if (p.x>=shuffleX && p.x<skipX && y>=0 && y<directionsHeight) {
				System.out.println("logic.shuffle()");
				shuffleClicked = true; 
				repaint(); 
			} else if (p.x>=skipX && p.x<exchangeX && y>=0 && y<directionsHeight) {
				System.out.println("logic.skip()");
				skipClicked = true;
			//	repaint(); 
			}else if (p.x>=exchangeX && p.x<800 && y>=0 && y<directionsHeight) {
				exchangeClicked = true; 
				System.out.println("logic.exchange()");
				repaint();
			}
		} else {
			int x = p.x;
			int playerTileOffset = scoreHeight+boardHeight+directionsHeight;
			int y = p.y - playerTileOffset; 

			if (x>=0 && x<=(7*playerTileSize) && y>=0 && y<playerTileSize) {
				//handle when one of the players tiles is selected;
				int tileIndex = x/playerTileSize;
				//System.out.println("selectTile("+tileIndex+")");
				clicked[tileIndex] = true; 
				logic.selectTile(tileIndex);
				repaint();
			} else if (x>=(7*playerTileSize) && x<(8*playerTileSize) && y >= 0 && y<playerTileSize) {
				System.out.println("submitMove()");
				submitClicked = true; 
				logic.submitCurrentWord();
				repaint();
			}




		}
	}

	//methods that are requried byb mouseListener interface
	public void mouseReleased(MouseEvent event) { 
		//for directions
		shuffleClicked = false;
		skipClicked = false;
		exchangeClicked = false;
		repaint();
		//for playerTile
		Point p = event.getPoint();
		int x = p.x;
    		int tileIndex = x/playerTileSize;
    		clicked[tileIndex] = false;
    		submitClicked = false;
    		repaint();
	
	}

	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }

}




