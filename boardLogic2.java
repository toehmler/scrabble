//boardLogic.java
import java.lang.*;
import java.awt.*;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.Vector;

public class boardLogic {

	//display and ai components
	protected scrabble display;
	protected tile[]board;
	protected tile[] p1tiles;
	protected tile[] p2tiles;
	protected Stack<tile> bag;

	protected String strDir;
	protected int p1score;
	protected int p2score;

	protected boolean p1turn;
	protected boolean p2turn;
	protected boolean gameInProgress;
	protected boolean wordStarted;
	protected int[] currentWord;
	protected int selectedTileIndex;

	public boardLogic(scrabble display) {

		this.display = display;
		board = new tile[225];
		p1tiles = new tile[7];
		p2tiles = new tile[7];
		bag = new Stack<tile>();
		strDir = "Welcome to scrabble. Press here to start a new game.";
		p1score = 0;
		p2score = 0;
		p1turn = false;
		p2turn = false;
		gameInProgress = false;
		wordStarted = false;
		selectTileIndex = -1;
		currentWord = new int[7];
		initCurrent();
		fillBag();
		populateBoard();
		setp1tiles();
		setp2tiles();

	}

	protected void initCurrent() {
		for (int i =0;i<7;i++) {
			currentWord[i] = -1;
		}
	}

	protected void fillBag() {
		//fills the bagStr with the correct number of tiles an
		String[] bagStr = newBag();
		for (int i=0;i<bagStr.length;i++) {
			int randIndex = randomNum(0,99);
			while (bagStr[randIndex] == "null")
			   randIndex = randomNum(0,99);
			bag.push(new tile(bagStr[randIndex], getTileValue(randIndex)));
		}
	}

	protected void populateBoard() {
		for (int i=0;i<225;i++) {
			board[i] = new tile();
		}
	}
	
	protected void setP1tiles() {
		for(int i = 0; i < 7; i ++) {
			p1tiles[i] = bag.pop();
		}
	}

	protected void setP2tiles() {
		for (int i=0;i<7;i++) {
			p2tiles[i] = bag.pop();
		}
	}

	protected void play() {
		gameInProgress = true;
		p1turn = true;
	}

	protected void selectTile(int index) {
		selectedTileIndex = index;
	}

	protected void placeTile(int index) {
		if (board[index].isEmpty()) {
			if (p1turn) {
				board[index] = p1tiles[selectedTileIndex];
				currentWord[selectedTileIndex] = index;
				selectedTileIndex = -1;
			} else {
				board[index] = p2tiles[selectedTileIndex];
				currentWord[selectedTileIndex] = index;
				selectTileIndex = -1;
			}
		} else 
			strDir = "Select an empty tile.";
	}

	protected void submitCurrentWord() {







	

			
}

	

















	public String[] newBag() {
		String[] bagStr = new String[100];
		for(int i = 0; i < 12; i++)
			bagStr[i] = "E"; 
		for(int i = 12; i < 21; i++)
			bagStr[i] = "A"; 
		for(int i = 21; i < 30; i++)
			bagStr[i] = "I"; 
		for(int i = 30; i < 38; i++)
			bagStr[i] = "O"; 
		for(int i = 38; i < 44; i++)
			bagStr[i] = "N"; 
		for(int i = 44; i < 50; i++)
			bagStr[i] = "R"; 
		for(int i = 50; i < 56; i++)
			bagStr[i] = "T"; 
		for(int i = 56; i < 60; i++)
			bagStr[i] = "L";
		for(int i = 60; i < 64; i++)
			bagStr[i] = "S"; 
		for(int i = 64; i < 68; i++)
			bagStr[i] = "U";
		//indices 0 through 67 are worth 1 point 
		for(int i = 68; i < 72; i++)
			bagStr[i] = "D";
		for(int i = 72; i < 75; i++)
			bagStr[i] = "G"; 
		//indices 68 through 74 are worth 2 points 
		for(int i = 75; i < 77; i++)
			bagStr[i] = "B"; 
		for(int i = 77; i < 79; i++)
			bagStr[i] = "C"; 
		for(int i = 79; i < 81; i++)
			bagStr[i] = "M"; 
		for(int i = 81; i < 83; i++)
			bagStr[i] = "P"; 
		//indices 74 through 82 are worth 3 points 
		for(int i = 83; i < 85; i++)
			bagStr[i] = "F"; 
		for(int i = 85; i < 87; i++)
			bagStr[i] = "H"; 
		for(int i = 87; i < 89; i++)
			bagStr[i] = "V"; 
		for(int i = 89; i < 91; i++)
			bagStr[i] = "W"; 
		for(int i = 91; i < 93; i++)
			bagStr[i] = "Y"; 
		//indices 83 through 92 are worth 4 points 
		for(int i = 93; i < 94; i++)
			bagStr[i] = "K"; 
		//index 93 (K) is worth 5 points 
		for(int i = 94; i < 95; i++)
			bagStr[i] = "J"; 
		for(int i = 95; i < 96; i++)
			bagStr[i] = "X"; 
		//indices 94 and 95 are worth 8 points 
		for(int i = 96; i < 97; i++)
			bagStr[i] = "Q";
		for(int i = 97; i < 98; i++)
			bagStr[i] = "Z"; 
		//indices 96 and 97 are worth 10 
		for(int i = 98; i < 100; i++)
			bagStr[i] = " "; 
		//indices 98 and 99 are BLANK, worth 0 
		return bagStr; 
	}
	












