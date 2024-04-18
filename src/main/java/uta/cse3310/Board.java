package uta.cse3310;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.System;
import java.util.Arrays;
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
    private int linkedWordFrequency;
    private ArrayList<Word> linkedWords;
    private double boardFormationTime;
    private Random random;
    private int seed;
    public Board() //Regular Board
    {
        density = 0.67;
        boardLength = 50;
        boardWidth = 50;
        boardArray = new char[boardLength][boardWidth];
        randomLetterFrequency = new int[26];
        linkedWords = new ArrayList<Word>();
        seed = -1;
        random = new Random();
    }
    public Board(int seed) //Overloaded board for seed testing
    {
        density = 0.67;
        boardLength = 50;
        boardWidth = 50;
        boardArray = new char[boardLength][boardWidth];
        randomLetterFrequency = new int[26];
        linkedWords = new ArrayList<Word>();
        this.seed = seed;
        random = new Random(seed);
    }
    public double getBoardLength()
    {
        return boardLength;
    }
    public double getBoardWidth()
    {
        return boardWidth;
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
        Arrays.fill(randomLetterFrequency, 0);
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
                    char randomCharacter = (char)((random.nextInt(26)) + 97);
                    boardArray[i][j] = randomCharacter;
                    randomLetterFrequency[randomCharacter - 97]++;
                }
            }
        }
        //Update orientation frequencies for board information
        setOrientationValues(wordBank);
        //Update linked word frequencies for board information
        setLinkedValues(wordBank);
        double endTimer = System.currentTimeMillis();
        boardFormationTime = endTimer - startTimer;
    }

    public int placeWord(WordBank wordBank, ArrayList<String> wordsFromFile)
    {
        //Selecting random line number
        double lineNumber = random.nextInt(wordsFromFile.size());
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
        xCoordinate = random.nextInt(boardLength);
        yCoordinate = random.nextInt(boardWidth);
        Word placingWord;
        int indexOfLinkedWord = -1;
        if(seed == -1) //Normal Word
        {
            placingWord = new Word(chosenWord);
            //If the word is a linked word and the wordbank isn't empty, look for a random word in the wordbank and trail down its letters
            //If the letter matches the first letter of the linked word, use those coordinates of that letter, break out of the loop, then proceed to the next instructions
            //Else return 0 (could not link word)
            if(placingWord.getLinked() == true && wordBank.getWordBank().size() != 0)
            {
                int index = random.nextInt(wordBank.getWordBank().size());
                Word tempWord = wordBank.getWordBank().get(index);
                //Save index of tempWord to change link status to true if word is accepted
                indexOfLinkedWord = index;
                boolean tempCheck = false;
                int offset = 0;
                for(int i = 0; i < tempWord.getLength(); i++)
                {
                    if(tempWord.getWord().substring(i,i+1).equals(placingWord.getWord().substring(0,1)))
                    {
                        tempCheck = true;
                        offset = i;
                        break;
                    }
                }
                if(tempCheck == false)
                {
                    return 0;
                }
                else
                {
                    //Getting coordinates of linked letter
                    switch(tempWord.getOrientation())
                    {
                        case HORIZONTAL:
                            placingWord.setXCoordinate(tempWord.getXCoordinate() + offset);
                            placingWord.setYCoordinate(tempWord.getYCoordinate());
                            break;
                        case VERTICALUP:
                            placingWord.setXCoordinate(tempWord.getXCoordinate());
                            placingWord.setYCoordinate(tempWord.getYCoordinate() + offset);
                            break;
                        case VERTICALDOWN:
                            placingWord.setXCoordinate(tempWord.getXCoordinate());
                            placingWord.setYCoordinate(tempWord.getYCoordinate() - offset);
                            break;
                        case DIAGONALUP:
                            placingWord.setXCoordinate(tempWord.getXCoordinate() + offset);
                            placingWord.setYCoordinate(tempWord.getYCoordinate() + offset);
                            break;
                        case DIAGONALDOWN:
                            placingWord.setXCoordinate(tempWord.getXCoordinate() + offset);
                            placingWord.setYCoordinate(tempWord.getYCoordinate() - offset);
                            break;
                        default:
                            System.out.println("Something went wrong");
                    }                   
                }
            }
            //Else if not a linked word or wordbank is empty, just use normal coordinates
            else
            {
            placingWord.setXCoordinate(xCoordinate);
            placingWord.setYCoordinate(yCoordinate);
            }
        }
        else //Seeded word chosen for testing
        {
            placingWord = new Word(chosenWord, random.nextInt(5), random.nextInt(10));
            placingWord.setXCoordinate(xCoordinate);
            placingWord.setYCoordinate(yCoordinate);
        }
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
            //If word is linked, then check everything but the first letter
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                if(placingWord.getLinked() == true && i == 0)
                {
                    i = 1;
                }
                if( ((placingWord.getXCoordinate() + i) >= boardLength) || boardArray[placingWord.getYCoordinate()][placingWord.getXCoordinate() + i] != '#')
                {
                    return 0;   
                }
            }
            //Else, do another for loop to place the word in
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                boardArray[placingWord.getYCoordinate()][placingWord.getXCoordinate() + i] = placingWord.getWord().charAt(i);
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
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                if(placingWord.getLinked() == true && i == 0)
                {
                    i = 1;
                }
                if( ((placingWord.getYCoordinate() + i) >= boardWidth) || boardArray[placingWord.getYCoordinate() + i][placingWord.getXCoordinate()] != '#')
                {
                    return 0;   
                }
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                boardArray[placingWord.getYCoordinate() + i][placingWord.getXCoordinate()] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("VERTICALDOWN"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[2] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                if(placingWord.getLinked() == true && i == 0)
                {
                    i = 1;
                }
                if(((placingWord.getYCoordinate() - i) < 0) || boardArray[placingWord.getYCoordinate() - i][placingWord.getXCoordinate()] != '#')
                {
                    return 0;   
                }
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                boardArray[placingWord.getYCoordinate() - i][placingWord.getXCoordinate()] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("DIAGONALUP"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[3] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                if(placingWord.getLinked() == true && i == 0)
                {
                    i = 1;
                }
                if( ((placingWord.getYCoordinate() + i) >= boardWidth) || (placingWord.getXCoordinate() + i ) >= boardLength || boardArray[placingWord.getYCoordinate() + i][placingWord.getXCoordinate() + i] != '#')
                {
                    return 0;   
                }            
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                boardArray[placingWord.getYCoordinate() + i][placingWord.getXCoordinate() + i] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("DIAGONALDOWN"))
        {
            setOrientationValues(wordBank);
            if(getOrientationValues()[4] > 0.21)
            {
                return 0;
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                if(placingWord.getLinked() == true && i == 0)
                {
                    i = 1;
                }
                if( ((placingWord.getYCoordinate() - i) < 0) || (placingWord.getXCoordinate() + i ) >= boardLength || boardArray[placingWord.getYCoordinate() - i][placingWord.getXCoordinate() + i] != '#')
                {
                    return 0;   
                }            
            }
            for(int i = 0; i < placingWord.getLength(); i++)
            {
                boardArray[placingWord.getYCoordinate() - i][placingWord.getXCoordinate() + i] = placingWord.getWord().charAt(i);
            }
        }
        else if(placingWord.getOrientation().name().equals("INVALID"))
        {
            System.out.println("NOT GAMER");
        }
        //Add the placed word to the wordbank
        wordBank.getWordBank().add(placingWord);
        //If linked status of the word was true, since the word managed to get placed
        //(Index of linked word shouldn't be -1 if this happens)
        if(placingWord.getLinked() == true && indexOfLinkedWord != -1)
        {
            //Make sure the other word linked to it is also link status true
            wordBank.getWordBank().get(indexOfLinkedWord).setLinked(true);
        }
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

    public int setLinkedValues(WordBank wordBank)
    {
        linkedWordFrequency = 0;
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(wordBank.getWordBank().get(i).getLinked() == true)
            {
                linkedWordFrequency++;
                linkedWords.add(wordBank.getWordBank().get(i));
            }
        }
        return linkedWordFrequency;
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
        str.append("\nLinked Word Frequency: " + linkedWordFrequency);
        /* This is for debugging
        str.append("\n All linked words: \n");
        for(int i = 0; i < linkedWords.size(); i++)
        {
            str.append("\n | " + linkedWords.get(i).getWord() + " |" + linkedWords.get(i).getXCoordinate() + " " + linkedWords.get(i).getYCoordinate());
        }
        */
        return str.toString();
    }
}
