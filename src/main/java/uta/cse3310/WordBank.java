package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
public class WordBank 
{
    private Word[] wordList;

    public WordBank()
    {
        
    }

    public Word[] initializeWordBank() //Put file parameter back in
    {
        try
        {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\brybo\\cse3310_sp24_group_1\\words.txt"));
        for(int i = 0; i < 5; i++)
        {
            System.out.println(br.readLine()); 
        }
        }
        catch(IOException e)
        {
            System.out.println("wadahek that wasn't a valid file!");
        }
        return wordList;
    }

    public static void updateWordBank(Word[] Word)
    {

    }
}
