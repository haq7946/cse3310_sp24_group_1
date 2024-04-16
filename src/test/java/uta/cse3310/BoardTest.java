package uta.cse3310;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BoardTest extends TestCase
{
    public BoardTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(BoardTest.class);
    }

    public void testTooLong()
    {
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        WordBank wordBank = new WordBank();
        //Word should not fit in the grid!
        wordsFromFile.add("penispenispenispenispenispenispenispenispenispeniss");
        assertEquals(0, b.placeWord(wordBank, wordsFromFile));

    }

    public void testTooShort()
    {
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        WordBank wordBank = new WordBank();
        //Word is too short!
        wordsFromFile.add("pen");
        assertEquals(0, b.placeWord(wordBank, wordsFromFile));
    }

    public void testOrientationValues()
    {
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        //Initalize board and wordbank 50 times and see if orientation values are appropriate each time
        for(int e = 0; e < 50; e++)
        {
            WordBank wordBank = new WordBank();
            b.initializeBoard(wordBank);
            double orientations[] = b.getOrientationValues();
            for(int i = 0; i < orientations.length; i++)
            {
                assertTrue(orientations[i] > 0.15);
            }
        }
    }

    public void testBoardFormationTime()
    {
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        //Initalize board and wordbank 50 times and see if timer values are appropriate each time
        for(int e = 0; e < 50; e++)
        {
            WordBank wordBank = new WordBank();
            b.initializeBoard(wordBank);
            double time = b.getBoardFormationTime();
            assertTrue(time < 1000);
        }
    }
    public void testDensity()
    {
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        //Initalize board and wordbank 5 times and see if density value is appropriate each time
        for(int e = 0; e < 5; e++)
        {
            double density = 0;
            WordBank wordBank = new WordBank();
            b.initializeBoard(wordBank);
            //Total of all random characters placed
            for(int i = 0; i < b.getRandomLetterFrequency().length; i++)
            {
                density += b.getRandomLetterFrequency()[i];
            }
            //Density of random characters
            density /= (b.getBoardLength() * b.getBoardWidth());
            //Density of placed characters = 1 - random characters
            density = 1 - density;
            assertTrue(density > b.getDensity());
        }
    }

}