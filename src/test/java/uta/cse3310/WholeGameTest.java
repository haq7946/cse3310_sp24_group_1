package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/*Testing the whole game */

public class WholeGameTest 
extends TestCase
{
    //Testing the wholegame
    //Variables needed
    Lobby lobby = new Lobby();
    //PLayers that will join the lobby and play the game
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    Player player3 = new Player("player3");
    Player player4 = new Player("player4");


    public WholeGameTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(WholeGameTest.class);
    }

    //Here we will test if players navigate through the lobby
    public void testGame()
    {
        ///////////////////////////////////Testing LOBBY
        //Adding the players in the lobby
        lobby.addPlayer(player1.username, Integer.toString(player1.numberOfVictores));
        assertEquals(1, lobby.playerList.size());
        lobby.addPlayer(player2.username, Integer.toString(player2.numberOfVictores));
        lobby.addPlayer(player3.username, Integer.toString(player3.numberOfVictores));
        assertEquals(3, lobby.playerList.size());
        lobby.addPlayer(player4.username, Integer.toString(player4.numberOfVictores));
        assertEquals(4, lobby.playerList.size());

        //Checking if lobby chat is working
        ServerEvent S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "sendChatLobby";
        S.event = "chatEvent";
        S.player = lobby.playerList.get(0);
        S.message = "This is player 1.";
        S.victores = "0";

        //Let's update lobby after the event is sent
        lobby.updateLobby(S);

        //Check if the chat is sent and seen by other players
        assertEquals(S.player.username + " : " + S.message, lobby.playerChat.get(1)); //Is the chat sent? //Do all players see it?
        System.out.println(S.player.username + " sent a message  (whole game test)");
        System.out.println(lobby.playerChat.get(1));

        //Let's create a room
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "createRoomButton";  
        S.event = "lobbyEvent";         
        S.player = lobby.playerList.get(0); //player1 creates a room              
        S.victores = Integer.toString(player1.numberOfVictores);

        //update the lobby
        lobby.updateLobby(S);

        //Is the room created?
        assertEquals(lobby.gameMakers.get(0).username, lobby.playerList.get(0).username); //Has player 1 created a room?
        System.out.println("room created by player 1");

        //Let player2 also create a room
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "createRoomButton";  
        S.event = "lobbyEvent";         
        S.player = lobby.playerList.get(1); //player2 creates a room              
        S.victores = Integer.toString(player2.numberOfVictores);

        //update lobby
        lobby.updateLobby(S);

        //Are there 2 rooms?
        for (int i = 0; i < 2; i++)
        {
        assertEquals(lobby.gameMakers.get(i).username, lobby.playerList.get(i).username);
        }
        System.out.println("There are currently 2 rooms");


        //PLayer 3 decides to leave the game
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.event = "lobbyEvent";
        S.button = "backButton";
        S.player = lobby.playerList.get(2);
        S.victores = Integer.toString(player3.numberOfVictores);
        System.out.println("Player 3 left the lobby");

        //update lobby
        lobby.updateLobby(S);

        //Are there 3 players? since one of them left
        assertEquals(3, lobby.playerList.size());
        System.out.println("There are 3 players in the lobby");

        //Make sure the rooms are still up
        assertEquals(2, lobby.gameList.size());
        //////////////////////////////////////LOBBY TEST ENDS HERE

        //////////////////////////////////////////////CONTINUE WITH GAME ROOM TESTING HERE
        //3 players decide to join player1's room
        //Player 1 joins the rooms
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = lobby.playerList.get(0);            //who did it
        S.victores = Integer.toString(player1.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 1 joined the room");

        //Player 2 joins the rooms
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = lobby.playerList.get(1);            //who did it
        S.victores = Integer.toString(player2.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 2 joined the room");

        //Player 4 joins the rooms   //This is because player 3 left
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = lobby.playerList.get(2);            //who did it
        S.victores = Integer.toString(player4.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 4 joined the room");

        //Make sure all three players are in the same room
        assertEquals(3, lobby.gameList.get(0).playerList.size());  //There should be 3 players in the room
        assertEquals("Game created", lobby.gameList.get(0).playerChat.get(0)); //make sure the chat is active
        //NOTE::::://All three players are in the room at this point


        //Testing the gameChat here
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "sendChat";
        S.event = "chatEvent";
        S.player = lobby.playerList.get(1); //Player 2 sends a chat in the game chat
        S.iidd = lobby.playerList.get(1).iD;
        S.message = "start game bro";
        S.victores = Integer.toString(player2.numberOfVictores);

        //update the state of the lobby
        lobby.updateLobby(S);
        //Did the chat display??
        assertEquals(lobby.playerList.get(1).username + " : " + S.message, lobby.gameList.get(0).playerChat.get(1));



        //Player 1 starts a game
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "startGame";  
        S.event = "gameEvent";        
        //timer = 0;  //This is on javaScript side
        S.player = lobby.playerList.get(0); //Player 1 starts a game             
        S.iidd = lobby.playerList.get(0).iD;
        S.victores = Integer.toString(player1.numberOfVictores);

        //update the state of the lobby
        lobby.updateLobby(S);

        System.out.println("The game has started..");  //At this point the game has started (PLayer 1, Player 2, Player 4 are in the same room)




        //////////////////////////////////////////////GAME ROOM TESTING ENDS HERE

    }



}