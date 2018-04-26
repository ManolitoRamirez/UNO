package test;
import core.*;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the Player class.
 * 
 * @author Ryan Kirmis
 */
public class TestPlayer 
{
	Unodeck testDeck;
	Player testPlayer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}

	/**
	 * Creating an test deck, filling it, shuffling it, and creating a player using the deck.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		testDeck = new Unodeck();
		testDeck.fillDeck();
		testDeck.shuffleDeck();
		testPlayer = new Player(testDeck);
	}

	/**
	 * Removing the test deck and test player.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		testDeck = null;
		testPlayer = null;
	}

	/**
	 * Testing the updateHandAfterPlay(), updateHandAfterDraw(), and getHandSize() methods.
	 */
	@Test
	public void testHand()
	{
		// testing initial hand size (7)
		System.out.println("Testing hand size");
		testPlayer.displayHand();
		assertEquals(testPlayer.getHandSize(), 7);

		// testing hand size after removing index 6
		testPlayer.updateHandAfterPlay(6);
		assertEquals(testPlayer.getHandSize(), 6);
		testPlayer.displayHand();

		// testing hand size after drawing a new card
		testPlayer.updateHandAfterDraw(new Unocard());
		assertEquals(testPlayer.getHandSize(), 7);
		testPlayer.displayHand();

		// testing hand size after removing index 0
		testPlayer.updateHandAfterPlay(0);
		assertEquals(testPlayer.getHandSize(), 6);
		testPlayer.displayHand();
	}

	/**
	 * Testing the sendCardsInHand() method.
	 */
	@Test
	public void testSendCardsInHand()
	{
		// creating a string array of cards
		String [] cards = {"red 5", "yellow 3", "blue 5"};
		// passing array to method
		String cardsString = testPlayer.sendCardsInHand(cards);
		// should return cards in string format red 5:yellow 3:blue 5
		assertTrue(cardsString.equals("red 5:yellow 3:blue 5:"));
	}
	
	/**
	 * Testing the getCardsInHand() method.
	 */
	@Test
	public void testGetCardsInHand()
	{
		// re-filling deck (no shuffle)
		testDeck.fillDeck();
		testPlayer = new Player(testDeck);
		
		// testing that method gives correct cards
		String [] cardsInHand = testPlayer.getCardsInHand();
		assertTrue(cardsInHand[0].equals("black,wild"));
	}
}
