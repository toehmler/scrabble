//playerTileCanvas.java

import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.awt.Stroke; 

class playerTileCanvas extends Canvas implements MouseListener {

	protected int size;
	boolean [] clicked = {false, false, false, false, false, false, false, false};
	boolean submitClicked; 
	protected boardLogic logic;

	public playerTileCanvas() {
		this.logic = scrabble.logic;
		this.size = 62;
		submitClicked = false; 
	}

	public static Hashtable<String, Integer> initHash() {
		Hashtable<String, Integer> tiles = new Hashtable<String, Integer>(); 
		tiles.put("A", 1); 
		tiles.put("B", 2); 
		tiles.put("C", 3); 
		tiles.put("D", 2); 
		tiles.put("E", 1); 
		tiles.put("F", 4); 
		tiles.put("G", 2); 
		return tiles; 
	}
	
	public static final Color GREEN = new Color(0, 180, 0);
	public static final Color DARKgreen = new Color(0, 102, 0);
	
	public void paint(Graphics g) {
		Hashtable<String, Integer> tiles = initHash(); 
		Object[] letters = tiles.keySet().toArray();
		for (int i=0;i<7;i++) {	
			int x = size*i;
			g.setColor(Color.black);
			g.drawRoundRect(x,0,size,size, 10, 10);
			if(clicked[i]) {
				g.setColor(Color.LIGHT_GRAY);
			}else {
				g.setColor(Color.WHITE);
			}
			g.fillRoundRect(x, 0, size, size, 10, 10);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(String.valueOf(letters[i]), x + 25, 40);
			g.setFont(new Font("Arial", Font.PLAIN, 9));
			g.drawString(String.valueOf(tiles.get(letters[i])), x + 55, 10);
		}
		g.drawRoundRect((7*size),0, size, size, 10, 10);
		if(submitClicked)
			g.setColor(DARKgreen);
		else
			g.setColor(GREEN);
		g.fillRoundRect((7*size),0, size, size, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Impact", Font.BOLD, 10));
		g.drawString("Submit Move!", (7*size) + 2, 35);
	}

	public void mousePressed(MouseEvent event) {
		Point p = event.getPoint();
		int x = p.x;
		int y = p.y;
		if (x>=0 && x<=(7*size) && y>=0 && y<size) {
			//handle when one of the players tiles is selected;
			int tileIndex = x/size;
			//System.out.println("selectTile("+tileIndex+")");
			clicked[tileIndex] = true; 
    			repaint(); 
    			logic.selectTile(tileIndex);
    			repaint(); 
		} else if (x>=(7*size) && x<(8*size) && y >= 0 && y<size) {
			System.out.println("submitMove()");
			submitClicked = true; 
			repaint();
			logic.submitCurrentWord();
			repaint(); 
		}
	}

 	public void mouseReleased(MouseEvent event) { 
 		Point p = event.getPoint();
		int x = p.x;
    		int tileIndex = x/size;
    		clicked[tileIndex] = false; 
    		submitClicked = false; 
    		repaint(); 
 	}
 	
 	//methods that are required by mouseListener interface;
    public void mouseClicked(MouseEvent event) { }
    public void mouseEntered(MouseEvent event) { }
    public void mouseExited(MouseEvent event) { }

}
