//directionsCanvas.java

import java.awt.*;
import java.awt.event.*;

class directionsCanvas extends Canvas implements MouseListener {

//	public boardLogic logic;
	protected int directionsX;
	protected int shuffleX;
	protected int skipX;
	protected int exchangeX;
	boolean shuffleClicked; 
	boolean skipClicked; 
	boolean exchangeClicked; 
	protected boardLogic logic; 
	

	public directionsCanvas() {
//		this.logic = logic;
		this.directionsX = 3;
		this.shuffleX = 270;
		this.skipX = 345;
		this.exchangeX = 428;
		shuffleClicked = false; 
		skipClicked = false; 
		exchangeClicked = false; 
		this.logic = scrabble.logic;
	}

	public static final Color LIGHTblue = new Color(0, 204, 255);
	public static final Color DARKblue = new Color(0, 75, 205); 
	public static final Color LIGHTred = new Color(255, 51, 51); 
	public static final Color DARKred = new Color(153, 0, 0); 
	public static final Color LIGHTyellow = new Color(255, 255, 153);
	public static final Color DARKyellow = new Color(255, 204, 0); 
	public static final Color DARKERyellow = new Color(153, 102, 0); 
	
	
	public void paint(Graphics g) {
		g.setFont(new Font("Impact", Font.BOLD, 12));
		g.setColor(LIGHTyellow);
		g.fillRoundRect(0,0,248,62,10,10);
		g.setColor(Color.black);
		g.drawRoundRect(0,0,248,62,10,10);
		if(shuffleClicked) 
			g.setColor(DARKERyellow);
		else 
			g.setColor(DARKyellow);
		g.fillRoundRect(248,0,82,62,10,10);
		g.setColor(Color.black);
		g.drawRoundRect(248,0,82,62,10,10);
		if(skipClicked) 
			g.setColor(DARKblue);
		else 
			g.setColor(LIGHTblue);
		g.fillRoundRect(330,0,83,62,10,10);
		g.setColor(Color.black);
		g.drawRoundRect(330,0,83,62,10,10);
		if(exchangeClicked) 
			g.setColor(DARKred);
		else 
			g.setColor(LIGHTred);
		g.fillRoundRect(413,0,83,62,10,10);
		g.setColor(Color.BLACK);
		g.drawRoundRect(413,0,83,62,10,10);
		g.drawString("Shuffle", shuffleX, 35);
		g.drawString("Skip Turn", skipX, 35);
	    g.drawString("Exchange", exchangeX, 35);
	    g.setFont(new Font("Impact", Font.PLAIN, 10));
	    g.drawString(logic.strDir, directionsX, 35);
	    //repaint(); 
	}

	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		if (p.x>=directionsX && p.x<shuffleX && p.y>=0 && p.y<100) {
			//System.out.println("logic.play()");
			if (logic.gameInProgress = false)
				logic.play();
		} else if (p.x>=shuffleX && p.x<skipX && p.y>=0 && p.y<100) {
			System.out.println("logic.shuffle()");
			shuffleClicked = true; 
			repaint(); 
			//logic.shuffle();
		} else if (p.x>=skipX && p.x<exchangeX && p.y>=0 && p.y<100) {
			System.out.println("logic.skip()");
			skipClicked = true;
			repaint(); 
			//logic.skip();
		}else if (p.x>=exchangeX && p.x<800 && p.y>=0 && p.y<100) {
			System.out.println("logic.exchange()");
			exchangeClicked = true; 
			repaint(); 
			//logic.exchange();
		}
	}

	public void mouseReleased(MouseEvent event) {
		shuffleClicked = false; 
		skipClicked = false; 
		exchangeClicked = false; 
		repaint(); 
	}
	public void mouseClicked(MouseEvent event) {};
	public void mouseEntered(MouseEvent event) {};
	public void mouseExited(MouseEvent event) {};

}

