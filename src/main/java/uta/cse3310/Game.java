package uta.cse3310;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    public String[] wordList;
    public String[] completedWordList;

    public Game()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        gameID = dtf.format(now); //GameID uses format yyyy/MM/dd HH:mm:ss
        playerList = new ArrayList<Player>();
        numberOfPlayers = 0;
        board = null;
        bank = null;
        isAvailableToJoin = true;
        startGame();

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
        bank.initializeWordBank();
        board = new Board();
        board.initializeBoard(bank);
        board.printBoardArray();
        System.out.println(bank);
        System.out.println(board);
    }

    public void setGameID(int gam) //we are never going to use this method
    {

    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int num)
    {
        numberOfPlayers = num;
    }

    public void updateState(UserEvent U)
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

    public String selectWord()
    {
        return "";
    }

    public void gameChat(String message)
    {
        playerChat.add(message);
    }

    public boolean checkValidWord(String word, WordBank wordBank)
    {
        for(int i = 0; i < wordBank.getWordBank().size(); i++)
        {
            if(word.equals(wordBank.getWordBank().get(i).getWord()))
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
        if (numberOfPlayers < 4)
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

    public boolean isAvailableToJoin() {
        return isAvailableToJoin;
    }
}