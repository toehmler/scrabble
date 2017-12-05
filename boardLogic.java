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

	protected boolean currentIntersects;

	protected scrabbleAI ai;
	protected scrabble display;

	protected tile selectedTile;
	protected word currentWord;

	public Vector bag;

	public int p1score;
	public int p2score;

	protected Vector placedTileIndicies;
	public Vector wordsPlayed;

	private String[] dictionary = {"ABCD"};


	public boardLogic(scrabble display) {
		board = new tile[225];
		p1tiles = new tile[7];

		
		strDir = "Welcome to Scrabble. Click this text to start a new game.";
		p1turn = false;
		gameInProgress = false;
		wordStarted = false;

		currentIntersects = false;
		
		selectedTile = new tile();
		currentWord = new word();

		bag = new Vector();

		p1score = 0;
		p2score = 0;

		placedTileIndicies = new Vector();
		wordsPlayed = new Vector();

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
			selectedTile = p1tiles[index];
			strDir = "You have choosen '"+ selectedTile.letterVal+"' select a location.";
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
				else
					currentIntersects = true;
			}
			return true;
		} else {
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index2+(i*15)].isEmpty()) 
					return false;
				else
					currentIntersects = true;
			}
			return true;
		}
	}


	protected boolean tilesFilledHoriz(int index1, int index2) {
		//returns true if the tiles between the two indicies are filled
		if (index1>index2) {
			int spacesBtwn = index1-index2;
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index2+i].isEmpty()) 
					return false;
				else
					currentIntersects = true;
			}
			return true;
		} else {
			int spacesBtwn = index2-index1;
			for (int i=1;i<=spacesBtwn;i++) {
				if (board[index1+i].isEmpty()) 
					return false;
				else 
					currentIntersects = true;
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
		} 
		return true;
	}

	protected void setBoardTile(int index, int playerTileIndex) {
		//maintains the multiplier value of each tile on the board
		board[index].letterVal = p1tiles[playerTileIndex].letterVal;
		board[index].numVal = p1tiles[playerTileIndex].numVal;
	}
	
	protected void addToBoard(int index, int playerTileIndex) {
		if (wordStarted == false) {
			wordStarted = true;
			currentWord.startIndex = index;
			currentWord.endIndex = index;
			setBoardTile(index, playerTileIndex);
			//add way of removing tiles from player
			//add way to keep track of indicies where tiles were placed
			//in case the word is not valid
		} else {		
			if (index>currentWord.endIndex) 
				currentWord.endIndex = index;
			else 
				currentWord.startIndex = index;
			setBoardTile(index, playerTileIndex);
		}
		placedTileIndicies.add(playerTileIndex);
		p1tiles[playerTileIndex] = null;
	}

	protected String wordToString(word w) {
		String wordStr = null;;
		if (w.orientation == 2) {
			for (int i=0;i<w.length();i++) {
				String letter = board[w.startIndex+ i].letterVal;
				wordStr += letter;
			}
			return wordStr;
		} else if (w.orientation == 1) {
			int length = ((w.endIndex - w.startIndex)/15) + 1;
			for (int i=0;i<w.length();i++) {
				String letter = board[w.startIndex + (i*15)].letterVal;
				wordStr += letter;
			}
			return wordStr;
		} else {
			return board[w.startIndex].letterVal;
		}
	}

	protected boolean wordisValid(word W) {
		//checks whether the current word is in the dictionary
		//returns true if word can be found in the array of strings
		String wordStr = wordToString(currentWord);
		//add a loop for something to check if the word is actually in the dict

		return true; //placeholder
	}
	
	protected boolean scoreCrossWords() {
		int increment = 1;
		if (currentWord.orientation == 1) 
			increment = 15;
		for (int i=currentWord.startIndex;i<=currentWord.startIndex;i+=increment) {
			if (currentWord.orientation == 1) {
				if (board[i-1].hasVal() || board[i+1].hasVal()) {
					int start = i;
					int end = i;
					while (board[start-1].hasVal()) 
						start --;
					while (board[end+1].hasVal())
						end --;
					word crossWord = new word(start, end, 2);
					if (wordisValid(crossWord)) {
						wordsPlayed.add(crossWord);
						p1score += scoreWord(crossWord);
					} else 
						return false;
				} 
			} else {
				if (board[i-15].hasVal() || board[i+15].hasVal()) {
					int start = i;
					int end = i;
					while (board[start-15].hasVal())
						start -= 15;
					while (board[start+15].hasVal()) 
						end += 15;
					word crossWord = new word(start, end, 1);
					if (wordisValid(crossWord)) {
						wordsPlayed.add(crossWord);
						p1score += scoreWord(crossWord);
					} else 
						return false;
				}
			}
		} 
		return true;
	}

	public int scoreWord(word w) {
		//need to add functionality to check if the tile mult is greater than 4
		//if 4 or 5 - double word or triple word score
		int score = 0;
		int incr = 1;
		if (w.orientation == 1)
			incr = 15;
		for (int i=w.startIndex;i<=w.endIndex;i+=incr) {
			score += board[i].numVal * board[i].mult;
		}
		return score;
	}

	protected void submitCurrentWord() {
		//called when the submit button is called
		if (wordisValid(currentWord)) {
			if (scoreCrossWords())
				p1score += scoreWord(currentWord);
			else {
				resetTurn();
			}
		} else 
			resetTurn();
	}

	public void resetTurn() {
	}
	
	public void undoPlacement() {
	}



	//resetTurn()
	//
	//undoPlacement()
	//
	//





		
















	
}
