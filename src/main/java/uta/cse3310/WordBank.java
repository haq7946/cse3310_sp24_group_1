package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Comparable;
import java.util.Collections;
public class WordBank 
{
    public ArrayList<Word> wordBank;

    public WordBank()
    {
         wordBank = new ArrayList<Word>();
    }

    public ArrayList<Word> getWordBank()
    {
        return wordBank;
    }

    public ArrayList<Word> initializeWordBank()
    {
        Collections.sort(wordBank, new WordComparator());
        return wordBank;
    }
    public static void updateWordBank(Word[] Word)
    {

    }

    public String toString()
    {
        Collections.sort(wordBank, new WordComparator());
        StringBuilder str = new StringBuilder();
        str.append("Word Bank: {\n");
        for(int i = 0; i < wordBank.size(); i++)
        {
            if(i % 20 == 0)
            {
                str.append("\n");
            }
            str.append(wordBank.get(i).getWord() + " ");
        }
        str.append("\n\n}");
        return str.toString();
    }
}
