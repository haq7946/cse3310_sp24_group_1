package uta.cse3310;
import java.util.ArrayList;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
public class WordBankTest extends TestCase
{
    public WordBankTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(WordBankTest.class);
    }
    //Just testing if sorting word bank works correctly
    public void testSort()
    {
        WordBank wordBank = new WordBank();
        wordBank.getWordBank().add(new Word("pen"));
        wordBank.getWordBank().add(new Word("is"));
        wordBank.getWordBank().add(new Word("land"));
        assertEquals("is", wordBank.getWordBank().get(1).getWord());
        assertEquals("pen", wordBank.getWordBank().get(0).getWord());
        wordBank.initializeWordBank();
        assertEquals("is", wordBank.getWordBank().get(0).getWord());
        assertEquals("pen", wordBank.getWordBank().get(2).getWord());
    }
}