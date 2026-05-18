import javax.swing.JFrame;
public class GolRunner {
	
	public static void main(String[] args) {
	    JFrame f = new JFrame("Conway's Game of Life"); 
	    GolPanel p = new GolPanel();
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.add(p);
	    f.pack();
	    f.setVisible(true);
	    p.setFocusable(true);
	    p.requestFocusInWindow();
	    p.run();
	}
}