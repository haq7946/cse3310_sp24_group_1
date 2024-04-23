package uta.cse3310;
import java.util.Random;

//THIS CLASS IS COMPLETE

public class Word 
{
    private String word;
    private int wordLength;
    private Orientation orient;
    private int xCoordinate;
    private int yCoordinate;
    private boolean availability;
    private boolean linked;
    public Word(String word) //Regular word constructor
    {
        Random random = new Random();
        //When word is created, input the word given into the word field, randomize the orientation
        //Make the availability true so it can be selected
        this.word = word;
        wordLength = word.length();
        xCoordinate = 0;
        yCoordinate = 0;
        availability = true;
        int value = random.nextInt(5);
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
        value = random.nextInt(5);
        if(value == 0)
        {
            linked = true;
        }
        else
        {
            linked = false;
        }
    }

    public Word(String word, int orientation, int link) //Using seed for orientation; word constructor for testing
    {
        this.word = word;
        wordLength = word.length();
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        availability = true;
        switch(orientation)
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
        if(link == 0)
        {
            linked = true;
        }
        else
        {
            linked = false;
        }
    }
    public String getWord()
    {
        return word;
    }

    public boolean getLinked()
    {
        return linked;
    }
    public void setLinked(boolean linkStatus)
    {
        linked = linkStatus;
    }
    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public void setXCoordinate(int x)
    {
        xCoordinate = x;
    }

    public void setYCoordinate(int y)
    {
        yCoordinate = y;
    }

    public void setWord(String s)
    {
        word = s;
    }

    public int getLength()
    {
        return wordLength;
    }

    public void setOrientation(Orientation ori)
    {
        orient = ori;
    }

    public Orientation getOrientation()
    {
        return orient;
    }

    public boolean getAvailability()
    {
        return availability;
    }

    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }
}
