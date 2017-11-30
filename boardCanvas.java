//boardCanvas.java

import java.awt.*;
import java.awt.event.*;

class boardCanvas extends Canvas implements MouseListener {
	
	//instance vars to keep track of number of boxes per row
	//height and width of boxes
	//border around the canvas
	int size = 50;
	int numTiles = 100;
	int numPerRow = 10;
	int borderWidth = 25;
	int boardLogic logic;

	public boardCanvas(boardLogic logic) {
		this.logic = logic;
	}
	
	public void paint(Graphics g) {
		for (int i=0;i<numTiles;i++) {
			//draw a grid of 100 boxes 
			int x = borderWidth + (size*i);
			int y = borderWidth + ((i/numPerRow)*size);
			g.setColor(Color.black);
			g.drawRect(x,y,size,size);
		}
	}

	//add a method for drawing a centered string

	public void mousePressed(MouseEvent event) {
		//handle events for when board is pressed
		Point p = event.getPoint();
		int x = p.x - border;
		int y = p.y - border;
		if (x>=0 && x<(numPerRow*size) && y>= 0 && y<((numTiles/numPerRow)*size)) {
			int tileIndex = ((y/size)*10) + (x/size);
			logic.placeTile(tileIndex);
			g.drawString(logic.board[tileIndex].letterVal, x, y);
		}
	}

	//methods that are requried byb mouseListener interface
	public void mouseReleased(MouseEvenet event) { }
	public void mouseClicked(MouseEvent event) { }
	public void mouseEntered(MouseEvent event) { }
	public void mouseExited(MouseEvent event) { }
}



		
			

			
	

