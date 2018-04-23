package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DeckCreationTest.class, TestPlayer.class, TestUnoCard.class })
public class AllTests {

}
