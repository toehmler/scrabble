//word.java

public class word {

	public String wordVal;
	public int startIndex;
	public int orientation;
	public int score;
	
	public word() {
		this.wordVal = null;
		this.startIndex = 0;
		this.orientation = 0;
		this.score = 0;
	}
	
	public void empty() {
		//reset all instance variables - used after a word is submitted
		wordVal = null;
		startIndex = 0;
		orientation = 0;
		score = 0;
	}

}

