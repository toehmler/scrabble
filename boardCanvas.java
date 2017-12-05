//boardCanvas.java

import java.awt.*;
import java.awt.event.*;

class boardCanvas extends Canvas implements MouseListener {
	
	//instance vars to keep track of number of boxes per row
	//height and width of boxes
	//border around the canvas
	protected int size;
	protected int numTiles;
	protected int numPerRow;
	protected int borderWidth;

	//	int boardLogic logic;

	public boardCanvas() {
		this.size = 30;
		this.numTiles = 225;
		this.numPerRow = 15;
		this.borderWidth = 23; 
	}
	
	public static final Color LIGHTred = new Color(255,102,102);
	public static final Color LIGHTyellow = new Color(255, 255, 230);
	  
	public void paint(Graphics g) {
		g.drawRect(0, 0, 496, 600);
		for (int i=0;i<numTiles;i++) {
			//draw a grid of 100 boxes 
			int x = borderWidth + ((i%15)*size);
			int y = borderWidth + ((i/numPerRow)*size);
			if(i == 112) {
				g.setColor(LIGHTred);
				g.fillRect(x, y, size, size);
				g.setColor(Color.black);
				g.drawRect(x, y, size, size);
			}else {
			g.setColor(LIGHTyellow);
			g.fillRect(x, y, size, size);
			g.setColor(Color.black);
			g.drawRect(x, y, size, size);
			}
		}
	}

	//add a method for drawing a centered string

	public void mousePressed(MouseEvent event) {
		//handle events for when board is pressed
		Point p = event.getPoint();
		int x = p.x - borderWidth;
		int y = p.y - borderWidth;
		if (x>=0 && x<(numPerRow*size) && y>= 0 && y<((numTiles/numPerRow)*size)) {
			int rowNum = (y/size); //starting at row 0
			int colNum = (x/size); //starting at row 0
			int tileIndex = (15*rowNum) + colNum; //indicies range from 0-224
			System.out.println("placeTile("+tileIndex+")");
	//		logic.placeTile(tileIndex);
	//		g.drawString(logic.board[tileIndex].letterVal, x, y);
		}
	}

	//methods that are requried byb mouseListener interface
	public void mouseReleased(MouseEvent event) { }
	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }
}
