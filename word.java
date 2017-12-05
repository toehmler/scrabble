//word.java

public class word {

	public int startIndex;
	public int endIndex;
	public int orientation;

	public word(int start) {
		this.startIndex = start;
		this.endIndex = start;
		this.orientation = 0;
	}

	public word() {
		this.startIndex = 0;
		this.endIndex = 0;
		this.orientation = 0;
	}

}


