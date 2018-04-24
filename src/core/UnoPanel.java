package core;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class UnoPanel extends JFrame implements UnoConstants, Runnable {

	// Instantiate game variables
	public int handSize = 7;
	int player;

	// while playing variables
	boolean myTurn = false;
	boolean waiting = true;
	String currentSelectedCard; // should be in the form (color,value)
	boolean continueToPlay = true;
	boolean isValidPlay = false;
	boolean skip = false;
	boolean skippedOpponent = false;
	Thread thread;
	public String colorChosen;
	public String wildCardColor;

	// Private
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private String host = "localhost";
	private int port = 8000;

	// Jframe
	private JPanel contentPane;


	// Runtime variables
	// Initiation variables
	String opponentName;
	int opponentCardCount;
	String topDiscardCard;
	String playersHand;
	static boolean gameStarted = false;
	private int status;
	private int checkStatus;

	/** had to make all these up here to manipulate in the functions */
	// panels
	JPanel GameBoardPanel;
	JPanel GameMenuPanel;
	JPanel HelpPanel;
	JLabel otherPlayerName;
	JLabel topDiscard;
	JLabel GameMenuLabel;
	JLabel otherPlayerhandSize;

	// buttons
	JButton btnGame;
	JButton btnHelp;
	JButton btnExit;
	JButton drawButton;
	JButton connect;
	JButton play;
	JButton btnPlaythiscard;

	// slider
	JSlider slider;

	// Labels
	JLabel leftCard;
	JLabel rightCard;
	JLabel selectedCardLabel;

	// Start of Main ===============================================
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() { // Start of run ====================
				try {
					UnoPanel frame = new UnoPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End of run ========================================
		});
	} // End of main ===============================================


	public UnoPanel() {
		Init();
	}

	// Create the frame ============================================
	public void Init() {

		// Creates main window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panels ====================================================

		GameBoardPanel = new JPanel();
		GameBoardPanel.setBackground(Color.LIGHT_GRAY);
		GameBoardPanel.setBounds(0, 0, 1066, 618);
		contentPane.add(GameBoardPanel);
		GameBoardPanel.setLayout(null);

		GameMenuPanel = new JPanel();
		GameMenuPanel.setBackground(new Color(0, 255, 0));
		GameMenuPanel.setForeground(new Color(0, 255, 0));
		GameMenuPanel.setBounds(0, 0, 1066, 618);
		contentPane.add(GameMenuPanel);
		GameMenuPanel.setLayout(null);

		otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setForeground(Color.WHITE);
		otherPlayerName.setIcon(new ImageIcon("./UI/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);

		// other player hand size
		otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setForeground(Color.WHITE);
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		GameBoardPanel.add(otherPlayerhandSize);

		// Buttons ===================================================
		btnGame = new JButton("Game");

		// Main Screen Help
		btnHelp = new JButton("Help");
		btnHelp.setBounds(0, 47, 64, 38);
		contentPane.add(btnHelp);

		// Main Screen Exit
		btnExit = new JButton("Exit");
		btnExit.setBounds(0, 90, 64, 38);
		contentPane.add(btnExit);

		// Game button
		btnGame.setBounds(0, 6, 64, 38);
		contentPane.add(btnGame);

		drawButton = new JButton("Draw");
		drawButton.setForeground(UIManager.getColor("Button.light"));
		drawButton.setBackground(UIManager.getColor("Button.light"));
		drawButton.setBounds(799, 249, 165, 245);
		GameBoardPanel.add(drawButton);

		connect = new JButton("Find a game");
		connect.setBounds(389, 342, 287, 82);

		play = new JButton("Play Game");
		play.setBounds(389, 342, 287, 82);

		// play this card button
		btnPlaythiscard = new JButton("PlayThisCard");
		btnPlaythiscard.setBounds(490, 530, 117, 29);
		GameBoardPanel.add(btnPlaythiscard);

		GameBoardPanel.setVisible(false);
		// Add play button to GameMenuPanel
		// GameMenuPanel.add(connect); // may take off
		GameMenuPanel.add(play);
		GameMenuPanel.setVisible(true);

		// Slider =========================================
		slider = new JSlider();
		slider.setMinimum(0);
		slider.setMaximum(6);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);

		topDiscard = new JLabel("");
		topDiscard.setBounds(109, 249, 165, 245);
		GameBoardPanel.add(topDiscard);

		// Cards In Hand ======================================
		selectedCardLabel = new JLabel("");
		selectedCardLabel.setBounds(490, 249, 117, 190);
		GameBoardPanel.add(selectedCardLabel);

		leftCard = new JLabel("");
		leftCard.setBounds(395, 275, 117, 190);
		GameBoardPanel.add(leftCard);

		rightCard = new JLabel("");
		rightCard.setBounds(585, 275, 117, 190);
		GameBoardPanel.add(rightCard);

		HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1066, 618);
		contentPane.add(HelpPanel);
		HelpPanel.setVisible(false);

		//  =========       GAME HELP SCREEN BACKGROUND =========
		HelpPanel.setOpaque(false);
		JLabel helpBackground = new JLabel(new ImageIcon("./gameCards/HelpMenu.jpg"));
		helpBackground.setBounds(0, 0, 1166, 596);
		HelpPanel.add(helpBackground);

		// ================================================== GUI BACKGROUNDS AND PICS ====================================

		//	=========       GAME BACKGROUND    =================
		JLabel gameBackground = new JLabel(new ImageIcon("./gameCards/Background.jpg"));
		gameBackground.setBounds(0, 0, 1166, 596);
		contentPane.add(gameBackground);

		//  ==========       MENU BACKFROUND    =================
		GameMenuPanel.setOpaque(false);
		JLabel menuBackground = new JLabel(new ImageIcon("./gameCards/GameMenu.jpg"));
		menuBackground.setBounds(0, 0, 1166, 596);
		GameMenuPanel.add(menuBackground);

		//  =========       GAME SCREEN CLEAR BACKGROUND ========
		GameBoardPanel.setOpaque(false);

		//  =========        RIGHT & LEFT CARDS _ FACEDOWN ======
		try {
			BufferedImage flippedCardImage = ImageIO.read(new File("./gameCards/FaceDown.png"));

			Image theResizedCardImageForFlippedCards =
					flippedCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);

			ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForFlippedCards);

			leftCard.setIcon(theFlippedCardIcon);

			rightCard.setIcon(theFlippedCardIcon);

			//  =========    DRAW BUTTON ==================
			drawButton.setOpaque(false);
			Image theResizedCardImageForDrawButton =
					flippedCardImage.getScaledInstance(drawButton.getWidth(), drawButton.getHeight(),Image.SCALE_DEFAULT);

			ImageIcon theDrawButtonIcon = new ImageIcon(theResizedCardImageForDrawButton);

			drawButton.setIcon(theDrawButtonIcon);
			//  ==========    MIDDLE CARD INITIAL ICON ========
			selectedCardLabel.setIcon(theFlippedCardIcon);
			//  ==========
			topDiscard.setIcon(theFlippedCardIcon);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}



		// Action Listeners ============================================================================


		// Action listener if user wants to exit
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close dialog code
				JDialog.setDefaultLookAndFeelDecorated(true);
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit game",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
					System.out.println("No button clicked");
				} else if (response == JOptionPane.YES_OPTION) {
					try {
						toServer.writeBoolean(false);
						toServer.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				} else if (response == JOptionPane.CLOSED_OPTION) {
					System.out.println("JOptionPane closed");
				} // End of close dialog code
			}
		});


		//------------------------------------------------------------------------------------


		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				HelpPanel.setVisible(false); //panel_1 is red

				if(gameStarted){
					GameMenuPanel.setVisible(false);
					GameBoardPanel.setVisible(true); // panel_2 is blue
				}else{
					GameMenuPanel.setVisible(true); // panel is green
					GameBoardPanel.setVisible(false);
				}
			}
		});


		//------------------------------------------------------------------------------------


		btnPlaythiscard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				// check to make sure its a valid play
				validatePlay(currentSelectedCard, topDiscardCard);

				// client wants to play this card and its valid
				if (isValidPlay) {
					status = PLAYCARD;
					waiting = false;

				} else { // Invalid Card Error dialog

					String [] e1 = topDiscardCard.split(",");
					String eColor = e1[0];
					String eVal = e1[1];
					if (eVal.equals("wild")){
						JOptionPane.showMessageDialog(null,
								"Please play a card that's " + eColor,
								"Invalid Card",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null,
								"Please play a card that's either " + eColor +" Or is " + eVal,
								"Invalid Card",
								JOptionPane.ERROR_MESSAGE);
					}

				}

				isValidPlay = false;
			}
		});


		//------------------------------------------------------------------------------------


		// drawButton will act everytime a card is drawn.
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = DRAW;
				waiting = false;
			}
		});

		//------------------------------------------------------------------------------------


		// play goes to the panel with the game
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// brings up the game panel
				gameStarted = true;
				play.setVisible(false);
				GameMenuPanel.setVisible(false);
				HelpPanel.setVisible(false);
				GameBoardPanel.setVisible(true);

				connectToServer();

			}
		});


		//------------------------------------------------------------------------------------

		// play goes to the panel with the game -DEPRICATED
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				// find a game button disappears, play button appears -GUI-
				connect.setVisible(false);
				connect.setEnabled(false);
				GameMenuPanel.add(play);
				play.setVisible(true);

			}
		});


		//------------------------------------------------------------------------------------


		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GameMenuPanel.setVisible(false); // panel is green
				HelpPanel.setVisible(true); //panel_1 is red
				GameBoardPanel.setVisible(false);

			}
		});


		//------------------------------------------------------------------------------------

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// should return the card in the hand @ that pos
				String [] cardsInHand = playersHand.split(":");
				if(slider.getValue() <= cardsInHand.length - 1) {
					currentSelectedCard = cardsInHand[slider.getValue()];
				}

				// print the current selected card
				System.out.println(currentSelectedCard);

				BufferedImage selectedCardImage = null;

				try {

					selectedCardImage = ImageIO.read(new File("./gameCards/"+currentSelectedCard+".jpg"));

				} catch (IOException e1) {

				}

				Image theResizedCardImageForSelectedCard =
						selectedCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);
				ImageIcon theSelectedCardIcon = new ImageIcon(theResizedCardImageForSelectedCard);

				selectedCardLabel.setIcon(theSelectedCardIcon);

				if(slider.getValue() == 0)
				{
					GameBoardPanel.remove(leftCard);
					GameBoardPanel.updateUI();
				}
				else
				{
					try {

						selectedCardImage = ImageIO.read(new File("./gameCards/"+cardsInHand[slider.getValue() - 1]+".jpg"));

					} catch (IOException e1) {

					}
					theResizedCardImageForSelectedCard = selectedCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);
					theSelectedCardIcon = new ImageIcon(theResizedCardImageForSelectedCard);
					leftCard.setIcon(theSelectedCardIcon);
					GameBoardPanel.add(leftCard);
					GameBoardPanel.updateUI();
				}
				if(slider.getValue() == cardsInHand.length - 1)
				{
					GameBoardPanel.remove(rightCard);
					GameBoardPanel.updateUI();
				}
				else
				{
					try {

						selectedCardImage = ImageIO.read(new File("./gameCards/"+cardsInHand[slider.getValue() + 1]+".jpg"));

					} catch (IOException e1) {

					}
					theResizedCardImageForSelectedCard = selectedCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);
					theSelectedCardIcon = new ImageIcon(theResizedCardImageForSelectedCard);
					rightCard.setIcon(theSelectedCardIcon);
					GameBoardPanel.add(rightCard);
					GameBoardPanel.updateUI();
				}
			}
		});


	}

	// Begin defining functions ==========================================================



	@Override
	public void run() {

		receiveInitialData();

		try {

			System.out.print("\nPlayer" + player + " ENTERING WHILE LOOP\n");

			while (continueToPlay) {

				if (player == PLAYER1) {
					System.out.println("Player hand:\n" + playersHand);
					slider.setMaximum(playersHand.split(":").length - 1);
					if(!skip)
					{
						System.out.print("\nPlayer" + player + " make a move\n");
						// wait for player 1 to make a move
						waitForPlayerAction();

						System.out.print("\nPlayer" + player + " Sending move to server\n");
						// Send the move to the server
						sendMove();
					}

					if(!skippedOpponent)
					{
						System.out.print("\nPlayer" + player + " Waiting to recieve to move from server\n");
						// recieve update from server of player2's move
						receiveInfoFromServer();
					}

				} else if (player == PLAYER2) {

					System.out.println("Player hand:\n" + playersHand);
					slider.setMaximum(playersHand.split(":").length - 1);

					if(!skippedOpponent)
					{
						System.out.print("\nPlayer" + player + " Waiting to recieve to move from server\n");
						// recieve update from server of player2's move
						receiveInfoFromServer();
					}

					if(!skip)
					{
						System.out.print("\nPlayer" + player + " make a move\n");
						// wait for player 1 to make a move
						waitForPlayerAction();

						System.out.print("\nPlayer" + player + " Sending move to server\n");
						// Send the move to the server
						sendMove();
					}

				}
			}
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}







	}


	//------------------------------------------------------------------------------------


	private void sendMove() {

		skippedOpponent = false;
		myTurn = false;
		drawButton.setEnabled(myTurn);
		btnPlaythiscard.setEnabled(myTurn);

		// check to see if action card
		if (currentSelectedCard.contains("draw two") && status != DRAW) {
			status = DRAW_TWO;
		} else if (currentSelectedCard.contains("wild") && status != DRAW) {
			status = WILD;
		} else if (currentSelectedCard.contains("skip") && status != DRAW || currentSelectedCard.contains("reverse") && status != DRAW) {
			status = SKIP;
		}

		/** send the move to the server */
		if (status == PLAYCARD) { // Play card

			try {
				// Send status to server that client wants to play a card
				toServer.writeInt(PLAYCARD); // UnoServer:176, path:1
				toServer.flush();

				// send the index of the card to play to the server
				toServer.writeInt(slider.getValue()); // UnoServer:182
				toServer.flush();

				// read the new hand after the play
				playersHand = fromServer.readUTF(); // UnoServer:191

				// displays the "You Win!" if player
				if (playersHand.equals("")) {
					showWinner("You");
				}

				// get the new topDiscard
				topDiscardCard = fromServer.readUTF(); // UnoServer:195

				// ============================== DISPLAY NEW CARDS =========================
				String [] receivedCards = playersHand.split(":");
				String middleCard = receivedCards[receivedCards.length/2];

				try {

					BufferedImage middleCardImage = ImageIO.read(new File("./gameCards/"+middleCard+".jpg"));

					Image theResizedCardImageForMiddleCard =
							middleCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForMiddleCard);

					selectedCardLabel.setIcon(theFlippedCardIcon);

				} catch (IOException e) {

				}

				try {

					BufferedImage topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

					Image theResizedCardImageFortopDiscard =
							topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

					topDiscard.setIcon(topDiscardIcon);

				} catch (IOException e) {

				}
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}

		} else if (status == DRAW) { // PlayDraw

			try {

				System.out.println("Cards read from server before draw: " + playersHand);

				//Send to server that client wants to draw a card
				toServer.writeInt(DRAW); // UnoServer:176, path:2
				toServer.flush();

				//Send to server that client wants to draw a card
				playersHand = fromServer.readUTF(); // UnoServer:216

				// test to see if they hand updated correctly
				System.out.println("Cards read from server after draw: " + playersHand);

			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		} else if (status == DRAW_TWO) {
			try {
				// Send status to server that client wants to play a card
				toServer.writeInt(DRAW_TWO); // UnoServer:176, path:1
				toServer.flush();

				// send the index of the card to play to the server
				toServer.writeInt(slider.getValue()); // UnoServer:182
				toServer.flush();

				// read the new hand after the play
				playersHand = fromServer.readUTF(); // UnoServer:191
				
				slider.setMaximum(playersHand.split(":").length - 1);
				
				// displays the "You Win!" if player
				if (playersHand.equals("")) {
					showWinner("You");
				}

				// get the new topDiscard
				topDiscardCard = fromServer.readUTF(); // UnoServer:195

				// read opponents hand size
				int tmp = fromServer.readInt();
				otherPlayerhandSize.setText(Integer.toString(tmp));

				// ============================== DISPLAY NEW CARDS =========================
				String [] receivedCards = playersHand.split(":");
				String middleCard = receivedCards[receivedCards.length/2];


				try {

					BufferedImage middleCardImage = ImageIO.read(new File("./gameCards/"+middleCard+".jpg"));

					Image theResizedCardImageForMiddleCard =
							middleCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForMiddleCard);

					selectedCardLabel.setIcon(theFlippedCardIcon);

				} catch (IOException e) {

				}

				try {

					BufferedImage topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

					Image theResizedCardImageFortopDiscard =
							topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

					topDiscard.setIcon(topDiscardIcon);

				} catch (IOException e) {

				}


			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (status == WILD){
			try {
				// Send status to server that client wants to play a card
				toServer.writeInt(WILD); // UnoServer:176, path:1
				toServer.flush();

				// Send the color of the color chosen from the wild card
				toServer.writeUTF(wildCardColor);

				// send the index of the card to play to the server
				toServer.writeInt(slider.getValue()); // UnoServer:182
				toServer.flush();

				// read the new hand after the play
				playersHand = fromServer.readUTF(); // UnoServer:191

				// displays the "You Win!" if player
				if (playersHand.equals("")) {
					showWinner("You");
				}

				// get the new topDiscard
				topDiscardCard = fromServer.readUTF(); // UnoServer:195

				// ============================== DISPLAY NEW CARDS =========================
				String [] receivedCards = playersHand.split(":");
				String middleCard = receivedCards[receivedCards.length/2];

				try {

					BufferedImage middleCardImage = ImageIO.read(new File("./gameCards/"+middleCard+".jpg"));

					Image theResizedCardImageForMiddleCard =
							middleCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForMiddleCard);

					selectedCardLabel.setIcon(theFlippedCardIcon);

				} catch (IOException e) {

				}

				try {

					BufferedImage topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

					Image theResizedCardImageFortopDiscard =
							topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

					topDiscard.setIcon(topDiscardIcon);

				} catch (IOException e) {

				}

			} catch(IOException ex) {
				ex.printStackTrace();
			}
		} else if(status == SKIP) {
			try {
				// Send status to server that client wants to play a card
				toServer.writeInt(SKIP); // UnoServer:176, path:1
				toServer.flush();

				// send the index of the card to play to the server
				toServer.writeInt(slider.getValue()); // UnoServer:182
				toServer.flush();

				// read the new hand after the play
				playersHand = fromServer.readUTF(); // UnoServer:191

				// displays the "You Win!" if player
				if (playersHand.equals("")) {
					showWinner("You");
				}

				// get the new topDiscard
				topDiscardCard = fromServer.readUTF(); // UnoServer:195

				// ============================== DISPLAY NEW CARDS =========================
				String [] receivedCards = playersHand.split(":");
				String middleCard = receivedCards[receivedCards.length/2];

				try {

					BufferedImage middleCardImage = ImageIO.read(new File("./gameCards/"+middleCard+".jpg"));

					Image theResizedCardImageForMiddleCard =
							middleCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForMiddleCard);

					selectedCardLabel.setIcon(theFlippedCardIcon);

				} catch (IOException e) {

				}

				try {

					BufferedImage topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

					Image theResizedCardImageFortopDiscard =
							topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

					ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

					topDiscard.setIcon(topDiscardIcon);

				} catch (IOException e) {

				}

				skippedOpponent = true;

			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}



	//------------------------------------------------------------------------------------


	public void receiveInfoFromServer() throws IOException {

		myTurn = false;

		playersHand = fromServer.readUTF();
		slider.setMaximum(playersHand.split(":").length - 1);

		status = fromServer.readInt();
		System.out.println("STATUS_CODE: " + status);

		checkStatus(status);

		drawButton.setEnabled(myTurn);
		btnPlaythiscard.setEnabled(myTurn);
		int tmp = 0;

		// get the play from the user
		topDiscardCard = fromServer.readUTF();
		System.out.print("Top discarded Card: " + topDiscardCard);

		BufferedImage topDiscardCardImage = null;


		try {

			topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

		} catch (IOException e) {

		}

		Image theResizedCardImageFortopDiscard =
				topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

		ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

		topDiscard.setIcon(topDiscardIcon);

		// get the new hand of the other player
		tmp = fromServer.readInt();
		otherPlayerhandSize.setText(Integer.toString(tmp));

		// ============================== DISPLAY NEW CARDS =========================
		String [] receivedCards = playersHand.split(":");
		slider.setMaximum(receivedCards.length - 1);
		for(int i = 0; i < receivedCards.length - 1; i++) {
			String[] temp = receivedCards[i].split(",");
			if(receivedCards[1].equals("wild")) {
				receivedCards[i] = "black,wild";
			}
		}
		System.out.println(Arrays.toString(receivedCards));

		if(status == SKIP)
			skip = true;
		else
			skip = false;
	}

	//------------------------------------------------------------------------------------

	private void waitForPlayerAction() throws InterruptedException {

		myTurn = true;
		drawButton.setEnabled(myTurn);
		btnPlaythiscard.setEnabled(myTurn);

		while (waiting) {
			Thread.sleep(100);
		}

		waiting = true;
	}

	//------------------------------------------------------------------------------------


	private void connectToServer() {
		// --------------- Connect to server ----------------------------
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket(host, port); // localhost:8000

			// Create IO streams to input/output data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			toServer =  new DataOutputStream(socket.getOutputStream() );
		}
		catch (IOException ex) {
			System.out.println(ex.toString());
		}

		// Control the game on a separate thread
		Thread thread = new Thread(this);
		thread.start();

		// -----------------------------------------------------------------

	}

	//------------------------------------------------------------------------------------

	// These functions should be in the GameLogic.java file

	private String validatePlay(String pCurrentCard, String pLastPlayedCard) {
		String [] played = pCurrentCard.split(",");
		String [] checkAgainst = pLastPlayedCard.split(",");

		// checks the current selected card against the card last played
		if (played[1].equals("wild")) {
			System.out.println("wildCard played!");
			isValidPlay = true;
			// select a new color to play
			wildCardColor = wildDialog();
			while (wildCardColor.equals("No option chosen")) {
				wildCardColor = wildDialog();
			}

			return colorChosen;
		} else if (played[0].equals(checkAgainst[0])) {
			System.out.println("Colors match!");
			isValidPlay = true;

		} else if (played[1].equals(checkAgainst[1])) {
			System.out.println("Values match!");
			isValidPlay = true;

		}  else {
			System.out.println("Invalid play!");
			isValidPlay = false;
		}

		return null;
	}

	//------------------------------------------------------------------------------------


	public void receiveInitialData() {

		// set the player to the player number they are
		try {
			player = fromServer.readInt(); // UnoServer:52 | UnoServer:64
			// set the opponents label and turn
			if (player == PLAYER1) {
				otherPlayerName.setText("Player 2");
				myTurn = true; // set player1's turn to true

			} else {
				otherPlayerName.setText("Player 1");
				btnPlaythiscard.setEnabled(myTurn);
				drawButton.setEnabled(myTurn);
			}

			// recieve card amount of opponent from server
			opponentCardCount = fromServer.readInt(); // UnoServer:277
			otherPlayerhandSize.setText(Integer.toString(opponentCardCount));

			// recieves the correct card that was initially discarded
			topDiscardCard = fromServer.readUTF(); // UnoServer:280
			System.out.println("card to play:\n" + topDiscardCard);

			// recieves the players delt hand
			playersHand = fromServer.readUTF(); // UnoServer:283
			
			slider.setMaximum(playersHand.split(":").length - 1);
			System.out.println("Player hand:\n" + playersHand);

			// ============================== DISPLAY INITIAL CARDS =========================
			String [] receivedCards = playersHand.split(":");
			String middleCard = receivedCards[receivedCards.length/2];
			currentSelectedCard = middleCard;

			try {

				BufferedImage middleCardImage = ImageIO.read(new File("./gameCards/"+middleCard+".jpg"));
				Image theResizedCardImageForMiddleCard =
						middleCardImage.getScaledInstance(selectedCardLabel.getWidth(), selectedCardLabel.getHeight(),Image.SCALE_DEFAULT);


				ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForMiddleCard);

				selectedCardLabel.setIcon(theFlippedCardIcon);

			} catch (IOException e) {

			}
			try {

				BufferedImage topDiscardCardImage = ImageIO.read(new File("./gameCards/"+topDiscardCard+".jpg"));

				Image theResizedCardImageFortopDiscard =
						topDiscardCardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);

				ImageIcon topDiscardIcon = new ImageIcon(theResizedCardImageFortopDiscard);

				topDiscard.setIcon(topDiscardIcon);

			} catch (IOException e) {

			}

			// =====================================================================================================

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//------------------------------------------------------------------------------------


	private void showWinner(String winner) {

		JOptionPane.showMessageDialog(null,
				winner + " won!",
				"GAME OVER!",
				JOptionPane.ERROR_MESSAGE);
	}



	//------------------------------------------------------------------------------------
	// if condition to check to see if it is win lose or tie
	public void checkStatus(int newStatus) {

		if (newStatus == PLAYER1_WON) {

			continueToPlay = false;
			System.out.println("PLAYER1_WON");
			showWinner("Player1");

		} else if (newStatus == PLAYER2_WON) {

			continueToPlay = false;
			System.out.println("PLAYER2_WON");
			showWinner("Player2");

		} else if (newStatus == DRAW_GAME) {

			continueToPlay = false;
			System.out.println("DRAW");
			showWinner("No one");
		}

	}

	//------------------------------------------------------------------------------------


	public String wildDialog(){
		Object[] colors = {"blue", "red", "yellow", "green"};
		String response = (String)JOptionPane.showInputDialog(
				null,null,
				"Choose Color",
				JOptionPane.PLAIN_MESSAGE,
				null,
				colors,
				"blue");

		//If a string was returned, say so.
		if ((response != null) && (response.length() > 0)) {
			return response;
		}
		return "No option chosen";
	}
}
