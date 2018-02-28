
import java.util.Random;

/* cards 0-5
 * 2 reverse (card # 6,7),
 * 2 draw two (card # 8, 9)
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 */

public class Unodeck {

	final int MAX_DECK_SIZE = 74;

	Unocard[] deck = new Unocard[MAX_DECK_SIZE];
	int deckSize = 0;

	//==================================================================================

	public Unocard peekCard() {
		return deck[deckSize]; // deckSize - 1
	}

	//==================================================================================

	public Unocard popCard() {
		deckSize--;
		return deck[deckSize];
	}

	//==================================================================================

	public void pushCard(Unocard c) {
		deck[deckSize + 1] = c;
		deckSize++;
	}

	//==================================================================================

	public void displayDeck() {
		System.out.println("");
		for (int i = 0; i < deckSize; ++i) {
			System.out.print(i + ": ");

			if ((deck[i].getValue() <= 5) && (deckSize > 0)) {
				/* displays the color and number if its a number card */
				System.out.println(deck[i].getColor() + " " + deck[i].getValue());
			} else {
				/* displays the color and the action if its an action card */
				System.out.println(deck[i].getColor() + " " + deck[i].getAction());
			}
		}
	}

	//==================================================================================



	// Adrian's fill deck method

	public void fillDeck()
	{
		String [] colors = {"red","blue","green","yellow"};
		String [] actions = {"skip","reverse","draw two","wild"};
		int [] nums = {0,1,2,3,4,5};
		int numIndex=0;
		int colorIndex=0;
		int actIndex=0;


		for(int j=0;j<4;j++)
		{

			for(int i=0;i<18;i++)
			{
				Unocard card = new Unocard();
				card.setColor(colors[colorIndex]);
				//Set Number Cards
				if(i<12)
				{
					card.setValue(nums[numIndex]);
					numIndex++;
					if(numIndex==6)numIndex=0;
				}
				else //Set Action Cards
				{
					card.setValue(6);
					card.setColor(colors[colorIndex]);
					card.setAction(actions[actIndex]);
					actIndex++;
					if(actIndex==3)actIndex=0;
				}

				if (j == 1) {
					deck[i+18]=card;
				} else if (j == 2) {
					deck[i+36]=card;
				} else if (j == 3) {
					deck[i+54]=card;
				} else deck[i]=card;


			}

			colorIndex++;

		}

		//Make Wild Cards
		Unocard card = new Unocard();
		card.setValue(6);
		card.setAction("wild");
		deck[72]=card;
//
//		card = new Unocard();
//		card.setValue(6);
//		card.setAction("wild");
		deck[73]=card;

		deckSize = MAX_DECK_SIZE;
	}

//==================================================================================
/* function that shuffles all of the cards */
	public void shuffleDeck() {

		int count = 0, thoroughness = 200, randCardPos;
		//Unocard tmp;
		Random randomNum = new Random();

		while (count < thoroughness) {

			Unocard tmp = new Unocard();
			randCardPos = randomNum.nextInt(MAX_DECK_SIZE);

			tmp = deck[randCardPos];
			deck[randCardPos] = deck[0];
			deck[0] = tmp;

			++count;

			}



		}

//==================================================================================
	public void validateStart() {
	    boolean flag = false;
	    while (!flag) {

	    	// If an action card, shuffle
	    	if (deck[deckSize - 1].getValue() > 5) {
	    		shuffleDeck();
	        } else flag = true; 					// else exit
	    }
	}


}
