package test;
import core.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeckCreationTest 
{
	Unodeck testDeck;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		testDeck = new Unodeck();
	}

	@After
	public void tearDown() throws Exception 
	{
		testDeck = null;
	}

	@Test
	public void testDeckCreation() 
	{
		testDeck.fillDeck();

	}

}
