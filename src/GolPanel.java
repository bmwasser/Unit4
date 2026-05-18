import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JPanel;

public class GolPanel extends JPanel implements KeyListener, MouseListener{

	final int GRID_WIDTH = 500;
	final int GRID_HEIGHT = 500;
	final int CELL_SIZE = 10;
	int myVariable =  0;

	int[][] cells; 
	// put class & instance variables to control animation here
    int fps = 1;
	boolean pause = false;
	int rules = 0;

	public GolPanel() {
		setPreferredSize(new Dimension(GRID_WIDTH, GRID_HEIGHT));
		setBackground(Color.BLACK);
		cells = new int[GRID_HEIGHT / CELL_SIZE][GRID_WIDTH / CELL_SIZE];
		
		// set some initial cells for testing
		cells[24][24] = 1;
		cells[24][23] = 1;
		cells[24][25] = 1;
		cells[23][25] = 1;
		cells[22][24] = 1;
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public static int[][] updateCellsConway(int[][] cells){
		int neighbors = 0;
		int[][] nextGen = new int[cells.length][cells[0].length];
		for (int r = 1; r < nextGen.length - 1; r++){
			for (int c = 1; c < nextGen[0].length - 1; c++){
				neighbors = cells[r-1][c-1] + cells[r-1][c] + cells[r-1][c+1] + cells[r][c-1] + cells[r][c+1] + cells[r+1][c-1] + cells[r+1][c] + cells[r+1][c+1];
				if (neighbors == 2 && cells[r][c] == 1 || neighbors == 3) { 
                	nextGen[r][c] = 1; 
                	
            	} 
			}

		
		}
		return nextGen;
	}

	public static int[][] updateCellsB6S16(int[][] cells){
		int neighbors = 0;
		int[][] nextGen = new int[cells.length][cells[0].length];
		for (int r = 1; r < nextGen.length - 1; r++){
			for (int c = 1; c < nextGen[0].length - 1; c++){
				neighbors = cells[r-1][c-1] + cells[r-1][c] + cells[r-1][c+1] + cells[r][c-1] + cells[r][c+1] + cells[r+1][c-1] + cells[r+1][c] + cells[r+1][c+1];
				if (neighbors == 1 && cells[r][c] == 1 || neighbors == 6) { 
                	nextGen[r][c] = 1; 
                	
            	} 
			}

		
		}
		return nextGen;
	}

	public static int[][] updateCellsHighLife(int[][] cells){
		int neighbors = 0;
		int[][] nextGen = new int[cells.length][cells[0].length];
		for (int r = 1; r < nextGen.length - 1; r++){
			for (int c = 1; c < nextGen[0].length - 1; c++){
				neighbors = cells[r-1][c-1] + cells[r-1][c] + cells[r-1][c+1] + cells[r][c-1] + cells[r][c+1] + cells[r+1][c-1] + cells[r+1][c] + cells[r+1][c+1];
				if (neighbors == 2 && cells[r][c] == 1 || neighbors == 3 || neighbors == 6) { 
                	nextGen[r][c] = 1; 
                	
            	} 
			}

		
		}
		return nextGen;
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		int currentWidth = this.getWidth();
        int currentHeight = this.getHeight(); 
		
		// draw gridlines
		g.setColor(Color.DARK_GRAY);
		for (int y = 0; y < currentHeight; y+=CELL_SIZE){
			g.drawLine(0, y, currentWidth, y);
		}
		for (int x = 0; x < currentWidth; x+=CELL_SIZE){
			g.drawLine(x, 0, x, currentHeight);
		}
	
		//draw cells
		g.setColor(Color.LIGHT_GRAY);
		for (int r = 0; r < cells.length; r++){
			for (int c = 0; c < cells[0].length; c++){
				if (cells[r][c] > 0){
					g.fillRect(r * CELL_SIZE, c * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1); //figure out
				}
			}
		}
	}

	public void run() {
		while (true) {
			if (!pause){
				if (rules == 1){
					cells = updateCellsHighLife(cells);
					repaint(); 
				} else if (rules == 2){
					cells = updateCellsB6S16(cells);
					repaint(); 
				} else {
					cells = updateCellsConway(cells);
					repaint(); 
				}
			}
			delay(1000/fps);
		}
	}

	public void saveGameState(){
		String filename = "gol.cfg";
 		boolean append = false;  // false to overwrite, true to append
			try (PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter(filename, append)))){
   				// first line: csv game data, pause, fps, rule type, numRows, numCol
				if (rules == 1){
					out.println(fps + "," + pause + "," + GRID_WIDTH + "," + GRID_HEIGHT + "," + "HighLife Rule Set");
				} else if (rules == 2){
					out.println(fps + "," + pause + "," + GRID_WIDTH + "," + GRID_HEIGHT + "," + "B6S16 Rule Set");
				} else if (rules == 0){
					out.println(fps + "," + pause + "," + GRID_WIDTH + "," + GRID_HEIGHT + "," + "Conway Rule Set");
				}
				
				// Iterate across all rows, one row per line
				// Print each comlumn: 0 for each dead cell and a 1 for each live cell
				for (int r = 0; r < cells.length; r++){
					for (int c = 0; c < cells[0].length; c++){
						if (cells[r][c] == 1){
							out.print(1);
						} else {
							out.print(0);
						}
					}
					out.println();
				}

 		}
 		catch (IOException e) {
   			System.out.println(e);
		}
	}

	public void loadGameState(){
		String filename = "gol.cfg";
		try (Scanner fileIn = new Scanner(new File(filename))) {
    		String header = fileIn.nextLine();
        	String[] tokens = header.split(",");
        
        	fps = Integer.parseInt(tokens[0]);
        	pause = Boolean.parseBoolean(tokens[1]);
        	
			if (tokens[4].equals("HighLife Rule Set")){
				rules = 1;
			} else if (tokens[4].equals("B6S16 Rule Set")){
				rules = 2;
			} else if (tokens[4].equals("Conway Rule Set")){
				rules = 0;
			}
			
			int row = 0;
			while (fileIn.hasNext()) {
      			String line = fileIn.nextLine();
				for (int col = 0; col < line.length(); col++) {
                	char ch = line.charAt(col);
					cells[row][col] = ch - '0'; 
				}
				row++;
    		}
  		}
  		catch (FileNotFoundException e) {
    		System.out.println(e);
  		}	
	}

	public void delay(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'q'){
			System.out.println("The q key was typed");
		}
		if (e.getKeyChar() == 'h'){
			rules = 1;
		}
		if (e.getKeyChar() == 'b'){
			rules = 2;
		}
		if (e.getKeyChar() == 'c'){
			rules = 0;
		}
		if (e.getKeyChar() == 's'){
			saveGameState();
		}
		if (e.getKeyChar() == 'l'){
			loadGameState();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP){
			fps++;
		}
		if (fps > 1 && e.getKeyCode() == KeyEvent.VK_DOWN){
			fps--;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			pause = !pause;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println(fps);
		System.out.println(pause);
		if (rules == 1){
			System.out.println("High Life");
		}
		if (rules == 2){
			System.out.println("B6S16");
		}
		if (rules == 0){
			System.out.println("Conway");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// no operation
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// no operation
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// no operation
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// no operation
	}


}

