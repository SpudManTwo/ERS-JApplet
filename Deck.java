import java.util.ArrayList;
import java.util.List;
public class Deck {
	private final int MAXSIZE=52;
	private List<Card> cards;
	public Deck() {
		reset();
	}
	public void reset() {
		cards = new ArrayList<Card>();
		for (int i=0;i<4;i++) {
			for (int c=1;c<14;c++) {
				if (i==0) {
					cards.add(new Card(Suit.SPADES,c));
				}
				if (i==1) {
					cards.add(new Card(Suit.HEARTS,c));
				}
				if (i==2) {
					cards.add(new Card(Suit.DIAMONDS,c));
				}
				if (i==3) {
					cards.add(new Card(Suit.CLUBS,c));
				}
			}
		}
	}
	public void shuffle() {
		if (isFull()) {
			List<Integer> forbiddenNumbers = new ArrayList<Integer>();
			List<Card> randomDeck = new ArrayList<Card>();
			for (int i=0;i<MAXSIZE;i++) {
				int random=(int) (Math.random()*52);
				while  (forbiddenNumbers.contains(random))
					random=(int) (Math.random()*52);
				randomDeck.add(cards.get(random));
				forbiddenNumbers.add(random);
			}
			cards=randomDeck;
		}
		else {
			throw new IllegalArgumentException("Not a full deck");
		}
	}
	private boolean isFull() {
		return cards.size()==MAXSIZE;
	}
	public boolean isEmpty() {
		return cards.size()==0;
	}
	public int size() {
		return cards.size();
	}
	public Card deal() {
		if (!isEmpty()) {
			return cards.remove(0);
		}
		else {
			throw new IllegalArgumentException("Deck is empty");
		}
	}
	public Card[] deal(int numberOfCards) {
		if (!isEmpty()&&cards.size()>=numberOfCards) {
			Card[] toBeReturned = new Card[numberOfCards];
			for (int i=0;i<numberOfCards;i++) {
				toBeReturned[i] = cards.remove(0);
			}
			return toBeReturned;
		}
		else {
			throw new IllegalArgumentException("Deck doesn't contain enough cards");
		}
	}
	public String toString() {
		String str="";
		for (Card c:cards) {
			str+="\n"+c.toString();
		}
		return str;
	}
}
