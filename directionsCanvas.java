//directionsCanvas.java

import java.awt.*;
import java.awt.event.*;

class directionsCanvas extends Canvas implements MouseListener {

	boardLogic logic;


	public directionsCanvas(boardLogic logic) {
		this.logic = logic;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		int width = 800;
		int height = 100;
		g.drawRect(0,0,width,height);
		g.drawString(logic.directions,0,0);
	}

	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		if (p.x>=0 && p.x<width && p.y>=0 && p.y<height) {
			if (logic.gameInProgress = false)
				logic.play();
		}
		
	
	
	};
	public void mouseRelease(MouseEvent event) {};
	public void mouseClicked(MouseEvent event) {};
	public void mouseEntered(MouseEvent event) {};
	public void mouseExited(MouseEvent event) {}:

}

