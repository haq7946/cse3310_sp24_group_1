package uta.cse3310;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Board 
{
    private char[][] boardArray;
    private int boardLength;
    private int boardWidth;
    private int xCoordinate;
    private int yCoordinate;
    private Word[] wordList;
    private double density;
    private double horizontalOrientation;
    private double verticalUpOrientation;
    private double verticalDownOrientation;
    private double diagonalUpOrientation;
    private double diagonalDownOrientation;
    private double boardFormationTime;

    public Board()
    {
        density = 0.67;
        boardLength = 20;
        boardWidth = 20;
        boardArray = new char[boardLength][boardWidth];
    }

    public static void updateBoardArray(char[][] arr)
    {

    }

    public char[][] getBoardArray()
    {
        return boardArray;
    }

    public void printBoardArray()
    {
       for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardLength; j++)
            {
                System.out.print(boardArray[i][j] + "  ");
            }
            System.out.println("\n");
        }
    }

    public void setDensity(double dens)
    {
        density = dens;
    }

    public double getDensity()
    {
        return density;
    }

    public static ArrayList<Word> addWordBank(Word word)
    {
        return null;
    }

    public void initializeBoard(WordBank wordBank)
    {  
        double volume = boardLength * boardWidth;
        double mass = 0;
        double calculatedDensity = 0;
        for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                boardArray[i][j] = '#';
            }
        }
        while(calculatedDensity < density)
        {
            System.out.println("placing word in...");
            mass += placeWord(wordBank);
            calculatedDensity = mass/volume;
        }
        System.out.println("Total word length is " + mass);
    }

    public int placeWord(WordBank wordBank)
    {
        //Reading number of lines in text file
        int numberOfLines = 0;
        try
        {
        BufferedReader br = new BufferedReader(new FileReader("words.txt"));
        while(br.readLine() != null)
        {
            numberOfLines++;
        }
        //Selecting random line number
        double lineNumber = Math.floor((Math.random() * numberOfLines));
        //Selecting word from that line number, resetting br to beginning
        br = new BufferedReader(new FileReader("words.txt"));
        for(int i = 1; i < lineNumber; i++){
            br.readLine();
        }
        //Word that will be placed in the board
        String chosenWord = br.readLine();
        //Check to see if word is not in the word bank, if it is then return 0 (word not able to be picked)
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(chosenWord.equals(wordBank.getWordBank().get(i)))
            {
                return 0;
            }
        }
        //Check to see if word is of length 4 or greater, if not then return 0 (word too short)
        if(chosenWord.length() < 4)
        {
            return 0;
        }
        //Choosing random coordinates for chosen word, make instance of a Word
        Word placingWord = new Word(chosenWord);
        xCoordinate = (int)(Math.random() * boardLength);
        yCoordinate = (int)(Math.random() * boardWidth);
        int wordLength = placingWord.getWord().length();
        //System.out.println(placingWord.getOrientation());
        //Based on the orientation, use the x/y coordinate and move from there to fill in the board
        //Do for loop to see if word will fit in with given x/y coordinates
        //If doesn't fit, return 0 (word doesn't fit, chance that word will NEVER fit, so just choose another word)
        //Else, do another for loop to place the word in
        if(placingWord.getOrientation().name() == "HORIZONTAL")
        {
            //Checking for loop
            for(int i = 0; i < wordLength; i++)
            {
                if( ((xCoordinate + i) >= boardLength) || boardArray[yCoordinate][xCoordinate + i] != '#')
                {
                    return 0;   
                }
            }
            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate][xCoordinate + i] = placingWord.getWord().charAt(i);
            }
        }
        /*
        else if(placingWord.getOrientation().name() == "VERTICALUP")
        {
            System.out.println("Gamerr");
        }
        else if(placingWord.getOrientation().name() == "VERTICALDOWN")
        {
            System.out.println("Gamerrr");
        }
        else if(placingWord.getOrientation().name() == "DIAGONALUP")
        {
            System.out.println("Gamerrrr");
        }
        else if(placingWord.getOrientation().name() == "DIAGONALDOWN")
        {
            System.out.println("Gamerrrrr");
        }
        else if(placingWord.getOrientation().name() == "INVALID")
        {
            System.out.println("NOT GAMER");
        }
        */
        //Return length to update calculatedDensity
        return chosenWord.length();
        }
        catch(IOException e)
        {
            System.out.println("wadahek that wasn't a valid file!");
        }
        return -1;
    }

    public void setOrientationValues()
    {
        double orientationValue = Math.random()/20 + 0.15;
        horizontalOrientation = orientationValue;
        verticalUpOrientation = orientationValue;
        verticalDownOrientation = orientationValue;
        diagonalUpOrientation = orientationValue;
        diagonalDownOrientation = orientationValue;
    }

    public double[] getOrientationValues()
    {
        double[] values = new double[5];
        values[0] = horizontalOrientation;
        values[1] = verticalUpOrientation;
        values[2] = verticalDownOrientation;
        values[3] = diagonalUpOrientation;
        values[4] = diagonalDownOrientation;
        return values;
    }

    public double getBoardFormationTime()
    {
        return boardFormationTime;
    }
}
