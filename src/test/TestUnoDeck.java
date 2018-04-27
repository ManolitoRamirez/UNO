package test;
import core.*;

import static org.junit.Assert.*;

import java.time.temporal.TemporalAmount;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Unodeck class.
 * 
 * @author Team #3
 */
public class TestUnoDeck 
{
	Unodeck testDeck;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Creating a test deck.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		testDeck = new Unodeck();
	}

	/**
	 * Removing test deck.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		testDeck = null;
	}
	
	/**
	 * Testing the peek card method.
	 */
	@Test
	public void testPeekCard()
	{
		Unocard temp = new Unocard();
		temp.setValue(4);
		testDeck.pushCard(temp);
		// ensuring the top card is the card that was pushed.
		assertEquals(4, testDeck.peekCard().getValue());
		testDeck.displayDeck();
	}

	/**
	 * Testing the pop card method.
	 */
	@Test
	public void testPopCard()
	{
		Unocard temp = new Unocard();
		temp.setValue(4);
		testDeck.pushCard(temp);
		// ensuring the top card is the card that was pushed.
		assertEquals(4, testDeck.popCard().getValue());
		testDeck.displayDeck();
	}
	
	/**
	 * Testing the fill deck method.
	 */
	@Test
	public void testFillDeck() 
	{
		testDeck.fillDeck();
		testDeck.displayDeck();
		// ensuring the top is a card and not null
		assertEquals(Unocard.class, testDeck.popCard().getClass());
		testDeck.displayDeck();
	}
	
	/**
	 * Testing the shuffle deck method.
	 */
	@Test
	public void testShuffleDeck()
	{
		testDeck.fillDeck();
		testDeck.shuffleDeck();
		// ensuring the top is a card and not null
		assertEquals(Unocard.class, testDeck.popCard().getClass());
		testDeck.displayDeck();
	}
	
	/**
	 * Testing the validateStart() method.
	 */
	@Test
	public void testValidateStart()
	{
		testDeck.fillDeck();
		testDeck.shuffleDeck();
		testDeck.validateStart();
		// ensuring the top card is not an action card
		if(testDeck.popCard().getValue() < 5)
			assertTrue(true);
		else
			fail();
		testDeck.displayDeck();
	}

}
