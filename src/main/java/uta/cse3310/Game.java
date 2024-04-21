package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.lang.Math;
public class Game 
{
    public String gameID;
    public ArrayList<Player> playerList;
    public ArrayList<String> playerChat;
    public int numberOfPlayers;
    public Board board;
    public WordBank bank;
    public boolean isAvailableToJoin;
    public int gameStatus; //wtf does this do
    public boolean gameHasStarted;
    public boolean inDeathmatch;
    public String gameResponse;  //This tells us when the game is started


    public String[] wordList;
    public String[] completedWordList;

    public Game()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        gameID = dtf.format(now); //GameID uses format yyyy/MM/dd HH:mm:ss
        playerList = new ArrayList<Player>();
        playerChat = new ArrayList<String>();
        numberOfPlayers = 0;
        board = null;
        bank = null;
        isAvailableToJoin = true;
        //startGame();

        //I moved the board initialization stuff here to startGame() - AE 11:30 04/13
    }

    public String getGameID()
    {
        return gameID;
    }

    public void startGame() //starts the game and makes the game unavailable to join
    {
        isAvailableToJoin = false;
        bank = new WordBank();
        board = new Board();
        board.initializeBoard(bank);
        //board.printBoardArray();
        //System.out.println(bank);
        //System.out.println(board);
        gameHasStarted = true;
        gameResponse = "start";
    }

    public void setGameID(int gam) //we are never going to use this method
    {

    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int num) //we are never going to use this method
    {
        numberOfPlayers = num;
    }

    public void updateState(ServerEvent U)
    {

    }

    public void exitGame() //what does this method do
    {

    }

    public void initializeWordList()
    {

    }

    public void crossOutWord(String word, WordBank wordBank)
    {
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(word.equals(wordBank.getWordBank().get(i).getWord()))
            {
                wordBank.getWordBank().get(i).setAvailability(false);
            }
        }      
    }

    public String selectWord(int boardX_1, int boardY_1, int boardX_2, int boardY_2)// (X1, Y1) is first click, (X2, Y2) is second click
    {
        if (boardX_1 == boardX_2) //word is vertical
        {
            if (boardY_1 > boardY_2) //word is vertical up
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardY_1 - boardY_2); i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardX_1][boardY_1 - i];
                }
                return wordToBeReturned;
            }
            else if (boardY_1 < boardY_2) //word is vertical down
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardY_2 - boardY_1); i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardX_1][boardY_1 + i];
                }
                return wordToBeReturned;
            }
        }
        else if (boardY_1 == boardY_2) //word is horizontal; words can only be horizontal forwards, so X2 > X1 always
        {
            String wordToBeReturned = ""; 
            for (int i = 0; i < (boardX_2 - boardX_1); i++)
            {
                wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardX_1 + i][boardY_1];
            }
            return wordToBeReturned;
        }
        else if ((boardX_1 - boardX_2) == (boardY_1 - boardY_2)) //word is diagonal
        {
            if (boardY_1 > boardY_2) //word is diagonal up
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardX_2 - boardX_1); i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardX_1 + i][boardY_1 - i];
                }
                return wordToBeReturned;
            }
            else if (boardY_1 < boardY_2) //word is diagonal down
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardX_2 - boardX_1); i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardX_1 + i][boardY_1 + i];
                }
                return wordToBeReturned;
            }
        }
        else //the two clicks do not form a valid word
        {
            return "";
        }
        return ""; //this return statement exists because "tHiS mEtHoD mUsT rEtUrN a ReSuLt Of TyPe StRiNg"
    }

    public void gameChat(String message)
    {
        playerChat.add(message);
    }

    public boolean checkValidWord(String word, WordBank wordBank)
    {
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(word.equals(wordBank.getWordBank().get(i).getWord()) && wordBank.getWordBank().get(i).getAvailability() == true)
            {
                return true;
            }
        }
        return false;
    }

    public Player checkWinner()
    {
        Player playerMax = playerList.get(0);
        for(int i = 1; i < playerList.size(); i++)
        {
            if(playerList.get(i).getScore() > playerMax.getScore())
            {
                playerMax = playerList.get(i);
            }
        }
        int vic = playerMax.getVictories();
        playerMax.setVictories(vic++);
        return playerMax;
    }

    public void roomChat(String message, String gameID)
    {

    }

    public String[] chooseWords(File file)
    {
        return null;
    }

    public void addPlayer(Player newPlayer)
    {
        if (numberOfPlayers < 4) //check that game has less than 4 players
        {
            playerList.add(newPlayer);
            numberOfPlayers++;
        }
        else
        {
            System.out.println("Error: too many players in that game");
        }
    }

    public void removePlayer(Player p)
    {
        playerList.remove(p);
        numberOfPlayers--;
    }

    public void displayInfo()
    {
        System.out.println("gameID = " + gameID);
        System.out.println("(game)playerList = " + playerList);
        System.out.println("numberOfPlayers = " + numberOfPlayers);
        System.out.println("isAvailableToJoin = " + isAvailableToJoin);
    }

    public boolean isAvailableToJoin() 
    {
        return isAvailableToJoin;
    }

    public ArrayList<Player> getPlayerList()
    {
        return playerList;
    }

    public void updateGame(GameEvent g)
    {
        
    }


}