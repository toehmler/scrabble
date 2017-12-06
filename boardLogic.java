//boardLogic.java
import java.lang.*;
import java.awt.*;
import structure.Vector;

public class boardLogic {

	protected tile[] board;
	protected tile[] p1tiles;

	public int selectedTileIndex;
	
	public String strDir;
	
	public boolean p1turn;
	public boolean gameInProgress;
	public boolean wordStarted;

	protected boolean currentIntersects;

	protected scrabbleAI ai;
	protected scrabble display;

	protected tile selectedTile;
	protected word currentWord;

	public String[] bag;

	public int p1score;
	public int p2score;

	protected Vector placedTileIndicies;
	public Vector wordsPlayed;

	private String[] dictionary = {"ABCD"};


	public boardLogic(scrabble display) {
		board = new tile[225];
		populateBoard();
		
		bag = new String[100]; 
		bag = newBag(); 
		p1tiles = new tile[7];
		setP1tiles();

		selectedTileIndex = -1;
		
		strDir = "Welcome to Scrabble. Click this text to start a new game.";
		p1turn = true;
		gameInProgress = false;
		wordStarted = false;

		currentIntersects = false;
		
		selectedTile = new tile();
		currentWord = new word();
		
		p1score = 0;
		p2score = 0;

		placedTileIndicies = new Vector();
		wordsPlayed = new Vector();

		this.display = display;

	}

	protected void populateBoard() {
		for (int i=0;i<225;i++) {
			board[i] = new tile();
		}
	}

	/*
	public void setP1tiles() {
		String letters[] = {"A","B","C","D","E","F","G"};
		for (int i=0;i<letters.length;i++) {
			p1tiles[i] = new tile(letters[i]);
		}
	}*/

	public boolean isWinner() {
		/*
		if (bag.isEmpty())
			return true;
		else
			return false;
			*/
		return false;
	}

	public void play() {
		/*
		while (isWinner() == false) {
			strDir = "Please select a tile.";
			gameInProgress = true;
			p1turn = true;
			if (p1turn == false) {
				ai.makeMove();
			}
		}
		*/
		//
		//strDir = "The game is over";
		strDir = "Please select a tile.";
		gameInProgress = true;
		p1turn = true;

	}

	public void selectTile(int index) {
		if (p1turn) {
			selectedTile = p1tiles[index];
			strDir = "You have choosen '"+ selectedTile.letterVal+"' select a location.";
			selectedTileIndex = index;
		} else {
			strDir = "It is not your turn.";
		}
	}

	public void placeTile(int index) {
		addToBoard(index, selectedTileIndex);
		/*
		if (isValidLocation(index)) 
			addToBoard(index, selectedTileIndex);
		else { 
			strDir = "Select a valid location.";
		}
		*/
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
		p1tiles[playerTileIndex] = new tile();
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
					currentIntersects = true;
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
					currentIntersects = true;
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
			if (currentIntersects  && scoreCrossWords())
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

	public int randomNum(int min, int max){
		   int range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
		}
		
	public void setP1tiles() {
		for(int i = 0; i < 7; i ++) {
			int randIndex = randomNum(0, 99); 
			while(bag[randIndex] == "null") {
				randIndex = randomNum(0, 99); 
			}
			//System.out.print(bag[randIndex]);
			p1tiles[i] =  new tile(bag[randIndex]);
			if(randIndex >= 0 && randIndex <= 67)
				p1tiles[i].numVal = 1; 
			else if(randIndex >= 68 && randIndex <= 74)
				p1tiles[i].numVal = 2; 
			else if(randIndex >= 75 && randIndex <= 82)
				p1tiles[i].numVal = 3;
			else if(randIndex >= 83 && randIndex <= 92)
				p1tiles[i].numVal = 4;
			else if(randIndex == 93)
				p1tiles[i].numVal = 5;
			else if(randIndex >= 94 && randIndex <= 95)
				p1tiles[i].numVal = 8;
			else if(randIndex >= 96 && randIndex <= 97)
				p1tiles[i].numVal = 10;
			else
				p1tiles[i].numVal = 0;
			bag[randIndex] = "null"; //signifies the piece at that index has been draw, to preserve indices */
		}
	}

	public String [] newBag() {
		for(int i = 0; i < 12; i++)
			bag[i] = "E"; 
		for(int i = 12; i < 21; i++)
			bag[i] = "A"; 
		for(int i = 21; i < 30; i++)
			bag[i] = "I"; 
		for(int i = 30; i < 38; i++)
			bag[i] = "O"; 
		for(int i = 38; i < 44; i++)
			bag[i] = "N"; 
		for(int i = 44; i < 50; i++)
			bag[i] = "R"; 
		for(int i = 50; i < 56; i++)
			bag[i] = "T"; 
		for(int i = 56; i < 60; i++)
			bag[i] = "L";
		for(int i = 60; i < 64; i++)
			bag[i] = "S"; 
		for(int i = 64; i < 68; i++)
			bag[i] = "U";
		//indices 0 through 67 are worth 1 point 
		for(int i = 68; i < 72; i++)
			bag[i] = "D";
		for(int i = 72; i < 75; i++)
			bag[i] = "G"; 
		//indices 68 through 74 are worth 2 points 
		for(int i = 75; i < 77; i++)
			bag[i] = "B"; 
		for(int i = 77; i < 79; i++)
			bag[i] = "C"; 
		for(int i = 79; i < 81; i++)
			bag[i] = "M"; 
		for(int i = 81; i < 83; i++)
			bag[i] = "P"; 
		//indices 74 through 82 are worth 3 points 
		for(int i = 83; i < 85; i++)
			bag[i] = "F"; 
		for(int i = 85; i < 87; i++)
			bag[i] = "H"; 
		for(int i = 87; i < 89; i++)
			bag[i] = "V"; 
		for(int i = 89; i < 91; i++)
			bag[i] = "W"; 
		for(int i = 91; i < 93; i++)
			bag[i] = "Y"; 
		//indices 83 through 92 are worth 4 points 
		for(int i = 93; i < 94; i++)
			bag[i] = "K"; 
		//index 93 (K) is worth 5 points 
		for(int i = 94; i < 95; i++)
			bag[i] = "J"; 
		for(int i = 95; i < 96; i++)
			bag[i] = "X"; 
		//indices 94 and 95 are worth 8 points 
		for(int i = 96; i < 97; i++)
			bag[i] = "Q";
		for(int i = 97; i < 98; i++)
			bag[i] = "Z"; 
		//indices 96 and 97 are worth 10 
		for(int i = 98; i < 100; i++)
			bag[i] = " "; 
		//indices 98 and 99 are BLANK, worth 0 
		return bag; 
		}



	//resetTurn()
	//
	//undoPlacement()
	//
	//
}
