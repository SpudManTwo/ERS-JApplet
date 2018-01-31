
public class Card implements Comparable{
	private int r;
	private boolean isUp;
	private Suit s;
	public Card(Suit suit,int rank) {
		s = suit;
		r = rank;
		isUp=false;
	}
	public int getRank() {
		return r;
	}
	public Suit getSuit() {
		return s;
	}
	public boolean isFaceUp() {
		return isUp;
	}
	public void turn() {
		isUp=!isUp;
	}
	public boolean equals(Object otherCard) {
		if (otherCard instanceof Card) {
			return this.r==((Card)otherCard).r;
		}
		else {
			throw new IllegalArgumentException("object passed is not a card.");
		}
	}
	public int compareTo(Object otherCard) {
		if (otherCard instanceof Card) {
			return this.r-((Card)otherCard).r;
		}
		else {
			throw new IllegalArgumentException("object compared to is not a card.");
		}
	}
	private String rankToString() {
		if (r<2) {
			return "Ace";
		}
		else if(r<11) {
			return ""+r;
		}
		else if(r==11) {
			return "Jack";
		}
		else if(r==12) {
			return "Queen";
		}
		else  {
			return "King";
		}
	}
	public String toString() {
		return rankToString()+" of "+s.toString();
	}
}
