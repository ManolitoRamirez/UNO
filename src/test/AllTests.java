package test;

/**
 * Runs all test cases for the non-GUI classes (Player, Unodeck, and Unocard).
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestUnoDeck.class, TestPlayer.class, TestUnoCard.class })
public class AllTests {

}
