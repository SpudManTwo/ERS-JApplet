import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


public class CardIconHolderAppletTest extends JApplet implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Icon cardDisplay;
	private static Deck deck;
	private static CardIconHolder imageHolder;
	private JButton nextCard;
	private static Card[] realDeck;
	private static int count;
	public CardIconHolderAppletTest() {
		Container c=getContentPane();
		nextCard = new JButton();
		c.setLayout(null);
		deck = new Deck();
		deck.shuffle();
		count=0;
		realDeck=deck.deal(52);
		URL codBase=null;
		try {
			codBase = new URL("file:\\L:\\Users\\Magic\\workspace\\ERS\\src");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageHolder= new CardIconHolder(codBase);
		Card firstCard;
		firstCard=realDeck[0];
		count=1;
		imageHolder.setIcon(firstCard);
		cardDisplay=imageHolder.getImageIcon();
		nextCard.addActionListener(this);
		nextCard.setIcon(cardDisplay);
		c.setSize(500,500);
		nextCard.setBounds(0,0,72,96);
		c.add(nextCard);
		c.setVisible(true);
	}
	public void actionPerformed(ActionEvent arg0) {
		Card nextC;
		System.out.println(deck.toString());
		if (count<52) {
			nextC=realDeck[count];
			count++;
		}
		else {
			count=1;
			deck.reset();
			deck.shuffle();
			realDeck = deck.deal(52);
			nextC=realDeck[0];
		}
		imageHolder.setIcon(nextC);
		cardDisplay=imageHolder.getImageIcon();
		nextCard.setIcon((Icon)cardDisplay);	
	}
}
