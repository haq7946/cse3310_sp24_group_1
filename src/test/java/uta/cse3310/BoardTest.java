package uta.cse3310;
import java.util.ArrayList;
import java.util.Random;
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
        Random random = new Random();
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        WordBank wordBank = new WordBank();
        //Word should not fit in the grid!
        wordsFromFile.add("penispenispenispenispenispenispenispenispenispeniss");
        assertEquals(0, b.placeWord(wordBank, wordsFromFile, random));

    }

    public void testTooShort()
    {
        Random random = new Random();
        Board b = new Board();
        ArrayList<String> wordsFromFile = new ArrayList<String>();
        WordBank wordBank = new WordBank();
        //Word is too short!
        wordsFromFile.add("pen");
        assertEquals(0, b.placeWord(wordBank, wordsFromFile, random));
    }

    public void testSeed()
    {
        Board b = new Board(10);
        Board b2 = new Board(10);
        assertEquals(b.getLinkedWordFrequency(), b2.getLinkedWordFrequency());
        assertEquals(b.density, b2.density);
        assertEquals(b.horizontalOrientation, b2.horizontalOrientation);
    }

    public void testOrientationValues()
    {
        Board b = new Board();
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
        //Initalize board and wordbank 3 times and see if density value is appropriate each time
        double density;
        WordBank wordBank;
        for(int e = 0; e < 5; e++)
        {
            density = 0;
            wordBank = new WordBank();
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
            //0.1% leeway due to weird double activity
            System.out.println(density);
            assertTrue(density + 0.001 > b.getDensity());
        }
    }
    public void testLinked()
    {
        Board b = new Board();
        WordBank wordBank;
        //Initalize board and wordbank 3 times and see if linked value is appropriate each time
        double linked;
        for(int e = 0; e < 3; e++)
        {
            linked = 0;
            wordBank = new WordBank();
            b.initializeBoard(wordBank);
            //I should be counting the number of linked words correctly
            assertEquals(b.getLinkedWordFrequency(), b.getLinkedWords().size());
            //Linked words should be > 10
            assertTrue(b.getLinkedWordFrequency() > 10);
        }
    }
}