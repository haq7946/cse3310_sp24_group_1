package uta.cse3310;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Example
{
    public static void main(String[] args)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("penis.txt"));
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }   
}
