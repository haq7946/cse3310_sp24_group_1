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
    private static ArrayList<Game> gameList; // list of current games
    private static ArrayList<Player> playerList; // list of players that are in the lobby (i.e. players not currently in
                                                 // a game)
    // private ArrayList<Player> leaderboardList; this is going to be a PointBoard
    // i'm pretty sure - AE

    public Lobby() 
    {
        gameList = new ArrayList<Game>();
        playerList = new ArrayList<Player>();
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
        Player p = new Player(name, color);
        playerList.add(p);
    }

    public void addPlayer(String name) // add a new player to the lobby
    {
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

    public void globalChat(String message) // no idea how chat is going to work lmao
    {

    }

    public void updateState(UserEvent U)
    {

    }
}
