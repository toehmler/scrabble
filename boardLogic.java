//boardLogic.java
import java.lang.*;
import java.awt.*;
import structure.Vector;

public class boardLogic {

	protected tile[] board;
	protected tile[] p1tiles;
	
	public String strDir;
	
	public boolean p1turn;
	public boolean gameInProgress;
	public boolean wordStarted;

	protected scrabbleAI ai;
	protected scrabble display;

	protected tile selectedTile;
	protected word currentWord;


	public Vector bag;

	public int p1score;
	public int p2score;

	protected Vector placedTileIndicies;
	public Vector wordsPlayed;

	public boardLogic(scrabble display) {
		board = new tile[225];
		p1tiles = new tile[7];
		
		strDir = "Welcome to Scrabble. Click this text to start a new game.";
		p1turn = false;
		gameInProgress = false;
		wordStarted = false;
		
		selectedTile = new tile();
		currentWord = new word();

		bag = new Vector<tile>();

		p1score = 0;
		p2score = 0;

		placedTileIndicies = new Vector<int>();
		wordsPlayed = new Vector<word>();
	}

	public void setP11iles() {
		String letters[] = {"A","B","C","D","E","F","G"};
		for (int i=0;i<letters.length;i++) {
			p1tiles[i] = new tile(letters[i]);
		}
	}

	public boolean isWinner() {
		if (bag.isEmpty())
			return true;
		else
			return false;
	}


	public void play() {
		while (!isWinner()) {
			gameInProgress = true;
			p1turn = true;
			if (p1turn == false) {
				ai.makeMove();
			}
		}
		strDir = "The game is over";
		display.directionsDisplay.repaint();
	}

	public void selectTile(int index) {
		if (p1turn) {
			selected = p1tiles[index];
			strDir = "You have choosen '"+selected.letterVal+"' select a location.";
			display.directionsDisplay.repaint();
		} else {
			strDir = "It is not your turn.";
			display.directionsDisplay.repaint();
		}
	}

	public void placeTile(int index, int playerTileIndex) {
		if (isValidLocation(index)) 
			addToBoard(index, playerTileIndex);
		else { 
			strDir = "Select a valid location.";
			display.directionsDisplay.repaint();
		}
	}

	protected boolean inSameCol(int index1, int index2) {
		//returns true if the indicies are in the same column
		if (Math.abs(index1-index2) % 15 == 0) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean inSameRow(int index1, int index2) {
		//returns true if the two indicies are in the same row
		if (index1 > index2) {
			if (index1-index2 < 15) {
				return true;
			} else {
				return false;
			}
		} else {
			if (index2-index1 < 15) {
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean tilesFilledVert(int index1, int index2) {
		//returns true if the tiles between the two indicies are filled
		//return false otherwise
		int spacesBtwn = (Math.abs(index2-index1)/15) - 1;
		if (index1>index2) { //index1 is below index2
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index1+(i*15)].isEmpty()) 
					return false;
			}
			return true;
		} else {
			for (int i=1;i<=spacestwn;i++) {
				if (board[index2+(i*15)].isEmpty()) 
					return false;
			}
		}
	}


	protected boolean tilesFilledHoriz(int index1, int index2) {
		//returns true if the tiles between the two indicies are filled
		if (index1>index2) {
			int spacesBtwn = index1-index2;
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index2+i].isEmpty()) 
					return false;
			}
			return true;
		} else {
			int spacesBtwn = index2-index1;
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index1].isEmpty()) 
					return false;
			}
			return true;
		}
	}


	protected boolean isValidLocation(int index) {
		//returns true if the selected tile doesn't have a piece
		//and is adjacent to started currentWord or inline with its current orientation
		if (board[index].hasVal()) {
			return false;
		} else if (wordStarted == true) {
			//sets the orientation of the current word
			//add to board is never passed an index that doesnt 
			if (currentWord.orientation == 0) { //orientation not set yet
				if (inSameCol(currentWord.endIndex,index)) {
					if (tilesFilledVert(currentWord.endIndex, index)) {
						currentWord.orientation = 1;
						return true;
					} else {
						return false;
					}
				} else if (inSameRow(currentWord.endIndex, index)) {
					if (tilesFilledHoriz(currentWord.endIndex, index)) {
						currentWord.orientation = 2;
						return true;
					} else 
						return false;
				}
			} else if (currentWord.orientation == 1) { //vertical orientation set previously
				if (inSameCol(currentWord.endIndex, index)) {
					if (tilesFilledVert(currentWord.endIndex, index)) 
						return true;
					 else 
						return false;
				} else 
					return false;
			} else { //horizontal orientation set previously  
				if (inSameRow(currentWord.endIndex, index)) {
					if (tilesFilledHoriz(currentWord.endIndex, index))
						return true;
					else
						return false;
				} else 
					return false;
			}
		} else 
			return true;
	}

	protected void addToBoard(int index, int playerTileIndex) {
		if (wordStarted == false) {
			wordStarted = true;
			currentWord.startIndex = index;
			currentWord.endIndex = start;
			board[index] = p1tiles[playerTileIndex];
			//add way of removing tiles from player
			//add way to keep track of indicies where tiles were placed
			//in case the word is not valid
		} else {		
			if (index>currentWord.endIndex) 
				currentWord.endIndex = index;
			else 
				currentWord.startIndex = index;
			board[index] = p1tiles[playerTileIndex];
		}
		placedTileIndicies.add(playerTileIndex);
		p1tiles[playerTileIndex] = null;
	}
}


