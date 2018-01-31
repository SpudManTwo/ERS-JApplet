import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
public class CardIconHolder{
	private static final long serialVersionUID = 5455443003382522779L;
	private URL codeBase;
	private ImageIcon cardDisplay;
	public CardIconHolder(URL codBase) {
		codeBase=codBase;
	}
	public void setIcon(Card c) {
		Deck d = new Deck();
		Card[] cards = d.deal(52);
		int pngNum;
		if(c.compareTo(cards[6])<0) { //Is less than a 7
			if (c.compareTo(cards[3])>0) { //Is greater than a 4
				if (c.compareTo(cards[4])>0) { //Is a 6
					pngNum=33;
				}
				else { //Is a 5
					pngNum=37;
				}
			}
			else if (c.compareTo(cards[3])<0) { //Less than a 4
				if (c.compareTo(cards[1])>0) { //Is a 3
					pngNum=45;
				}
				else if (c.compareTo(cards[1])<0) { //Is a Ace
					pngNum=1;
				}
				else { //Is a 2
					pngNum=49;
				}
			}
			else { //Is a 4
				pngNum=41;
			}
		}
		else if(c.compareTo(cards[6])>0) { //Is greater than a 7
			if (c.compareTo(cards[10])>0) { //Is greater than a Jack
				if (c.compareTo(cards[11])>0) { //Is a King
					pngNum=5;
				}
				else { //Is a Queen
					pngNum=9;
				}
			}
			else if (c.compareTo(cards[10])<0) { //Less than a Jack
				if (c.compareTo(cards[8])>0) { //Is a 10
					pngNum=17;
				}
				else if (c.compareTo(cards[8])<0) { //Is a 8
					pngNum=25;
				}
				else { //Is a 9
					pngNum=21;
				}
			}
			else { //Is a Jack
				pngNum=13;
			}
		}
		else { //Is a 7
			pngNum=29;
		}
		//Do Suit Stuff
		if (c.getSuit().compareTo(Suit.SPADES)==0) { //Is a Spades
				pngNum++;
		}
		else if(c.getSuit().compareTo(Suit.HEARTS)==0) { //Is a Hearts
			pngNum+=2;
		}
		else if (c.getSuit().compareTo(Suit.DIAMONDS)==0) { //Is a Diamond
			pngNum+=3;
		}
		URL imageLocation= codeBase;
		boolean threwError=false;
		try {
			imageLocation = new URL("file:\\L:\\Users\\Magic\\workspace\\ERS\\src"+"\\"+pngNum+".png");
		} 
		catch (MalformedURLException e) {
			threwError=true;
			e.printStackTrace();
		}
		if (!threwError)
			cardDisplay=new ImageIcon(imageLocation);
		else {
			System.out.println("Error Loading Image");
		}
	}
	public Icon getImageIcon() {
		return (Icon)cardDisplay;
	}
	public Icon getDeckIcon(Color request) {
		URL imageLocation = null;
		boolean threwError=false;
		try {
			if (request.equals(Color.blue))
				imageLocation = new URL("file:\\L:\\Users\\Magic\\workspace\\ERS\\src"+"\\"+"b2fv"/*b2fv is red b1fv is blue*/+".png");
			else
				imageLocation = new URL("file:\\L:\\Users\\Magic\\workspace\\ERS\\src"+"\\"+"b1fv"/*b2fv is red b1fv is blue*/+".png");
		} 
		catch (MalformedURLException e) {
			threwError=true;
			e.printStackTrace();
		}
		if (!threwError)
			return (Icon)(new ImageIcon(imageLocation));
		System.out.println("Error Loading Image");
		return null;
	}
}
