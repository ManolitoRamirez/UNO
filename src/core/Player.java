package core;

/**
 * Player class for UNO game. Allows server to keep track of players' hands.
 * 
 * @version 1.0.0
 */
public class Player {

	Unocard[] hand = new Unocard[80];
	int handSize = 0;
	static int playerNum = 0;
	String playerName; // Simply 'Player 1' or 'Player 2'
	static int playerTurn; // If this is a 0, then it is player 2's turn, if it is a 1, then it is player 1's turn

	//===================================================================

	/**
	 * Setting up player's hand and name.
	 * @param d the deck
	 */
	public Player(Unodeck d) {
		++playerNum;
		playerName = "Player " + playerNum;

		// initiate hand
		for (int i = 0; i <= 6; i++) {
			hand[handSize] = d.popCard();
			handSize++;
		}
	}


	//===================================================================

	/**
	 * Displays hand to console.
	 */
	public void displayHand() {
		System.out.println("");
		for (int i = 0; i < handSize; ++i) {
			if (hand[i].getValue() <= 5) {
				/* displays the color and number if its a number card */
				System.out.print("[ " + hand[i].getColor() + " " + hand[i].getValue() + " ] ");
			} else {
				/* displays the color and the action if its an action card */
				System.out.print("[ " + hand[i].getColor() + " " + hand[i].getAction() + " ] ");
			}
		}
	}

	// ===================================================================

	/**
	 * Returns the player's hand size.
	 * @return handSize
	 */
	public int getHandSize(){
		return handSize;
	}

	// ===================================================================

	/**
	 * Returns a string array of the player's hand.
	 * @return cards
	 */
	public String [] getCardsInHand(){
		String [] cards = new String[handSize];

		for(int i = 0; i < handSize; i++){

			if (hand[i].getValue() <= 5) {
				cards[i] = hand[i].getColor() + "," + hand[i].getValue();
			} else {
				cards[i] = hand[i].getColor() + "," + hand[i].getAction();
			}
		}
		return cards;
	}

	// ===================================================================

	/**
	 * Returns a string of the player's hand. Takes in string array.
	 * @param theCards string array
	 * @return CardsInHand string
	 */
	public String sendCardsInHand(String [] theCards){
		String CardsInHand = "";
		for(int i = 0; i < theCards.length; i++){
			CardsInHand+=theCards[i]+":";
		}
		return CardsInHand;
	}


	// ===================================================================
	
	/** 
	 * Updates the handSize to have the index passed go away.
	 * @param indexToRemove the index of the card to remove.
	 */
	public void updateHandAfterPlay(int indexToRemove){

		Unocard[] newHand = new Unocard[80]; // remove one card for the play
		for (int i = 0; i < handSize; ++i) {
			if (i == (handSize - 1)) {
				// break if the handSize is equal to index
				break;
			} else if (i >= indexToRemove) {
				newHand[i] = hand[i+1]; // put the next card from hand into the new hand
			} else {
				newHand[i] = hand[i];
			}
		}

		--handSize;

		for (int i = 0; i < handSize; ++i) {
			hand[i] = newHand[i];
		}
	}

	// ===================================================================

	/**
	 * Adds new card to hand and increases hand size.
	 * @param drawnCard the card being added
	 */
	public void updateHandAfterDraw(Unocard drawnCard) {

		hand[handSize] = drawnCard;
		++handSize;

	}

	// ==================================================================
	
	/**
	 * Returns the name of player (ex. "Player #")
	 * @return playerName
	 */
	public String getName(){
		return playerName;
	}

}
