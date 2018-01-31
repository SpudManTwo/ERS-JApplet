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
import javax.swing.JButton;
import javax.swing.JLabel;



public class TwoPlayers2 extends Game implements ActionListener {
	
										//	GUI Compenent Declarations
	private static JButton playerDeckButton; 	//Click this for player to flip card off of deck
	private static JButton potButton; 			//AKA Slap Button
	private static JButton cpu1DeckButton; 		//Not really a button, it just holds the cpu's deck
	private static Container c; 				//What holds this all
	private static CardIconHolder imageHolder; 	//Gets Display for Pot and Decks
	
										//	Pot and Deck Declarations
	private static Queue<Card> playerDeck; 		//The Players Deck of Cards
	private static Queue<Card> computer1Deck;   //The Computer's Deck of Cards
	private static Stack<Card> pot; 			//The Stack of Cards that are in play
	
										// Other Necessary Declarations
	private static TimerTask cpu1ActionTimer;	//TimerTask for A.I.
	private static Timer timer; 				//Timer to operate cpu1ActionTimer
	private static boolean isCompTurn=false; 	//A Variable that controls when the computer flips over a card
	
										//Face Card Interaction Stuff
	private static int faceCountdown=-1; 		//A varaible that counts down for face cards
	private static TimerTask delayer;			//TimerTask for delaying after Face Cards
	private static Timer delayTimer;			//Timer to operate delayer
	private static boolean hasDelayed=true;		//Stops Computer or player from adding more cards when delaying
	private static int playerToTake=0; 			//Useful for Face card countdown taking
	
	private static Timer potHighlightTimer;
	private static TimerTask potHighlight;
	private static boolean isChanging=true;
	
