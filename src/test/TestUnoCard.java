package test;
import core.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Unocard class.
 * 
 * @author Team #3
 */
public class TestUnoCard 
{
	Unocard testCard;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}

	/**
	 * Creating a test card.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		testCard = new Unocard();
	}

	/**
	 * Removing the test card.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		testCard = null;
	}

	/**
	 * Testing the set/get action methods.
	 */
	@Test
	public void testAction() 
	{
		testCard.setAction("wild");
		assertTrue(testCard.getAction().equals("wild"));
	}

	/**
	 * Testing the set/get value methods.
	 */
	@Test
	public void testValue() 
	{
		testCard.setValue(1);
		assertEquals(1, testCard.getValue());
	}

	/**
	 * Testing the set/get color methods.
	 */
	@Test
	public void testColor() 
	{
		testCard.setColor("red");
		assertTrue(testCard.getColor().equals("red"));
	}

	/**
	 * Testing the toString() method.
	 */
	@Test
	public void testToString() 
	{
		assertTrue(testCard.toString().equals("black,-1"));
		testCard.setValue(5);
		assertTrue(!testCard.toString().equals("black,default"));
		testCard.setValue(6);
		assertTrue(testCard.toString().equals("black,default"));
	}

	/**
	 * Testing the displayCard() method.
	 */
	@Test
	public void testDisplayCard()
	{
		testCard.displayCard();
		testCard.setValue(5);
		testCard.displayCard();
		testCard.setValue(6);
		testCard.displayCard();
	}
}
