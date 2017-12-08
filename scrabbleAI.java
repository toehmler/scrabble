public class scrabbleAI {

	protected boardLogic logic;
	protected tile[] cmpTiles;

	public scrabbleAI(boardLogic logic) {
	   this.logic = logic;
	   cmpTiles = new tile[7];
	   setCmpTiles();
	}	   

	protected void setCmpTiles() { //used for testing
		String letters[] = {"H","I","J","K","L","M","J"};
		for (int i=0;i<letters.length;i++) {
			cmpTiles[i] = new tile(letters[i]);
		}
	}

	public void makeMove() {
		logic.p1turn = true;
	}






}
