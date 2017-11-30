//playerTileCanvas.java

import java.awt.*;
import java.awt.event.*;

class playerTileCanvas extends Canvas implements MouseListener {

	int size = 100;
	int boardLogic logic;

	public playerTileCanvas(boardLogic logic) {
		this.logic = logic;
	}

	public void paint(Graphics g) {
		for (int i=0;i<7;i++) {
			int x = size*i;
			g.setColor(Color.black);
			g.drawRect(x,0,size,size);
			g.drawString(logic.p1Tiles[0], x, 0);
		}
		g.drawRect((7*size),0, size, size);
		g.drawString("Submit move", (7*size), 0);
	}

	public void mousePressed(MouseEvent event) {
		
		Point p = event.getPoint();
		int x = p.x;
		int y = p.y;
		if (x>=0 && x<=(7*size) && y>=0 && y<size) {
			//handle when one of the players tiles is selected;
			
			int tileIndex = x/size;
			logic.selectTile(tileIndex);
		} else if (x>=(7*size) && x<(8*size) && y >= 0 && y<size) 
			logic.submitMove();
	}

	//methods that are required by mouseListener interface;
 	public void mouseReleased(MouseEvent event) { }
    public void mouseClicked(MouseEvent event) { }
    public void mouseEntered(MouseEvent event) { }
    public void mouseExited(MouseEvent event) { }

}
			




