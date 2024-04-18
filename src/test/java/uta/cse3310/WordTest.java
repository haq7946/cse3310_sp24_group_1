package uta.cse3310;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Random;
public class WordTest extends TestCase
{
    public WordTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(WordTest.class);
    }
    //Just testing if two random words with same seed generate same values
    public void testRandom()
    {
        Random random = new Random(5);
        Random random2 = new Random(5);
        Word word = new Word("pen", random.nextInt(5), random.nextInt(10));
        Word word2 = new Word("is", random2.nextInt(5), random2.nextInt(10));
        assertEquals(word.getXCoordinate(), word2.getXCoordinate());
        assertEquals(word.getYCoordinate(), word2.getYCoordinate());
        assertEquals(word.getAvailability(), word2.getAvailability());
        assertEquals(word.getOrientation(), word2.getOrientation());
        assertFalse(word.getWord().equals(word2.getWord()));
    }
}