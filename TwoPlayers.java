import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;



public class TwoPlayers extends Game implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7079735666453795330L;
										//	GUI Compenent Declarations
	private JButton playerDeckButton; 	//Click this for player to flip card off of deck
	private JButton potButton; 			//AKA Slap Button
	private JButton cpuDeckButton; 		//Not really a Button but more of a display
	private static Container c; 		//What holds this all
										//	Pot and Deck Declarations
	private static Queue<Card> playerDeck; 		//The Players Deck of Cards
	private static Queue<Card> computerDeck; 	//The Computers Deck of Cards
	private static Stack<Card> pot; 			//The Stack of Cards that are in play
	private static CardIconHolder imageHolder; //Gets Display for Pot and Decks
											// Other Necessary Declarations
	private static int faceCountdown=-1; 	//A varaible that counts down for face cards
	private static TimerTask cpuActionTimer;//Timer for A.I.
	private boolean isComputerTurn=false; 	//A Variable that controls when the computer flips over a card
	public TwoPlayers(Container where,long cpu1) { //The Constructor
		Deck deck= new Deck();  //Creates Deck of Cards
		deck.shuffle(); //Shuffles Deck
		c=where; //Instiatates the content pane
		c.removeAll();
		c.setLayout(null); //Just some lsyout stuff
		c.setSize(500,500);
		pot = new Stack<Card>(); // instantistes the pot
		playerDeck = new LinkedList<Card>(); //instantiates the player's deck
		for (Card card:deck.deal(26)) //Deals the player 26 cards
			playerDeck.add(card);  
		computerDeck = new LinkedList<Card>(); //instantiates the computer's deck
		for (Card card:deck.deal(26)) //deals the computer the remaining 26 cards
			computerDeck.add(card); 
		URL codBase=null; //defines the code base for the program
		try {
			codBase = new URL("file:\\H:\\Advanced Topics\\ERS"); //instantiates the code base url 
		}
		catch (MalformedURLException e) {
			// Oh well
		}
		imageHolder= new CardIconHolder(codBase);
		playerDeckButton = new JButton();
		playerDeckButton.addActionListener(this);
		playerDeckButton.setIcon(imageHolder.getDeckIcon(Color.BLUE));
		playerDeckButton.setBounds(0,250,72,96);
		potButton = new JButton();
		potButton.addActionListener(this);
		//potButton.setIcon(imageHolder.getDeckIcon(Color.BLUE)); Pot is regular JButton when there are no cards on it
		potButton.setBounds(0,125,72,96);
		cpuDeckButton = new JButton();
		cpuDeckButton.setEnabled(false);
		cpuDeckButton.setIcon(imageHolder.getDeckIcon(Color.BLUE));
		cpuDeckButton.setBounds(0,0,72,96);
		c.add(potButton);
		c.add(playerDeckButton);
		c.add(cpuDeckButton);
		c.setVisible(true);
		
		//CPU Stuff
		cpuActionTimer= new TimerTask()
			{
				public void run() {
					if (pot.size()>0&&!isSlappable()&&isComputerTurn) { //Add Card to pot action
						if (computerDeck.size()>0) {
							Card card=computerDeck.remove();
							pot.add(card);
							imageHolder.setIcon(card);
							potButton.setIcon(imageHolder.getImageIcon());
							if (isFace()>0) {
								faceCountdown=isFace();
								isComputerTurn=false;
							}
							if (faceCountdown!=-1) {
								if (isFace()<0) {
									if(faceCountdown>1)
										faceCountdown--;
									else {
										for (int i=0;i<pot.size();i++)
											playerDeck.add(pot.pop());
										potButton.setIcon(null);
										faceCountdown=-1;
										isComputerTurn=false;;
										playerDeckButton.setEnabled(true);
									}
								}
							}
							else {
								isComputerTurn=false;
							}
						}
						else {
							endGame("Player");
						}
					}
					else if (pot.size()>0&&isSlappable()) {
						if (isSlappable()) {
							for (int i=0;i<pot.size();i++) {
								computerDeck.add(pot.pop());
							}
							potButton.setIcon(null);
							faceCountdown=-1;
							TwoPlayers.c.repaint();
							isComputerTurn=true;
						}
						else  {
							if (computerDeck.size()>0) {
								Card c = computerDeck.remove();
								c.turn();
								Stack<Card> pot2 = new Stack<Card>();
								pot2.add(c);
								for (Card card:pot)
									pot2.add(card);
								pot=pot2;
								isComputerTurn=true;
							}
							else {
								endGame("Player");
								isComputerTurn=false;
							}
						}
					}
				}
			};		
		   Timer timer = new Timer();
		   timer.schedule(cpuActionTimer,0,cpu1);
	}
	public void actionPerformed(ActionEvent event) {
		JButton clicked = (JButton)event.getSource();
		if (clicked==playerDeckButton&&!isComputerTurn) {
			if (playerDeck.size()>0) {
				Card card=playerDeck.remove();
				pot.add(card);
				imageHolder.setIcon(card);
				potButton.setIcon(imageHolder.getImageIcon());
				if (isFace()>0) {
					faceCountdown=isFace();
					isComputerTurn=true;
				}
				if (faceCountdown!=-1) {
					if (isFace()<0) {
						if(faceCountdown>1) {
							faceCountdown--;
						}
						else {
							for (int i=0;i<pot.size();i++)
								computerDeck.add(pot.pop());
							potButton.setIcon(null);
							faceCountdown=-1;
							isComputerTurn=true;
						}
					}
				}
				else {
					isComputerTurn=true;
				}
			}
			else {
				endGame("Computer");
			}
		}
		else {
			boolean slappable = isSlappable();
			if (slappable) {
				int j=pot.size();
				for (int i=0;i<j;i++)
					playerDeck.add(pot.pop());
				potButton.setIcon(null);
				faceCountdown=-1;
				c.repaint();
				isComputerTurn=false;
			}
			else if (pot.size()>0) {
				if (playerDeck.size()>0) {
					Card c = playerDeck.remove();
					c.turn();
					Stack<Card> pot2 = new Stack<Card>();
					pot2.add(c);
					for (Card card:pot)
						pot2.add(card);
					pot=pot2;
				}
				else {
					endGame("computer");
				}
			}
		}
	}
	private static void endGame(String winner) {
		c.removeAll();
		JLabel winnerShow=new JLabel("The "+winner+" won.");
		winnerShow.setBounds(125,125,125,125);
		c.add(winnerShow);
		c.repaint();
	}
	private static boolean isSlappable() {
		Card[] cards = new Card[3];
		int count;
		for (count=0;pot.size()>0&&count<3;count++) {
			Card temp=pot.pop();
			if (temp.isFaceUp()) {
				break;
			}
			else {
				cards[count]=temp;
			}
		}
		for (int i=2;i>-1;i--) {
			if (cards[i]!=null)
				pot.push(cards[i]);
		}
		if (count==3) {
			boolean tbr= isSandwich(cards)||isDouble(cards)||isFive(cards);
			cards = null;
			return tbr;
		}
		else {
			if (count==2) {
				boolean tbr= isFive(cards)||isDouble(cards);
				cards =null;
				return tbr;
			}
			else {
				if (count==1) {
					boolean tbr= isFive(cards);
					cards =null;
					return tbr;
				}
				else {
					return false;
				}
			}
		}
	}
	private static boolean isFive(Card[] cards) {
		return cards[0].compareTo(new Card(Suit.SPADES,5))==0;
	}
	private static boolean isDouble(Card[] cards) {
		return cards[0].compareTo(cards[1])==0;
	}
	private static boolean isSandwich(Card[] cards) {
		return cards[0].compareTo(cards[2])==0;
	}
	private int isFace() {
		if (pot.peek().compareTo(new Card(Suit.SPADES,1))==0) //Ace
			return 4;
		if (pot.peek().compareTo(new Card(Suit.SPADES,13))==0) //King
			return 3;
		if (pot.peek().compareTo(new Card(Suit.SPADES,12))==0) //Queen
			return 2;
		if (pot.peek().compareTo(new Card(Suit.SPADES,11))==0) //Jack
			return 1;
		return -1;
	}
}
