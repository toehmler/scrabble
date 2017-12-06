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
	protected boardLogic logic; 
	
	public static final Color LIGHTyellow = new Color(255, 255, 153);
	
	public scoreCanvas() {
		this.scoreX = 15;
		this.p1X = 70;
		this.cmpX = 150;
		this.cwsX = 240;
		this.plX = 390;
		this.height = 50;
		this.width = 496;
		this.logic = scrabble.logic; 
	}
	
	public void paint(Graphics g) {
		g.setFont(new Font("Impact", Font.BOLD, 12));
		g.setColor(LIGHTyellow); 
		g.fillRect(0,0,width,height);
		g.setColor(Color.black);
		g.drawRect(0,0,width,height);
		g.drawString("SCORE", scoreX, height-20);
		g.drawRoundRect(p1X + 47, height-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(p1X + 47, height-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Player 1      "+String.valueOf(logic.p1score), p1X, height-20);
		g.drawRoundRect(cmpX + 58, height-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(cmpX + 58, height-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Computer      "+String.valueOf(logic.cmpScore), cmpX, height-20);
		g.drawRoundRect(cwsX + 100, height-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(cwsX + 100, height-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Current Word Score    0", cwsX, height-20);
		g.drawRoundRect(plX + 55, height-33, 22, 18, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRoundRect(plX + 55, height-33, 22, 18, 5, 5);
		g.setColor(Color.BLACK);
		g.drawString("Pieces left   0", plX, height-20);
		g.drawRect(0, 0, 235, 50);
	}

}
