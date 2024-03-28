package uta.cse3310;
import java.io.File;
import java.lang.Math;
public class Board 
{
    private char[][] boardArray;
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
