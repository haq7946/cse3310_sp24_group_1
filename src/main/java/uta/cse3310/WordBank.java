package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
public class WordBank 
{
    private ArrayList<Word> wordBank;

    public WordBank()
    {
         wordBank = new ArrayList<Word>();
         Word word = new Word("Burger");
         wordBank.add(word);
    }

    public ArrayList<Word> getWordBank()
    {
        return wordBank;
    }

    public ArrayList<Word> initializeWordBank() //Put file parameter back in
    {
        try
        {
        BufferedReader br = new BufferedReader(new FileReader("resources\\words.txt"));
        for(int i = 0; i < 5; i++)
        {
            System.out.println(br.readLine()); 
        }
        }
        catch(IOException e)
        {
            System.out.println("wadahek that wasn't a valid file!");
        }
        return wordBank;
    }

    public static void updateWordBank(Word[] Word)
    {

    }
}
