import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Menu extends JApplet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237335232850181080L;
	private static Container c;
	private JButton oneComputer;
	private JButton twoComputers;
	private JButton threeComputers;
	private JButton start;
	private JButton yes;
	private JButton no;
	private JLabel highlight;
	private JLabel highlight2;
	private JLabel numOfComputers;
	private JLabel difficulty;
	private JLabel difficulty2;
	private JTextField computer1;
	private JTextField computer2;
	private JTextField computer3;
	private int numOfComps;
	public Menu() {
		c=getContentPane();
		c.setSize(500,500);
		c.setLayout(null);
		numOfComputers=new JLabel("Number of Computers");
		numOfComputers.setBounds(0,0,200,12);
		
		oneComputer=new JButton("1");
		oneComputer.setBounds(0,20,100,25);
		oneComputer.addActionListener(this);
		
		twoComputers=new JButton("2");
		twoComputers.setBounds(0,50,100,25);
		twoComputers.addActionListener(this);		

		threeComputers=new JButton("3");
		threeComputers.setBounds(0,80,100,25);
		threeComputers.addActionListener(this);	
		
		start=new JButton("Start Game");
		start.setBounds(0,100,100,25);
		start.addActionListener(this);
		
		difficulty = new JLabel("Enter each computer's reaction ");
		difficulty2=new JLabel("time in seconds (1 = Hard,3 = Easy)");
		difficulty.setBounds(0,0,200,12);
		difficulty2.setBounds(0,12,200,12);
		
		computer1=new JTextField("0");
		computer1.setBounds(0,25,200,25);
		
		computer2=new JTextField("0");
		computer2.setBounds(0,50,200,25);
		
		computer3=new JTextField("0");
		computer3.setBounds(0,75,200,25);
		
		yes=new JButton("Yes");
		yes.setBounds(0,50,100,25);
		yes.addActionListener(this);
		
		no=new JButton("No");
		no.setBounds(0,150,100,25);
		no.addActionListener(this);
		
		highlight = new JLabel("Would you like the pot ");
		highlight2=new JLabel("to be highlighted");
		highlight.setBounds(0,0,200,15);
		highlight2.setBounds(0,20,200,15);
		
		c.add(numOfComputers);
		c.add(oneComputer);
		c.add(twoComputers);
		c.add(threeComputers);
	}
	public void actionPerformed(ActionEvent event) {
		Game game=null;
		if (game==null) {
			//Nevermind
		}
		if (!(event.getSource() instanceof JButton))
			return;
		JButton clicked = (JButton)(event.getSource());
		if (clicked==oneComputer) {
			numOfComps=1;
			c.removeAll();
			c.add(difficulty);
			c.add(difficulty2);
			c.add(computer1);
			c.add(start);
		}
		else if (clicked==twoComputers) {
			numOfComps=2;
			c.removeAll();
			c.add(difficulty);
			c.add(difficulty2);
			c.add(computer1);
			c.add(computer2);
			c.add(start);
		}
		else if (clicked==threeComputers) {
			numOfComps=3;
			c.removeAll();
			c.add(difficulty);
			c.add(difficulty2);
			c.add(computer1);
			c.add(computer2);
			c.add(computer3);
			c.add(start);
		}
		else if (clicked==start) {
			if (Long.parseLong(computer1.getText())>=3||Long.parseLong(computer2.getText())>=3||Long.parseLong(computer1.getText())>=3) {
				c.removeAll();
				c.add(yes);
				c.add(no);
				c.add(highlight);
				c.add(highlight2);
			}	
			else {
				if (numOfComps==1)
					game = new TwoPlayers2(c,Long.parseLong(computer1.getText())*1000,clicked==yes);			
				if (numOfComps==2)
					game = new ThreePlayers2(c,Long.parseLong(computer1.getText())*1000,Long.parseLong(computer2.getText())*1000,clicked==yes);
				if (numOfComps==3)
					game = new FourPlayers2(c,Long.parseLong(computer1.getText())*1000,Long.parseLong(computer2.getText())*1000,Long.parseLong(computer3.getText())*1000,clicked==yes);
			}
		}
		else {
			if (numOfComps==1)
				game = new TwoPlayers2(c,Long.parseLong(computer1.getText())*1000,clicked==yes);			
			if (numOfComps==2)
				game = new ThreePlayers2(c,Long.parseLong(computer1.getText())*1000,Long.parseLong(computer2.getText())*1000,clicked==yes);
			if (numOfComps==3)
				game = new FourPlayers2(c,Long.parseLong(computer1.getText())*1000,Long.parseLong(computer2.getText())*1000,Long.parseLong(computer3.getText())*1000,clicked==yes);
		}
		c.repaint();
	}
}
