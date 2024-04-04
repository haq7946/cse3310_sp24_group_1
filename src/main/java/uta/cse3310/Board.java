package uta.cse3310;
import java.io.File;
import java.lang.Math;
public class Board 
{
    private char[][] boardArray;
    private int boardLength;
    private int boardWidth;
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
        boardLength = 50;
        boardWidth = 50;
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

    public static Word[] initializeWordBank(File file)
    {
        return null;
    }

    public void initializeBoard()
    {  
        boardLength = 10;
        boardWidth = 10;
        for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardLength; j++)
            {
                boardArray[i][j] = '#';
            }
        }
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
