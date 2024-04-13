package uta.cse3310;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.System;
//TODO:
//1. Limit word generation based off orientation limits
//2. 
//3.
//4. 
//5. 
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
    private int[] randomLetterFrequency;
    private double boardFormationTime;

    public Board()
    {
        density = 0.67;
        boardLength = 50;
        boardWidth = 50;
        boardArray = new char[boardLength][boardWidth];
        randomLetterFrequency = new int[26];
    }

    public static void updateBoardArray(char[][] arr)
    {

    }

    public int[] getRandomLetterFrequency()
    {
        return randomLetterFrequency;
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
        double startTimer = System.currentTimeMillis();
        double volume = boardLength * boardWidth;
        double mass = 0;
        double calculatedDensity = 0;
        //Intializing board to #'s to prepare for word input
        for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                boardArray[i][j] = '#';
            }
        }
        //Putting all words in the file in an arraylist for faster reading
        ArrayList<String> allWords = new ArrayList<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("resources/words.txt"));
            String wordFromFile = br.readLine();
            while(wordFromFile != null)
            {
                allWords.add(wordFromFile);
                wordFromFile = br.readLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("wadahek that wasn't a valid file!");
            e.printStackTrace();
        }
        //Placing words into the board
        while(calculatedDensity < density)
        {
            mass += placeWord(wordBank, allWords);
            calculatedDensity = mass/volume;
        }
        //If space is still equal to # after placing words, fill it in with a random letter, increment that letter frequency for board information
        for(int i = 0; i < boardLength; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                if(boardArray[i][j] == '#')
                {
                    char randomCharacter = (char)((Math.random() * 26) + 97);
                    boardArray[i][j] = randomCharacter;
                    randomLetterFrequency[randomCharacter - 97]++;
                }
            }
        }
        //Update orientation frequencies for board information
        setOrientationValues(wordBank);
        double endTimer = System.currentTimeMillis();
        boardFormationTime = endTimer - startTimer;
    }

    public int placeWord(WordBank wordBank, ArrayList<String> wordsFromFile)
    {

        //Selecting random line number
        double lineNumber = Math.floor((Math.random() * wordsFromFile.size()));
        //Selecting word from that line number that will be placed in the board
        String chosenWord = wordsFromFile.get((int)lineNumber);
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
        xCoordinate = (int)(Math.random() * boardLength);
        yCoordinate = (int)(Math.random() * boardWidth);
        Word placingWord = new Word(chosenWord, xCoordinate, yCoordinate);
        int wordLength = placingWord.getWord().length();
        //Based on the orientation, use the x/y coordinate and move from there to fill in the board
        if(placingWord.getOrientation().name().equals("HORIZONTAL"))
        {
            //Check to see how frequently this orientation has appeared so far within the wordbank
            //If orientation appears too frequently choose another word with a different orientation
            setOrientationValues(wordBank);
            if(getOrientationValues()[0] > 0.21)
            {
                return 0;
            }
            //Do for loop to see if word will fit in with given x/y coordinates
            //If doesn't fit, return 0 (word doesn't fit, chance that word will NEVER fit, so just choose another word)
            for(int i = 0; i < wordLength; i++)
            {
                if( ((xCoordinate + i) >= boardLength) || boardArray[yCoordinate][xCoordinate + i] != '#')
                {
                    return 0;   
                }
            }
            //Else, do another for loop to place the word in
            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate][xCoordinate + i] = placingWord.getWord().charAt(i);
            }
        }
        //Rinse and repeat for all other orientations
        else if(placingWord.getOrientation().name().equals("VERTICALUP"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[1] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < wordLength; i++)
            {
                if( ((yCoordinate + i) >= boardWidth) || boardArray[yCoordinate + i][xCoordinate] != '#')
                {
                    return 0;   
                }
            }
            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate + i][xCoordinate] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("VERTICALDOWN"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[2] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < wordLength; i++)
            {
                if( ((yCoordinate - i) < 0) || boardArray[yCoordinate - i][xCoordinate] != '#')
                {
                    return 0;   
                }
            }
            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate - i][xCoordinate] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("DIAGONALUP"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[3] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < wordLength; i++)
            {
                if( ((yCoordinate + i) >= boardWidth) || (xCoordinate + i ) >= boardLength || boardArray[yCoordinate + i][xCoordinate + i] != '#')
                {
                    return 0;   
                }            
            }
            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate + i][xCoordinate + i] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("DIAGONALDOWN"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[4] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < wordLength; i++)
            {
                if( ((yCoordinate - i) < 0) || (xCoordinate + i ) >= boardLength || boardArray[yCoordinate - i][xCoordinate + i] != '#')
                {
                    return 0;   
                }            
            }

            for(int i = 0; i < wordLength; i++)
            {
                boardArray[yCoordinate - i][xCoordinate + i] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("INVALID"))
        {
            System.out.println("NOT GAMER");
        }
        //Add the placed word to the wordbank
        wordBank.getWordBank().add(placingWord);
        //Return length to update calculatedDensity
        return chosenWord.length();
    }

    public void setOrientationValues(WordBank wordBank)
    {
        horizontalOrientation = 0;
        verticalUpOrientation = 0;
        verticalDownOrientation = 0;
        diagonalUpOrientation = 0;
        diagonalDownOrientation = 0;
        //All orientation values need a value of at least 0.15
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(wordBank.getWordBank().get(i).getOrientation().name().equals("HORIZONTAL"))
            {
                horizontalOrientation++;
            }
            else if(wordBank.getWordBank().get(i).getOrientation().name().equals("VERTICALUP"))
            {
                verticalUpOrientation++;
            }
            else if(wordBank.getWordBank().get(i).getOrientation().name().equals("VERTICALDOWN"))
            {
                verticalDownOrientation++;
            }
            else if(wordBank.getWordBank().get(i).getOrientation().name().equals("DIAGONALUP"))
            {
                diagonalUpOrientation++;
            }
            else if(wordBank.getWordBank().get(i).getOrientation().name().equals("DIAGONALDOWN"))
            {
                diagonalDownOrientation++;
            }
        }
        //Normalize those values to add up to 1
        horizontalOrientation /= wordBank.getWordBank().size();
        verticalUpOrientation /= wordBank.getWordBank().size();
        verticalDownOrientation /= wordBank.getWordBank().size();
        diagonalUpOrientation /= wordBank.getWordBank().size();
        diagonalDownOrientation /= wordBank.getWordBank().size();

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

    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("Random letter frequency: {");
        //Need to format the string like this sort there's not a comma at the end
        str.append((char)(97) + ": " + randomLetterFrequency[0]);
        for(int i = 1; i < 26; i++)
        {
            str.append(", " + (char)(i + 97) + ": " + randomLetterFrequency[i]);
        }
        str.append("}\n\n");
        str.append("Time to generate board: " + boardFormationTime + " milliseconds\n");
        str.append("Orientation %'s: \nHorizontal: " + getOrientationValues()[0] + "\nVerticalUp: " + getOrientationValues()[1]);
        str.append("\nVerticalDown: " + getOrientationValues()[2] + "\nDiagonalUp: " + getOrientationValues()[3] + "\nDiagonalDown: " + getOrientationValues()[4] + "\n");
        return str.toString();
    }
}
