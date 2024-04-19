package uta.cse3310;

import java.util.ArrayList;

public class ServerData //this class exists to send all data about everything to every game client
{
    ServerEvent ev;
    public ArrayList<Player> playerList;
    public Player player;
    public String playerName;

    public ArrayList<Game> gameList;
    public Game game;
    public String GameID;
    
    int wordGridX;
    int wordGridY;
    ////Board
    //Chat will go here eventually
    ServerData()
    {

    }
    ServerData(String _GameID, String _playerName)
    {
        GameID = _GameID;
        playerName = _playerName;
    }
}
