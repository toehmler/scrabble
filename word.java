//word.java
import structure.Vector;

public class word {

	public int startIndex;
	public int endIndex;
	public int orientation;
	public Vector intersects;

	public word(int start) {
		this.startIndex = start;
		this.endIndex = start;
		this.orientation = 0;
		intersects = new Vector();
	}

	public word() {
		this.startIndex = 0;
		this.endIndex = 0;
		this.orientation = 0;
		intersects = new Vector();
	}

	public word(int start, int end, int orient) {
		this.startIndex = start;
		this.endIndex = end;
		this.orientation = orient;
		intersects = new Vector();
	}


	public int length() {
		if (orientation == 0) 
			return 1;
		else if (orientation == 1)
			return ((endIndex - startIndex)/15) + 1;
		else 
			return (endIndex - startIndex) + 1;
	}
}


