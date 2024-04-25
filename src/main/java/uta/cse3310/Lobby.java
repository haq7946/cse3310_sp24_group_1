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
    //This will be used to broadcast that the specific room is full and the button needs to be disabled
    // private ArrayList<Player> leaderboardList; this is going to be a PointBoard
    // i'm pretty sure - AE
    public String serverResponse;  //This gives us a response when players join the room
    public String exitResponse;
    public Lobby() 
    {
        gameList = new ArrayList<Game>();
        gameMakers = new ArrayList<Player>();
        playerList = new ArrayList<Player>();
        playerChat = new ArrayList<String>();
        playerChat.add("Server started");

    }

    public ArrayList<Game> getGamelist() // return the game list. probably never calling this method but w/e
    {
        return gameList;
    }

    public Game makeGame() // make a new game. called when a player clicks the "create new game" button
    {
        Game g = new Game();
        g.playerChat.add("Game created");
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
                //Add a check for players
                for(int i = 0; i < playerList.size(); i++)
                {
                    //Find that player and set their id so everything is filtered for them only
                    if(P.username.equals(playerList.get(i).username))
                    {
                        playerList.get(i).iD = gameList.get(S.occurrence -1).gameID; //ocurrence corresponds to the id of the game in the list (if occurrence = 2 the we pick the game id of the second game )
                        System.out.println("The game id of " + P.username + " is: " + playerList.get(i).iD);
                        serverResponse = "gameIdResponse";
                    }
                }
                //Find a way to link the gameId with occurence
                //Add a disble button
            }
            else if(S.button.compareTo("backButton")==0)
            {
                Player P = new Player(S.player.username);
                //Remove player from the list
                for(int i = 0; i < playerList.size(); i++)
                {
                    System.out.println(playerList.get(i).username);
                    System.out.println("player username: " + P.username);
                    if(P.username.equals(playerList.get(i).username))
                    {
                        playerList.remove(i);
                        System.out.println("removed" + S.player);
                    }
                }
            }
            else if(S.button.compareTo("backToLobbyButton")==0)
            {
                Player P = new Player(S.player.username);
                //Bring them back to the lobby and remove them from the room
                //So we can assign them a new game ID if they join another room
                for(int i = 0; i < playerList.size(); i++)
                {
                    if(P.username.equals(playerList.get(i).username))
                    {
                        playerList.get(i).iD = "nothing";
                    }
                }

            }
            
        }
        else if(S.event.compareTo("gameEvent") == 0)   //Execute game stuff beacause it is game event
        {
            if(S.button.compareTo("startGame")==0)
            {
                System.out.println("In start game");
                for(int i = 0; i < gameList.size(); i++)
                {
                    System.out.println("PLayer ID " + S.iidd);
                    System.out.println("Game id " + gameList.get(i).gameID);
                    if(S.iidd.compareTo(gameList.get(i).gameID) == 0)
                    {
                        gameList.get(i).startGame();
                        System.out.println("Started game and response");
                        gameList.get(i).gameResponse = "start";
                    }
                }
            }
            else if(S.button.compareTo("boardResponse") == 0)
            {
                
            }
            else if(S.button.compareTo("boardClick") == 0)
            {
                System.out.println("Board has been pressed");
                System.out.println("Player: " + S.player.username);
                System.out.println( "Coardinates: " +S.x + "," + S.y);
                for(int i = 0; i < gameList.size(); i++)
                {
                    if(S.iidd.compareTo(gameList.get(i).gameID) == 0)  //Finding the specific game using the gameId
                    {
                        for(int j = 0; j < gameList.get(i).numberOfPlayers; j++)
                        {
                            if(S.player.username.compareTo(gameList.get(i).playerList.get(j).username) == 0) //Finds the player in the specific game
                            {
                                if(gameList.get(i).playerList.get(j).firstClick == true) //This means that this is their secind click
                                {
                                    System.out.println("Woohoo second click registered"); //We will eventually also store coordinates for the second click
                                    //Write game logic of what happens after second click
                                    //We set it to false after
                                    gameList.get(i).playerList.get(j).firstClick = false;
                                }
                                else
                                {
                                    gameList.get(i).playerList.get(j).x1 = S.x; //Set the x1 and y1 coordinates for the first click
                                    gameList.get(i).playerList.get(j).y1 = S.y;
                                    System.out.println(gameList.get(i).playerList.get(j).x1 +  gameList.get(i).playerList.get(j).y1);
                                    //We set it to true
                                    gameList.get(i).playerList.get(j).firstClick = true;
                                }
                            }
                        }
                    }
                }
            }
            
        }
        else if(S.event.compareTo("chatEvent") == 0 )
        {
            if(S.button.compareTo("sendChat") == 0)
            {
                for(int i = 0; i < gameList.size(); i++)
                {
                    if(S.iidd.compareTo(gameList.get(i).gameID) == 0)
                    {
                        System.out.println(S.message);
                        String message = S.player.username + " : " + S.message;
                        gameList.get(i).playerChat.add(message);
                    }
                }

            }
            else if(S.button.compareTo("sendChatLobby") == 0)
            {
                String message = S.player.username + " : " + S.message;
                playerChat.add(message);
            }
        }
        else if(S.event.compareTo("clockEvent") == 0)
        {
            
        }
    }
}
//TODO: When you create a room it should forcefully put you in that room
//When room is empty, should immediately kill itself
//Redirecting people to the same room