import java.awt.event.*;
import java.awt.*;

public class boardLogic{

	public tile[] board;
	public tile[] p1Tiles;
	public String directions;
	public boolean p1Turn;
	public boolean gameInProgress;
	protected boolean wordStarted;
	protected boolean orientationSet;
	protected Word word;
	protected String selected;  
	protected int p1Score;
	protected int p2Score;
	protected scrabbleAI ai;
	protected int numTiles;
	protected scrabble display;


	//add scores for p1 and p2
	
	public boardLogic(scrabble display, int numTiles) {
		this.display = display;
		this.numTiles = numTiles;
		tile[] board = new tile[numTiles];
		tile[] p1Tiles = new tile[7];
		setp1Tiles();
		selected = null;
		ai = new scrabbleAI(this);
		p1Score = 0;
		p2Score = 0;
		directions = "Welcome to scrabble! Click this text to begin!";
		p1Turn = false;
		gameInProgress = false;
		wordStarted = false;
		word = new Word();
	}

	public void setP1Tiles() {
		p1Tiles[0] = "A";
		p1Tiles[1] = "B";
		p1Tiles[2] = "C";
		p1Tiles[3] = "D";
		p1Tiles[4] = "E";
		p1Tiles[5] = "F";
		p1Tiles[6] = "G";
	}
	
	public void play() {
		//while there is no winner:
		gameInProgess = true;
		p1TurnStart();
	}

	public void p1TurnStart() {
		//runs every time it is the first players turn;
		p1Turn = true;
		directions = "Player 1, please select a piece";
		scrabble.directions.paint();
		
	}

	public void p1TurnEnd() {
		//ends the current turn
		p1Turn = false;
	}

	public void submitWord() {
		//checks to see if the word for the current turn is valid
	}

	public void selectTile(int index) {
		while (p1Turn) {
			selected = p1Tiles[index].letterVal;
		}
	}

	public void placeTile(int index){
		if (wordStarted) {
			if (orientation == 1) {
				//orientation is vertical
			} else if (orientation == 2) {
				//orientation is horizontal 
			} else {
				//check if the word is on either above, below or to the sides
					//if it is set the orientation
				//otherwise update directions
					directions = "You can't make words on the diagonal";
					scrabble.directions.paint();
			}
		} else {
			//Start a new word with the selected letter
			//update the board struct with the new tile
			//set the starting index of the word
			wordStarted = true;
			word.wordVal += selected; 
			board[index].letterVal = selected;
			word.startIndex = index;
		}
		//reset selected
		selected = null;
	}

	public void selectTile(int index) {
		selected = p1Tiles[index];
	}

	public void submitMove() {
		wordStarted = false;
		//check if word is valid
		score();
		word.empty();  
	}

	public void score() {
		//score the current word
	}


}




