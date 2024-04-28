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
import java.lang.Thread;

public class Game 
{
    public String gameID;
    public ArrayList<Player> playerList;
    public ArrayList<String> playerChat;
    public ArrayList<Player> winners;
    public int numberOfPlayers;
    public Board board;
    public WordBank bank;
    public boolean isAvailableToJoin;
    public int gameStatus; //wtf does this do
    public boolean gameHasStarted;
    public boolean inDeathmatch;
    public String gameResponse;  //This tells us when the game is started
    public String boardButtonMessage; //This will tell the board to update the button with that players color //Message will say "updateBoard" or "resetBoard"
    public String colorOrientation;  //The orientation of the color(horizontal, vertical, diagonal)
    public int colorToShow;
    public Clock clock;
    int x1; int y1;
    int x2; int y2;

    public String[] wordList;
    public String[] completedWordList;

    public Game()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        gameID = dtf.format(now); //GameID uses format yyyy/MM/dd HH:mm:ss
        playerList = new ArrayList<Player>();
        playerChat = new ArrayList<String>();
        winners = new ArrayList<Player>();
        numberOfPlayers = 0;
        board = null;
        bank = null;
        clock = null;
        isAvailableToJoin = true;
        //startGame();

        //I moved the board initialization stuff here to startGame() - AE 11:30 04/13
    }

    public String getGameID()
    {
        return gameID;
    }

    public void startGame() //starts the game and makes the game unavailable to join
    //Additionally, starts the timer
    {
        isAvailableToJoin = false;
        bank = new WordBank();
        board = new Board();
        board.initializeBoard(bank);
        clock = new Clock(300);
        //board.printBoardArray();
        //System.out.println(bank);
        //System.out.println(board);
        for(int i = 0; i < playerList.size(); i++) //Set everyone's score to 0
        {
            playerList.get(i).score = 0;
        }
        gameHasStarted = true;
        //gameResponse = "start"; //Already does that in lobby.java but keeping it just in case -Bryan
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
        //Since the board Array in java is flipped
        //Board array x[y,y,y,y]
        //Website x,y
        
        if (boardX_1 == boardX_2) //word is vertical
        {
            if (boardY_1 > boardY_2) //word is vertical up
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardY_1 - boardY_2) + 1; i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardY_1 - i][boardX_1];
                }
                return wordToBeReturned;
            }
            else if (boardY_1 < boardY_2) //word is vertical down
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardY_2 - boardY_1) + 1; i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardY_1 + i][boardX_1];
                }
                return wordToBeReturned;
            }
        }
        else if (boardY_1 == boardY_2) //word is horizontal; words can only be horizontal forwards, so X2 > X1 always
        {
            String wordToBeReturned = ""; 
            for (int i = 0; i < (boardX_2 - boardX_1) + 1; i++)
            {
                wordToBeReturned = wordToBeReturned + board.boardArray[boardY_1][boardX_1 + i];
            }
            return wordToBeReturned;
        }
        else if ((boardX_1 - boardX_2) == (boardY_1 - boardY_2)) //word is diagonal
        {
            if (boardY_1 > boardY_2) //word is diagonal up
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardX_2 - boardX_1) + 1; i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardY_1 - i][boardX_1 + i];
                }
                return wordToBeReturned;
            }
            else if (boardY_1 < boardY_2) //word is diagonal down
            {
                String wordToBeReturned = "";
                for (int i = 0; i < (boardX_2 - boardX_1) + 1; i++)
                {
                    wordToBeReturned = wordToBeReturned + board.getBoardArray()[boardY_1 + i][boardX_1 + i];
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

    public String checkOrientation(int boardX_1, int boardY_1, int boardX_2, int boardY_2)
    {
        if (boardX_1 == boardX_2) //word is vertical
        {
            return "vertical";
        }
        else if (boardY_1 == boardY_2) //word is horizontal; words can only be horizontal forwards, so X2 > X1 always
        {
            return "horizontal";
        }
        else if ((boardX_1 - boardX_2) == (boardY_1 - boardY_2)) //word is diagonal
        {
            
           return "diagonal";
        }
        else //the two clicks do not form a valid word
        {
            return "";
        }

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
/*
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
    */

    public void addPlayer(String name, String victories) // add a new player to the lobby
    {
        for (int i = 0; i < playerList.size(); i++) //check players in lobby to see if they already have the name
        {
            if(name.equals(playerList.get(i).getUsername()))
            {
                System.out.println("Error: username already taken (lobby)");
                return;
            }
        }
            for (int j = 0; j < getPlayerList().size(); j++)
            {
                if (name.equals(getPlayerList().get(j).getUsername()))
                {
                    System.out.println("Error: username already taken(game)");
                    return;
                }
            }
        //nobody else has the same name, go ahead and add the new player
        Player p = new Player(name, victories);
        if (numberOfPlayers < 4) //check that game has less than 4 players
        {
            playerList.add(p);
            numberOfPlayers++;
        }
        else
        {
            System.out.println("Error: too many players in that game");
        }
    }
    public void removePlayer(Player p)
    {
        for(int i = 0; i < playerList.size(); i++)
        {
            if(playerList.get(i).username.equals(p.username))
            {
                playerList.remove(i);
                numberOfPlayers--;
            }
        }

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



}