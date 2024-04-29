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


        //Testing the gameChat here  //PLayer 2 sends chat
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

        //PLayer 4 sends chat
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "sendChat";
        S.event = "chatEvent";
        S.player = lobby.playerList.get(2); //Player 4 sends a chat in the game chat
        S.iidd = lobby.playerList.get(2).iD;
        S.message = "wait";
        S.victores = Integer.toString(player4.numberOfVictores);

        //update the lobby
        lobby.updateLobby(S);
        //check if the chat is there and it matches
        assertEquals(lobby.playerList.get(2).username + " : " + S.message, lobby.gameList.get(0).playerChat.get(2));


        ////////////////////////HARD CHECKING GAME CHAT//////////////////////
        //Print all of the chats sent to game chat
        for(int i = 0; i < lobby.gameList.get(0).playerChat.size(); i++)
        {
            System.out.println(lobby.gameList.get(0).playerChat.get(i));
            //make sure everyone can see it, becasue javascript only just prints what is in the arraylist
            if(i == 0)
            {
                assertEquals("Game created", lobby.gameList.get(0).playerChat.get(i));
            }
            else if(i == 1)
            {
                assertEquals("player2 : start game bro", lobby.gameList.get(0).playerChat.get(i));
            }
            else if(i == 2)
            {
                assertEquals("player4 : wait", lobby.gameList.get(0).playerChat.get(i));
            }
        }
        ///////////////////////GAME CHAT IS PERFECT////////////////////////////////

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

        String env_variable = System.getenv("TEST_GRID");
        if(env_variable != null)
        {
            System.out.println("Environmental variable is set for testing.");
        }
        else
        {
            System.out.println("Environmental variable is not set for testing. Exit");
            
        }

        if(env_variable != null)
        if(env_variable.compareTo("2") == 0)
        {
            //Board has been initialized
            //Game has started
            //environmental_variable is 2 //aka seed is 2 //Refer to seed document to take a look at the board

            //Player one selects a word // The word is heaven (18, 0) (23, 0) //firstClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "h"; //first letter clicked is h
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 18;
            S.y = 0;

            //update the lobby state to process the first click
            lobby.updateLobby(S);

            //Player one is still selcting the word heaven // secondClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "n"; //first letter clicked is h
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 23;
            S.y = 0;

            //update the lobby state
            lobby.updateLobby(S);

            //make sure we've selected the word heaven
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("horizontal", lobby.gameList.get(0).checkOrientation(18, 0, 23, 0));
            assertEquals("heaven", lobby.gameList.get(0).selectWord(18, 0, 23, 0));
            System.out.println("HORIZONTAL WORD TEST: PASSED");


            //Player 2 selects a word // The word is martial (1, 2) (1, 8) //firstClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(1);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "m"; //first letter clicked is h
            S.iidd = lobby.playerList.get(1).iD;
            S.victores = Integer.toString(player2.numberOfVictores);
            S.x = 1;
            S.y = 2;

            //update the lobby state
            lobby.updateLobby(S);

            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(1);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "l"; //first letter clicked is h
            S.iidd = lobby.playerList.get(1).iD;
            S.victores = Integer.toString(player2.numberOfVictores);
            S.x = 1;
            S.y = 8;

            //update the lobby state
            lobby.updateLobby(S);

            //Check if the word selected is martial //vertical
            //make sure we've selected the word heaven
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("vertical", lobby.gameList.get(0).checkOrientation(1, 2, 1, 8));
            assertEquals("martial", lobby.gameList.get(0).selectWord(1, 2, 1, 8));
            System.out.println("VERTICAL WORD TEST: PASSED");


            //PLayer 4 selects a word //agricultural (4, 15) (4, 4) //first Click //Vertical up
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(2);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "a"; //first letter clicked is a
            S.iidd = lobby.playerList.get(2).iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 4;
            S.y = 15;

            //updaet the state of lobby
            lobby.updateLobby(S);

            //Second click for player 4
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(2);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "l"; //first letter clicked is l
            S.iidd = lobby.playerList.get(2).iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 4;
            S.y = 4;

            //update the lobby state
            lobby.updateLobby(S);

            //check for agricultural
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("vertical", lobby.gameList.get(0).checkOrientation(4, 15, 4 ,4));
            assertEquals("agricultural", lobby.gameList.get(0).selectWord(4, 15, 4, 4));
            System.out.println("VERTICAL UP WORD TEST: PASSED");


            //PLayer 1 selects a word //magic (16, 6) (20, 10) //first Click //diagonal down
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "m"; //first letter clicked is m
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 16;
            S.y = 6;

            //update the lobby
            lobby.updateLobby(S);

            //Second click for player 1
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "c"; //first letter clicked is c
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 20;
            S.y = 10;

            lobby.updateLobby(S);

             //check for magic
             assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
             assertEquals("diagonal", lobby.gameList.get(0).checkOrientation(16, 6, 20, 10));
             assertEquals("magic", lobby.gameList.get(0).selectWord(16, 6, 20, 10));
             System.out.println("DIAGONAL DOWN WORD TEST: PASSED");


            // //PLayer 1 selects a word //crowd (24, 21) (28, 17) //first Click //diagonal up
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "c"; //first letter clicked is c
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 24;
            S.y = 21;

            lobby.updateLobby(S);

           //Second click for crowd by player 1
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = lobby.playerList.get(0);
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "d"; //first letter clicked is m
            S.iidd = lobby.playerList.get(0).iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 28;
            S.y = 17;

            lobby.updateLobby(S);

            //check for crowd
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("diagonal", lobby.gameList.get(0).checkOrientation(24, 21, 28, 17));
            assertEquals("crowd", lobby.gameList.get(0).selectWord(24, 21, 28, 17));
            System.out.println("DIAGONAL UP WORD TEST: PASSED");



            //The game ends here //We check for winners and check the exit conditions


        }
        


        //////////////////////////////////////////////GAME ROOM TESTING ENDS HERE

    }



}