package uta.cse3310;
import java.io.File;

public class Board 
{
    private char[][] boardArray;
    private Word[] wordList;
    private double density;
    private double horizontalOrientation;
    private double verticalOrientation;
    private double diagonalUpOrientation;
    private double diagonalDownOrientation;
    private double boardFormationTime;

    public Board()
    {
        boardArray = new char[50][50];
    }

    public static void updateBoardArray(char[][] arr)
    {

    }

    public static char[][] getBoardArray()
    {
        return null;
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
        boardArray = new char[50][50];
    }

    public static void setOrientationValues()
    {

    }

    public static double[] getOrientationValues()
    {
        return null;
    }

    public static double getBoardFormationTime()
    {
        return 0.0;
    }
}
