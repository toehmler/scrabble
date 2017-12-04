//scoreCanvas.java

import java.awt.*;

public class scoreCanvas extends Canvas {

	protected int scoreX;
	protected int p1X;
	protected int cmpX;
	protected int cwsX;
	protected int plX;
	protected int height;
	protected int width;

	public scoreCanvas() {
		this.scoreX = 15;
		this.p1X = 70;
		this.cmpX = 140;
		this.cwsX = 230;
		this.plX = 400;
		this.height = 50;
		this.width = 496;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0,0,width,height);
		g.drawString("SCORE", scoreX, height-20);
		g.drawString("Player 1: 0", p1X, height-20);
		g.drawString("Computer: 0", cmpX, height-20);
		g.drawString("Current Word Score: 0", cwsX, height-20);
		g.drawString("Pieces left: 0", plX, height-20);
	}

}





