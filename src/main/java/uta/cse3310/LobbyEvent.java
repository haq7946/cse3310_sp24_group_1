package uta.cse3310;

import java.util.ArrayList;

public class LobbyEvent 
{
    public ArrayList<Player> playerList;
    public Player player;
    public String playerName;

    public ArrayList<Game> gameList;
    public Game game;
    public String GameID;
   
    public boolean nameButton, createRoomButton, joinRoomButton, backButton, exitGameButton;
    
    int wordGridX;
    int wordGridY;
    ////Board
    //Chat will go here eventually
    LobbyEvent()
    {

    }
    LobbyEvent(String _GameID, String _playerName)
    {
        GameID = _GameID;
        playerName = _playerName;
    }
}
