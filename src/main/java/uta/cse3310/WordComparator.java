package uta.cse3310;
import java.util.Comparator;
import java.lang.Comparable;
public class WordComparator implements Comparator<Word>
{
    @Override
    public int compare(Word word1, Word word2)
    {
        return word1.getWord().compareTo(word2.getWord());
    }
}