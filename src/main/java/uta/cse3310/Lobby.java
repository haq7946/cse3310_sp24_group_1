package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Lobby 
{
    public ArrayList<Game> gameList; // list of current games
    public ArrayList<Player> gameMakers; //list that corresponds to makers of each game
    public ArrayList<Player> playerList; // list of players that are in the lobby (i.e. players not currently in a game)
    public ArrayList<String> playerChat; //list of message history sent
    // private ArrayList<Player> leaderboardList; this is going to be a PointBoard
    // i'm pretty sure - AE

    public Lobby() 
    {
        gameList = new ArrayList<Game>();
        gameMakers = new ArrayList<Player>();
        playerList = new ArrayList<Player>();
        playerChat = new ArrayList<String>();
        //makeGame(); why is this here
    }

    public ArrayList<Game> getGamelist() // return the game list. probably never calling this method but w/e
    {
        return gameList;
    }

    public Game makeGame() // make a new game. called when a player clicks the "create new game" button
    {
        Game g = new Game();
        gameList.add(g); // add the new game to the list of games
        return g;
    }

    public void joinGame(Game g, Player p) // adds a player to a game and removes them from the lobby
    {
        g.addPlayer(p);
        playerList.remove(p);
    }

    public void addPlayer(String name, int color) // add a new player to the lobby
    {
        for (int i = 0; i < playerList.size(); i++) //check players in lobby to see if they already have the name
        {
            if(name.equals(playerList.get(i).getUsername()))
            {
                System.out.println("Error: username already taken (lobby)");
                return;
            }
        }
        for (int i = 0; i < gameList.size(); i++) //check players in games to see if they already have the name
        {
            for (int j = 0; j < gameList.get(i).getPlayerList().size(); j++)
            {
                if (name.equals(gameList.get(i).getPlayerList().get(j).getUsername()))
                {
                    System.out.println("Error: username already taken(game)");
                    return;
                }
            }
        }
        Player p = new Player(name, color);
        playerList.add(p);
    }

    public void addPlayer(String name) // add a new player to the lobby
    {
        for (int i = 0; i < playerList.size(); i++) //check players in lobby to see if they already have the name
        {
            if(name.equals(playerList.get(i).getUsername()))
            {
                System.out.println("Error: username already taken (lobby)");
                return;
            }
        }
        for (int i = 0; i < gameList.size(); i++) //check players in games to see if they already have the name
        {
            for (int j = 0; j < gameList.get(i).getPlayerList().size(); j++)
            {
                if (name.equals(gameList.get(i).getPlayerList().get(j).getUsername()))
                {
                    System.out.println("Error: username already taken(game)");
                    return;
                }
            }
        }
        //nobody else has the same name, go ahead and add the new player
        Player p = new Player(name);
        playerList.add(p);
    }

    public void toPlayerSelect() // wtf is this method supposed to do
    {

    }

    public void toLobby(Player p, Game g) // removes a player from the game they are in and sends them back to the lobby
    {
        g.removePlayer(p); // remove from game player is in
        playerList.add(p); // add him back to lobby list
    }

    public Player[] updateLeaderBoard(Player[] players) 
    {
        return null;
    }

    public void globalChat(String message) // adds message to the arraylist of messages to be displayed
    {
        playerChat.add(message); //Works exactly the same as the chat in game.java
    }

    public void updateLobby(ServerEvent S) //this is the worst code i have ever written
    {
        if(S.event.compareTo("lobbyEvent") == 0)  //Execute lobby stuff because it is a lobby event
        {
            if(S.button.compareTo("nameButton")==0)  //This executes when nameButton is pressed
            {
                addPlayer(S.player.username);  //Adding player to the lobby
                System.out.println("Added player in the lobby"); //Debug
            }
            else if(S.button.compareTo("createRoomButton")==0)  //This executes when create room button is pressed
            {
                makeGame();  //This creates a room commented out for now
                Player p = new Player(S.player.username);
                gameMakers.add(p);    
                System.out.println("Made a game");
            }
            else if(S.button.compareTo("joinRoomButton")==0) //This executes when room button is pressed
            {
                Player P = new Player(S.player.username);
                System.out.println("||||||||||||||||||||||||| The occurrence is " + S.occurrence);
                gameList.get(S.occurrence-1).addPlayer(P);
                System.out.println("Addplayer failed/succeeded");
            }
            else if(S.button.compareTo("backButton")==0)
            {
                //Remove player from the list
            }
            
        }
        else if(S.event.compareTo("gameEvent") == 0)   //Execute game stuff beacause it is game event
        {
            
        }
    }
}
//TODO: When you create a room it should forcefully put you in that room
//When room is empty, should immediately kill itself
//Redirecting people to the same room