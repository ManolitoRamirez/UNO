package test;
import core.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the Player class.
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

	@Before
	public void setUp() throws Exception 
	{
		testDeck = new Unodeck();
		testDeck.fillDeck();
		testDeck.shuffleDeck();
		testPlayer = new Player(testDeck);
	}

	@After
	public void tearDown() throws Exception 
	{
		testDeck = null;
		testPlayer = null;
	}
	
	@Test
	public void testHand()
	{
		System.out.println("Testing hand size");
		testPlayer.displayHand();
		assertEquals(testPlayer.getHandSize(), 7);
		
		testPlayer.updateHandAfterPlay(6);
		assertEquals(testPlayer.getHandSize(), 6);
		testPlayer.displayHand();
		
		testPlayer.updateHandAfterDraw(new Unocard());
		assertEquals(testPlayer.getHandSize(), 7);
		testPlayer.displayHand();
		
		testPlayer.updateHandAfterPlay(0);
		assertEquals(testPlayer.getHandSize(), 6);
		testPlayer.displayHand();
		
		System.out.println(testPlayer.getCardsInHand());
	}
	
	@Test
	public void testSendCardsInHand()
	{
		String [] cards = {"red 5", "yellow 3", "blue 5"};
		String cardsString = testPlayer.sendCardsInHand(cards);
		assertTrue(cardsString.equals("red 5:yellow 3:blue 5:"));
	}
}
