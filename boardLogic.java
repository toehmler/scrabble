//boardLogic.java
import java.lang.*;
import java.awt.*;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;
import java.util.Vector;


public class boardLogic {

	//display and ai components  
	protected scrabbleAI ai;
	protected scrabble display;

	//board tiles, player tiles and bagStr tiles
	protected tile[] board;
	protected tile[] p1tiles;
	protected tile[] p2tiles;
	protected Stack<tile> bag;

	//store directions and scores to display
	protected String strDir;
	protected int p1score;
	protected int p2score;

	//track state of turn and game
	protected boolean p1turn;
	protected boolean p2turn;
	protected boolean gameInProgress;
	protected boolean wordStarted;
	protected boolean currentIntersects;
	protected word currentWord;

	//track selection and placements of tiles during turn
	protected int[] placedTileIndices;
	public int selectedTileIndex; //track what number tile was placed

	//placedTileIndices[0] = board index loc of the first playerTile

	//track what words have been played (as strings)
	public Vector<word> wordsPlayed;

	protected String[] dictionary = {"ABCD"};
	
	public boardLogic(scrabble display) {

		this.display = display;

		//init and populate various storage objects
		bag = new Stack<tile>();
		board = new tile[225];
		p1tiles = new tile[7];
		p2tiles = new tile[7];
		fillBag();
		populateBoard();
		setP1tiles();
		setP2tiles();

		strDir = "Welcome to scrabble. Press here to start a new game.";
		p1score = 0;
		p2score = 0;

		p1turn = false;
		p2turn = false;
		gameInProgress = false;
		wordStarted = false;
		currentIntersects = false;
		currentWord = new word();

		placedTileIndices = new int[7];
		selectedTileIndex = -1; //value >= 0 means a tile is selected
		initPlacedTileIndices();

		wordsPlayed = new Vector<word>();
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
		//fill the board with tiles
		for (int i=0;i<225;i++) {
			board[i] = new tile();
		}
	}

	protected void initPlacedTileIndices() {
		//init placeTileIndices with all -1s (nothing placed)
		for (int i=0;i<7;i++) {
			placedTileIndices[i] = -1;
		}
	}

	public boolean isWinner() {
		//checks if the bagStr is empty and declares a winner
		/*
		if (bagStr.isEmpty())
			return true;
		else
			return false;
			*/
		if (bag.isEmpty()) {
			if (p1score > p2score) {
				strDir = "Player 1 won. Press to play again.";
			} else {
				strDir = "Player 2 has won. Press to play again.";
			}
			return true;
		} else {
			return false;
		}
	}

	public void endGame() {
		p1turn = false;
		p2turn = false;
		gameInProgress = false;
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
		strDir = "Please select a tile;";
		gameInProgress = true;
		p1turn = true;

	}

	public void selectTile(int index) {
		if (p1turn || p2turn) {
			selectedTileIndex = index;
			if (p1turn) {
				if(isSelectedBlank(p1tiles[selectedTileIndex]))
					strDir = "Please select a letter for your blank tile."; 
				else
					strDir = "You have choosen '"+ p1tiles[selectedTileIndex].letterVal+"' select a location.";
			} else {
				if(isSelectedBlank(p2tiles[selectedTileIndex]))
					strDir = "Please select a letter for your blank tile."; 
				else
					strDir = "You have choosen '"+ p2tiles[selectedTileIndex].letterVal+"' select a location.";
			}

		} else {
			strDir = "There is no game in progress, press here to start a new game.";
		}
	}

