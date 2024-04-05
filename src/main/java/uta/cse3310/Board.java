package uta.cse3310;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
public class Board 
{
    private char[][] boardArray;
    private int boardLength;
    private int boardWidth;
    private double xCoordinate;
    private double yCoordinate;
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
        int volume = boardLength * boardWidth;
        int mass = 0;
        int calculatedDensity = 0;

        for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                boardArray[i][j] = '#';
            }
        }
        while(calculatedDensity < density)
        {
            mass += placeWord(wordBank);
            calculatedDensity = mass/volume;
        }
    }

    public int placeWord(WordBank wordBank)
    {
        //Reading number of lines in text file
        int numberOfLines = 0;
        try
        {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\brybo\\cse3310_sp24_group_1\\words.txt"));
        while(br.readLine() != null)
        {
            numberOfLines++;
        }

        //Selecting random line number
        double lineNumber = Math.floor((Math.random() * numberOfLines));
        //Selecting word from that line number, resetting br to beginning
        br = new BufferedReader(new FileReader("C:\\Users\\brybo\\cse3310_sp24_group_1\\words.txt"));
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
        xCoordinate = Math.floor(Math.random() * boardLength);
        yCoordinate = Math.floor(Math.random() * boardWidth);
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
