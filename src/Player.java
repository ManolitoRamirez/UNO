public class Player {

	Unocard[] hand = new Unocard[80];
	int handSize = 0;
	static int playerNum = 0;
	String playerName; // Simply 'Player 1' or 'Player 2'
	static int playerTurn; // If this is a 0, then it is player 2's turn, if it is a 1, then it is player 1's turn

//===================================================================

/* Player constructor */
	public Player(Unodeck d) {
		++playerNum;
		playerName = "Player" + playerNum;
		
		// initiate hand
		for (int i = 0; i <= 6; i++) {
			hand[handSize] = d.popCard();
			handSize++;
		}
	}


//===================================================================
// Define methods

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

	public int getHandSize(){
		return handSize;
	}

// ===================================================================

	public String [] getCardsInHand(){
		String [] cards = new String[handSize];

		for(int i = 0; i < handSize; i++){

			if (hand[i].getValue() <= 5) {
				cards[i] = hand[i].getColor() + "," + hand[i].getValue();
			} else {
				if(hand[i].getAction() == "wild") {
					hand[i].setColor("black");
				}
				cards[i] = hand[i].getColor() + "," + hand[i].getAction();
			}
		}
		return cards;
	}

// ===================================================================

	public String sendCardsInHand(String [] theCards){
		String CardsInHand = "";
		for(int i = 0; i < theCards.length; i++){
			CardsInHand+=theCards[i]+":";
		}
		return CardsInHand;
	}


// ===================================================================
	/** update the handSize to have the index passed go away */
	public void updateHandAfterPlay(int indexToRemove){

		Unocard[] newHand = new Unocard[20]; // remove one card for the play
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

	public void updateHandAfterDraw() {

		Unocard[] newHand = new Unocard[hand.length - 2];

	}

// ===================================================================


	public void updateHandAfterDraw(Unocard drawnCard) {

		hand[handSize] = drawnCard;
		++handSize;

	}


// ==================================================================
	public String getName(){
		return playerName;
	}

}
