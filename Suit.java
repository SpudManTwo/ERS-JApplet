
public class Suit implements Comparable {
	private int order;
	private String name;
	public static final Suit SPADES=new Suit(4,"Spades");
	public static final Suit HEARTS=new Suit(3,"Hearts");
	public static final Suit DIAMONDS=new Suit(2,"Diamonds");
	public static final Suit CLUBS=new Suit(1,"Clubs");
	private Suit(int o,String n) {
		order=o;
		name=n;
	}
	public int compareTo(Object arg0) {
		if (arg0 instanceof Suit) {
			return order-((Suit)arg0).order;
		}
		else {
			throw new IllegalArgumentException("object compared to is not a suit.");
		}
	}
	public String toString() {
		return name;	
	}
}
