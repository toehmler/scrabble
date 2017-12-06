import java.awt.*;

public class tile {

	public String letterVal;
	public int numVal;
	public int mult;

	public tile() {
		//constructor for tiles on the board
		letterVal = "";
		numVal = 0;
		mult = 1;
	}

	public tile(String s) {
		//constructor for players tiles
		letterVal = s;
		numVal = 0;
		mult = 1;
	}

	public tile(int m) {
		//constructor for tiles with a multiplier
		letterVal = null;
		numVal = 0;
		mult = 1;
	}




	public boolean hasVal() {
		if (letterVal == null) 
			return false;
		else 
			return true;
	}

	public boolean isEmpty() {
		return !hasVal();
	}


}

	

