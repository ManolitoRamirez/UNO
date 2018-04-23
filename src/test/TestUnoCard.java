package test;
import core.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the UnoCard class.
 * @author Ryan Kirmis
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

	@Before
	public void setUp() throws Exception 
	{
		testCard = new Unocard();
	}

	@After
	public void tearDown() throws Exception 
	{
		testCard = null;
	}

	@Test
	public void testAction() 
	{
		testCard.setAction("wild");
		assertTrue(testCard.getAction().equals("wild"));
	}

	@Test
	public void testValue() 
	{
		testCard.setValue(1);
		assertEquals(1, testCard.getValue());
	}

	@Test
	public void testColor() 
	{
		testCard.setColor("red");
		assertTrue(testCard.getColor().equals("red"));
	}

	@Test
	public void testToString() 
	{
		assertTrue(testCard.toString().equals("black,-1"));
		testCard.setValue(5);
		assertTrue(!testCard.toString().equals("black,default"));
		testCard.setValue(6);
		assertTrue(testCard.toString().equals("black,default"));
	}

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
