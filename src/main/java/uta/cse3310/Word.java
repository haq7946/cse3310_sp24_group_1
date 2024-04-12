package uta.cse3310;
import java.lang.Math;

public class Word 
{
    private String word;
    private int wordLength;
    private Orientation orient;
    private int xCoordinate;
    private int yCoordinate;

    public Word(String word, int xCoordinate, int yCoordinate)
    {
        //When word is created, input the word given into the word field, randomize the orientation
        this.word = word;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        int value = (int)(Math.random() * 5);
        switch(value)
        {
            case 0:
                orient = Orientation.HORIZONTAL;
                break;
            case 1:
                orient = Orientation.VERTICALUP;
                break;
            case 2:
                orient = Orientation.VERTICALDOWN;
                break;
            case 3:
                orient = Orientation.DIAGONALUP;
                break;
            case 4:
                orient = Orientation.DIAGONALDOWN;
                break;
            default:
                orient = Orientation.INVALID;
        }
    }

    public String getWord()
    {
        return word;
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public static void setWord(String s)
    {

    }

    public static int getLength()
    {
        return 0;
    }

    public static void setOrientation(int ori)
    {

    }

    public Orientation getOrientation()
    {
        return orient;
    }


}
