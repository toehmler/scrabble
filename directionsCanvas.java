//directionsCanvas.java

import java.awt.*;
import java.awt.event.*;

class directionsCanvas extends Canvas implements MouseListener {

//	public boardLogic logic;
	protected int directionsX;
	protected int shuffleX;
	protected int skipX;
	protected int exchangeX;

	public directionsCanvas() {
//		this.logic = logic;
		this.directionsX = 0;
		this.shuffleX = 248;
		this.skipX = 330;
		this.exchangeX = 413;
	}


	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0,0,248,62);
		g.drawRect(248,0,82,62);
		g.drawRect(330,0,83,62);
		g.drawRect(413,0,83,62);
		//g.drawString(logic.directions,directionsX,0);
		g.drawString("Directions go here. Press for output to cli.", directionsX, 30);
		g.drawString("Shuffle", shuffleX, 30);
		g.drawString("Skip Turn", skipX, 30);
	    g.drawString("Exchange", exchangeX, 30);
	}

	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		if (p.x>=directionsX && p.x<shuffleX && p.y>=0 && p.y<100) {
			System.out.println("logic.play()");
			//if (logic.gameInProgress = false)
			//	logic.play();
		} else if (p.x>=shuffleX && p.x<skipX && p.y>=0 && p.y<100) 
			//logic.shuffle();
			System.out.println("logic.shuffle()");
		else if (p.x>=skipX && p.x<exchangeX && p.y>=0 && p.y<100)
			System.out.println("logic.skip()");
			//logic.skip();
		else if (p.x>=exchangeX && p.x<800 && p.y>=0 && p.y<100) 
			System.out.println("logic.exchange()");
			//logic.exchange();
	}

	public void mouseReleased(MouseEvent event) {};
	public void mouseClicked(MouseEvent event) {};
	public void mouseEntered(MouseEvent event) {};
	public void mouseExited(MouseEvent event) {};

}

