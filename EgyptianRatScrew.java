import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class EgyptianRatScrew extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6925209382545457290L;
	private static List<Card> playerDeck;
	private static List<Card> computerDeck;
	private static JButton slapButton;
	private static JButton flipButton;
	public EgyptianRatScrew() {
		playerDeck = new ArrayList<Card>();
		computerDeck = new ArrayList<Card>();
		Deck d = new Deck();
		for (Card c:d.deal(26))
			playerDeck.add(c);
		for (Card c:d.deal(26))
			computerDeck.add(c);
		Container c = getContentPane();
		c.setLayout(null);
		slapButton = new JButton("SLAP");
		flipButton = new JButton("Flip Next Card");
		
	}
	public void init() {
		SwingUtilities.invokeLater(new Runnable() { //Begins Invoke Later Runnable App Thingy
			public void run() { //Begins Run
				new EgyptianRatScrew(); //To Do in the Run
			} //Ends Run
		}); //Ends Invoke Later Runnable App Thingy
	}
}