	private static long waitTime;
	public TwoPlayers2(Container where,long cpu1,boolean highlighter) { //The Constructor
		Deck deck= new Deck();  						//Creates Deck of Cards
		deck.shuffle(); 								//Shuffles Deck
		c=where; 										//Stores it's container locally
		c.removeAll();									//Clears out it's container's previous contents
		c.setLayout(null); 								//Just some layout stuff
		c.setSize(500,500);
		
		pot = new Stack<Card>(); 						// instantistes the pot
		playerDeck = new LinkedList<Card>();	 		//instantiates the player's deck
		computer1Deck = new LinkedList<Card>(); 		//Instatiates the computer's deck
		
		for (Card card:deck.deal(26))	 				//Deals the player 26 cards
			playerDeck.add(card);  
		for (Card card:deck.deal(26))	 				//deals the computer 26 cards
			computer1Deck.add(card); 
		
		URL codBase=null; 								//defines the code base for the program
		try {											//instantiates the code base url 			
			codBase = new URL("file:\\H:\\Advanced Topics\\ERS"); 
		}
		catch (MalformedURLException e) {
														// Oh well No pics for you
		}
		imageHolder=new CardIconHolder(codBase);		//Instantiates the CardIconHolder using codBase as it's Code Base
		
		playerDeckButton = new JButton();				//Instantiates the Player's Deck Button
		playerDeckButton.addActionListener(this);		//Adds the action listener
		playerDeckButton.setIcon(imageHolder.getDeckIcon(Color.BLUE));
//		playerDeckButton.setIcon(imageHolder.getDeckIcon(Color.RED)); //Uncomment to make it red
		playerDeckButton.setBounds(125,250,100,100);
		
		potButton = new JButton(); //Instantiates pot/slap button
		potButton.addActionListener(this); //Adds the action listener
		potButton.setBounds(125,125,100,100);
		
		cpu1DeckButton = new JButton(); //Instantiates the computer's deck button
		cpu1DeckButton.setEnabled(false);//Disables the player from clicking the computer's deck
		cpu1DeckButton.setIcon(imageHolder.getDeckIcon(Color.BLUE));
//		playerDeckButton.setIcon(imageHolder.getDeckIcon(Color.RED)); //Uncomment to make it red
		cpu1DeckButton.setBounds(125,0,100,100);
		
		highlight(0); //Highlights the player's deck to start
		
		c.add(potButton); //Adds all the components to the container
		c.add(playerDeckButton);
		c.add(cpu1DeckButton);
		c.setVisible(true);
		
		//CPU Stuff
		cpu1ActionTimer= new TimerTask()
			{
				public void run() { //Stuff that happens when the computer reacts
					if (hasDelayed&&!isSlappable()&&isCompTurn) { //Add Card to pot action
						
						if (computer1Deck.size()>0) { //Makes sure the computer has cards
							
							Card card=computer1Deck.remove(); //Takes the card from the computer
							pot.add(card);	//Adds it to the pot
							
							imageHolder.setIcon(card);
							potButton.setIcon(imageHolder.getImageIcon()); //Updates the pot's image
							
							if (isFace()>0) { //Checks if the card just played was a face card
								
								faceCountdown=isFace(); //Sets the faceCountdown
								isCompTurn=false; //Makes it the players turn
								TwoPlayers2.highlight(0); //Highlights the players deck
							}
							
							else if (faceCountdown!=-1) { //If the faceCountdown was started
								
								if (isFace()<0) { //If  you didn't turn up a face card
									
									if(faceCountdown>1) //If You still have any chances left
										
										faceCountdown--;
									
									else { //If you're out of chances
										
										delay(); //Delay for possible slaps
										playerToTake=0; //Gets ready to give it to the player
									}
								}
							}
							
							else { //If nothing else applies aka no facecountdown or no face card flipped
								
								isCompTurn=false; //Makes it the players turn
								TwoPlayers2.highlight(0); //Highlights the players deck
							}
						}
						else {
							
							TwoPlayers2.cpu1DeckButton.setVisible(false); //If the computer is out of cards
							endGame("Player"); //End the game for the player
						}
					}
					else if (isSlappable()) { //If the pot is slappable
							
							for (int i=0;i<pot.size();i++) //Gives the computer the pot
								
								computer1Deck.add(pot.pop());
							
							potButton.setIcon(null); //resets the pot
							faceCountdown=-1;
							highlight(1);
							isCompTurn=true;
							TwoPlayers2.c.repaint();
							if (!hasDelayed) {
								delayTimer.cancel();	
								delayTimer.purge();
								hasDelayed=true;
							}
					}
				}
			};
			if (highlighter) {
				potHighlightTimer = new Timer();
				potHighlight = new TimerTask() {
					public void run() {
						if (isSlappable()) {
							if (!isChanging) {
								potButton.setBackground(Color.yellow);
								c.repaint();
							}
							isChanging=true;
						}
						else {
							if (isChanging) {
								potButton.setBackground(null);
								c.repaint();
							}
							isChanging=false;
						}
					}	
				};
				potHighlightTimer = new Timer();
				potHighlightTimer.schedule(potHighlight,0,1);
			}
			waitTime = cpu1;
			timer = new Timer();
			delayTimer = new Timer();
			timer.schedule(cpu1ActionTimer,0,waitTime);
	}
	public void actionPerformed(ActionEvent event) { //If the player acts
		
		JButton clicked = (JButton)event.getSource(); //Gets the source
		if (clicked==playerDeckButton&&!isCompTurn) { //If you clicked your deck during your turn
			
			if (playerDeck.size()>0) { //If you have cards left
				
				Card card=playerDeck.remove(); 
				pot.add(card);
				
				imageHolder.setIcon(card);
				potButton.setIcon(imageHolder.getImageIcon());
				c.repaint();
				
				if (isFace()>0) {
					
					faceCountdown=isFace();
					isCompTurn=true;
					TwoPlayers2.highlight(1);
				}
				if (faceCountdown!=-1) {
					if (isFace()<0) {
						if(faceCountdown>1) {
							faceCountdown--;
						}
						else {
							delay();
							playerToTake=1;
						}
					}
				}
				else {
					isCompTurn=true;
					TwoPlayers2.highlight(1);
				}
			}
			else {
				playerDeckButton.setVisible(false);
				endGame("computer");
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
				if (!hasDelayed) {
					delayTimer.cancel();	
					delayTimer.purge();
					hasDelayed=true;
				}
				isCompTurn=false;
				TwoPlayers2.highlight(0);
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
				else 
					endGame("computer");
			}
		}
		c.repaint();
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
		for (count=0;!pot.isEmpty()&&count<3;count++) {
			
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
	private void delay() {
		
		hasDelayed=false;		
		delayer = new TimerTask() {
			
				public void run() {
					
					TwoPlayers2.resume();
					
				}
			};
			
		delayTimer = new Timer();
		delayTimer.schedule(delayer,0,waitTime);
	}
	private static void resume() {
		delayTimer.cancel();	
		delayTimer.purge();
		hasDelayed=true;
		if (playerToTake==0) {
			
			for (int i=0;i<pot.size();i++)
				
				playerDeck.add(pot.pop());
			
			potButton.setIcon(null);
			faceCountdown=-1;
			isCompTurn=false;
			TwoPlayers2.highlight(0);
		}
		if (playerToTake==1) {
			
			for (int i=0;i<pot.size();i++)
				
				computer1Deck.add(pot.pop());
			
			potButton.setIcon(null);
			faceCountdown=-1;
			isCompTurn=true;
			TwoPlayers2.highlight(1);
		}
	}
	private static void highlight(int playerNum) {
		if (playerNum==0) {
			playerDeckButton.setBackground(Color.yellow);
			cpu1DeckButton.setBackground(null);
		}
		else {
			playerDeckButton.setBackground(null);
			cpu1DeckButton.setBackground(Color.yellow);
		}
		c.repaint();
	}
}