	public void placeTile(int index) {
		//called when a tile on the board is pressed
		if (gameInProgress) {
			if (isValidLocation(index)) {
				addToBoard(index, selectedTileIndex);
				strDir = "Select another tile or your submit move.";
			}else { 
				strDir = "Select a valid location.";
			}
		} else 
			strDir = "It is not your turn.";
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

	protected boolean tilesFilledHoriz(int index1, int index2) {
		//still missing update to current intersects this way
		if (index1>index2) {
			for (int i=index2+1;i<index1;i++) {
				if (board[i].isEmpty())
					return false;
				else {
					currentWord.intersects.add(i);
					currentIntersects = true;
				}
			} 
		} else {
			for (int i=index1+1;i<index2;i++) {
				if (board[i].isEmpty())
					return false;
				else { 
					currentWord.intersects.add(i);
					currentIntersects = true;
				}
			}
		} return true;
	}

	protected boolean tilesFilledVert(int index1, int index2) {
		//also missing update to currentIntersects
		if (index1>index2) {
			if (index1+15 == index2)
			
			for (int i=(index2+15);i<index1;i+=15) {
				if (board[i].isEmpty())
					return false;
				else {
				   	currentWord.intersects.add(i);	
					currentIntersects = true;
				}
			}
		} else {
			for (int i=(index1+15);i<index2;i+=15) {
				if (board[i].isEmpty())
					return false;
				else {
				   	currentWord.intersects.add(i);	
					currentIntersects = true;
				}
			}
		} return true;
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
						if (board[currentWord.startIndex-15].hasVal()) {
							currentIntersects = true;
							while (board[currentWord.startIndex-15].hasVal()) {
								currentWord.startIndex -= 15;
								currentWord.intersects.add(currentWord.startIndex);
							}
						} else if (board[currentWord.endIndex+15].hasVal()) {
							currentIntersects = true;
							while (board[currentWord.endIndex+15].hasVal()) {
								currentWord.endIndex += 15;
								currentWord.intersects.add(currentWord.endIndex);
							}

						}
						return true;
					} else {
						return false;
					}
				} else if (inSameRow(currentWord.endIndex, index)) {
					if (tilesFilledHoriz(currentWord.endIndex, index)) {
						currentWord.orientation = 2;
						if (board[currentWord.startIndex-1].hasVal()) {
							currentIntersects = true;
							while (board[currentWord.startIndex-1].hasVal()) {
								currentWord.startIndex --;
								currentWord.intersects.add(currentWord.startIndex);
							}

						} else if (board[currentWord.endIndex+1].hasVal()) {
							while (board[currentWord.endIndex+1].hasVal()) {
								currentWord.endIndex ++;
								currentWord.intersects.add(currentWord.endIndex);
							}
						}
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
					else {
						return false;
					}
				} else {
					return false;
				}
			}
		} 
		return true;
	}

	protected boolean checkForAdjacent(int index) {
		if (board[index+15].hasVal() || board[index-15].hasVal()) {
			wordStarted = true;
			int start = index;
			int end = index;
			while (board[end+15].hasVal())
				end+=15;
			while (board[start-15].hasVal())
				start-=15;
			currentWord.startIndex = start;
			currentWord.endIndex = end;
			currentWord.orientation = 1;
			return true;
		} else if (board[index+1].hasVal() || board[index-1].hasVal()) {
			wordStarted = true;
			int start = index;
			int end = index;
			while (board[start-1].hasVal())
				start --;
			while (board[end+1].hasVal())
				end ++;
			currentWord.startIndex = start;
			currentWord.endIndex = end;
			currentWord.orientation = 2;
			return true;
		} else 
			return false;
	}

	protected void setBoardTile(int index, int playerTileIndex) {
		//maintains the multiplier value of each tile on the board
		if(p1turn) {
			board[index].letterVal = p1tiles[playerTileIndex].letterVal;
			board[index].numVal = p1tiles[playerTileIndex].numVal;
		} else {
			board[index].letterVal = p2tiles[playerTileIndex].letterVal;
			board[index].numVal = p2tiles[playerTileIndex].numVal;
		}

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
			}
			setBoardTile(index, playerTileIndex);
		//keep track of what playerTileIndex was placed
		placedTileIndices[playerTileIndex] = index;
		//reset that playerTile to be a blank tile (remove it)
		if (p1turn) 
			p1tiles[playerTileIndex] = new tile();
		else 
			p2tiles[playerTileIndex] = new tile();
	}

	protected String wordToString(word w) {
		String wordStr = "";
		if (w.orientation == 2) {
			for (int i=0;i<w.length();i++) {
				String letter = board[w.startIndex+ i].letterVal;
				wordStr += letter;
			}
		} else if (w.orientation == 1) {
			int length = ((w.endIndex - w.startIndex)/15) + 1;
			for (int i=0;i<w.length();i++) {
				String letter = board[w.startIndex + (i*15)].letterVal;
				wordStr += letter;
			}
		} else {
			wordStr = board[w.startIndex].letterVal;
		}
		return wordStr.toUpperCase();
	}

	public static String [] parseDict() throws IOException {
		//BufferedReader file = new BufferedReader(new FileReader("scrabbleDict.txt"));

		ArrayList<String>list = new ArrayList<String>();
		try {
			URL url = new URL("http://www.cs.middlebury.edu/~roehmler/fp/scrabbleDict.txt");
			InputStream file = url.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(file));
			String word;
			while((word = bf.readLine()) != null){
				list.add(word);
			}
			file.close();
		} catch(IOException err)
		{}
		String[] stringArr = list.toArray(new String[0]);
		return stringArr;

	}
	
	protected boolean wordisValid(word W) throws IOException {
		//checks whether the current word is in the dictionary
		//
		//returns true if word can be found in the array of strings
		String [] dict  = parseDict(); 
		String wordStr = wordToString(W);
		return Arrays.asList(dict).contains(wordStr); 
	}
	
	protected boolean scoreCrossWords() throws IOException {
		int increment = 1;
		if (currentWord.orientation == 1) 
			increment = 15;
		for (int i=currentWord.startIndex;i<=currentWord.endIndex;i+=increment) { //possible
			if (currentWord.orientation == 1) {
				if (currentWord.intersects.contains(i) == false) {
					if (board[i-1].hasVal() && board[i+1].hasVal()) {
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
							if (p1turn) 
								p1score += scoreWord(crossWord);
							else 
								p2score += scoreWord(crossWord);
						} else 
							return false;
					}
				} 
			} else {
				if (currentWord.intersects.contains(i) == false) {
					if (board[i-15].hasVal() && board[i+15].hasVal()) {
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
							if (p1turn)
								p1score += scoreWord(crossWord);
							else 
								p2score += scoreWord(crossWord);
						} else 
							return false;
					}
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

	protected void submitCurrentWord() throws IOException {
		//called when the submit button is called
		if (currentWord.startIndex == currentWord.endIndex) {
			if (checkForAdjacent(currentWord.startIndex)){

				//possibly add a check for crosswords too
				int wordScore = scoreWord(currentWord);
				if (p1turn) {
					p1score += wordScore;
					strDir = "Player 1 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				} else {
					p2score += wordScore;
					strDir = "Player 2 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				wordsPlayed.add(currentWord);
				changeTurn();
				}
			} else {
				resetPlayerTiles();
				strDir = "The word you submitted was not valid. Try again.";
			}

		} else if (wordisValid(currentWord)) {
				System.out.println("here");
			if (wordsPlayed.isEmpty()) {
				int wordScore = scoreWord(currentWord);
				if (p1turn) {
					p1score += wordScore;
					strDir = "player 1 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				} else {
					p2score += wordScore;
					strDir = "player 2 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				}
				wordsPlayed.add(currentWord);
				changeTurn();
			} else if (currentIntersects && scoreCrossWords())  {
				int wordScore = scoreWord(currentWord);
				if (p1turn) {
					p1score += wordScore;
					strDir = "player 1 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				} else {
					p2score += wordScore;
					strDir = "player 2 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
				}
				wordsPlayed.add(currentWord);
				changeTurn();
			} else if (currentIntersects == false && board[currentWord.endIndex+1].hasVal()) {
				while (board[currentWord.endIndex+1].hasVal()){
					currentWord.endIndex ++;
					currentWord.intersects.add(currentWord.endIndex);
				}
				System.out.println("test");
				if (scoreCrossWords()) {
					int wordScore = scoreWord(currentWord);
					if (p1turn) {
						p1score += wordScore;
						strDir = "player 1 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";

					} else {
						p2score += wordScore;
						strDir = "player 2 played  '"+wordToString(currentWord)+"'  for "+wordScore+" points.";
					}
					wordsPlayed.add(currentWord);
					changeTurn();
				}
			} else {
				resetPlayerTiles();
				strDir = "The word you submitted was not valid. Try again.";
			}
		} else {
			resetPlayerTiles();
			strDir = "The word you submitted was not valid. Try again.";
		}
	}

	public void resetPlayerTiles() {
		//take the players tiles off the board and puts them back
		for (int i=0;i<7;i++) {
			int boardIndex = placedTileIndices[i];
			if (boardIndex >= 0) {
				if (p1turn) 
					p1tiles[i] = board[boardIndex];
				else 
					p2tiles[i] = board[boardIndex];
				board[boardIndex] = new tile();
			}
		}
		resetCurrentWord();
	}

	protected void getNewPlayerTiles() {
		for (int i=0;i<7;i++) {
			if (placedTileIndices[i] >= 0) {
				if (p1turn)
					p1tiles[i] = bag.pop();
				else
					p2tiles[i] = bag.pop();
			}
		}
	}

	public void resetCurrentWord() {
		wordStarted = false;
		currentIntersects = false;
		currentWord = new word();
		initPlacedTileIndices();
		selectedTileIndex = -1;
		currentWord.intersects.removeAllElements();
	}

	public void changeTurn() {
		getNewPlayerTiles();
		resetCurrentWord();
		if (p1turn) {
			p1turn = false;
			p2turn = true;
		} else {
			p1turn = true;
			p2turn = false;
		}
	}


	public void resetTurn() {
		/*

		selectedTileIndex = -1;

		currentIntersects = false;
		selectedTile = new tile();
		currentWord = new word();
		for (int i=0;i<placedTileIndicies.size();i++) {
			int pTileIndex = placedTileIndicies.elementAt(i);
			//
		}	
		*/
	}
	
	public int randomNum(int min, int max){
		   int range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
	}

	public int getTileValue(int index) {
		if (index >= 0 && index <= 74) 
			return 1;
		else if (index >= 68 && index <=74) 
			return 2;
		else if(index >= 75 && index <= 82)
			return 3;
		else if(index >= 83 && index <= 92)
			return 4;
		else if(index == 93)
			return 5;
		else if(index >= 94 && index <= 95)
			return 8;
		else if(index >= 96 && index <= 97) 
			return 10;
		else 
			return 0;
		
	}
		
	public void setP1tiles() {
		for(int i = 0; i < 7; i ++) {
			p1tiles[i] = bag.pop();
		}
	}

	public void setP2tiles() {
		for (int i=0;i<7;i++) {
			p2tiles[i] = bag.pop();
		}
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
	
	public boolean isSelectedBlank(tile Tile) {
		//method to deal with setting blank tile value
		if(Tile.letterVal == " ") {
			return true; 
		}else {
			return false; 
		}
	}
	
	public void shuffle(tile[] playerTiles) {
		//method to shuffle player's pieces
        tile[] shuffledTiles = new tile[7];
        int mid = 6/2;
        int k = 0; 
        for (int i = 0; i < mid; i++) {
            shuffledTiles[k] = playerTiles[i];
            shuffledTiles[k+1] = playerTiles[mid + i];
            k = k + 2;
        }
        shuffledTiles[6] = playerTiles[0];
        shuffledTiles[0] = playerTiles[5]; 
        shuffledTiles[5] = playerTiles[6]; 
        p1tiles = shuffledTiles;
	}
	
	public void skip() {
		//method for player to skip turn 
		if(p1turn) {
			strDir = "Player 1 skipped their turn. It's not player 2's turn.";
			resetPlayerTiles();
			changeTurn();
		}else {
			strDir = "Player 2 skipped their turn. It's now player 1's turn.";
			resetPlayerTiles();
			changeTurn();

		}
	}

	public void exchange() {
		//method to exchange players tiles for new ones
		strDir = "You exchanged your tiles and forfeited your turn.";
		tile [] newPlayerTiles = new tile[7]; 
		for(int i=0;i < 7;i++) {
			newPlayerTiles[i] = bag.pop(); 
		}
		if (p1turn) {
			for(tile playerTile : p1tiles) {
				bag.push(playerTile);
			}
			p1tiles = newPlayerTiles;
			changeTurn();
		} else {
			for (tile playerTile : p2tiles) {
				bag.push(playerTile);
			}
			p1tiles = newPlayerTiles;
			changeTurn();
		}
	}
	
	//resetTurn()
	//
	//undoPlacement()
	//
	//	
}
