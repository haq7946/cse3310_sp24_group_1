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
    Player player5 = new Player("player5");
    Player player6 = new Player("player6");//Might not use this but doens't hurt to test the code properly


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
        S.player = player1; //player1 creates a room              
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
        S.player = player2; //player2 creates a room              
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
        S.player = player1;            //who did it
        S.victores = Integer.toString(player1.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //Set player1's game ID since javascript does that for us
        player1.iD = lobby.gameList.get(S.occurrence - 1).gameID;
        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 1 joined the room");

        //Player 2 joins the rooms
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = player2;            //who did it
        S.victores = Integer.toString(player2.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //Set player2's game ID since javascript does that for us
        player2.iD = lobby.gameList.get(S.occurrence - 1).gameID;
        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 2 joined the room");
        

        //Player 4 joins the rooms   //This is because player 3 left
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = player4;            //who did it
        S.victores = Integer.toString(player4.numberOfVictores); //
        S.occurrence = 1; //Since we are joining player1's room

        //Set player4's game ID since javascript does that for us
        player4.iD = lobby.gameList.get(S.occurrence - 1).gameID;
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
        S.player = player2; //Player 2 sends a chat in the game chat
        S.iidd = player2.iD;
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
        S.player = player4; //Player 4 sends a chat in the game chat
        S.iidd = player4.iD;
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
        S.player = player1; //Player 1 starts a game             
        S.iidd = player1.iD;
        S.victores = Integer.toString(player1.numberOfVictores);
        //update the state of the lobby
        lobby.updateLobby(S);

        System.out.println("The game has started..");  //At this point the game has started (PLayer 1, Player 2, Player 4 are in the same room)

        //At this point game 1 has already started //let's check if it is joinable
        assertEquals(true, lobby.gameList.get(0).gameHasStarted);
        assertEquals(false, lobby.gameList.get(0).isAvailableToJoin);

        String env_variable = System.getenv("TEST_GRID");
        if(env_variable != null)
        {
            System.out.println("Environmental variable is set for testing.");
            System.out.println("The environment variable is " + env_variable);
        }
        else
        {
            System.out.println("Environmental variable is not set for testing. Exit");
            System.out.println("Try setting environment varaible to test the GRID");
            System.out.println("Try.... (export TEST_GRID=2) in the command line");
            
        }

        //////////////////////////////////////////////////////////SEEED 2///////////////////
        if(env_variable != null)
        if(env_variable.compareTo("2") == 0)    //If the environmental variable is 2
        {
            //Board has been initialized
            //Game has started
            //environmental_variable is 2 //aka seed is 2 //Refer to seed document to take a look at the board

            //Player one selects a word // The word is heaven (18, 0) (23, 0) //firstClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "h"; //first letter clicked is h
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 18;
            S.y = 0;
            //Javascript handles this for us
            player1.x1 = S.x;
            player1.y1 = S.y;

            //update the lobby state to process the first click
            lobby.updateLobby(S);

            //Player one is still selcting the word heaven // secondClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "n"; //first letter clicked is h
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 23;
            S.y = 0;
            //Javascript handles this for us
            player1.x2 = S.x;
            player1.y2 = S.y;

            //update the lobby state
            lobby.updateLobby(S);

            //Are we broadcasting that player 1 has found this word
            //Since this is player 1 color will always be red
            assertEquals(1, lobby.gameList.get(0).colorToShow);

            //make sure we've selected the word heaven
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("horizontal", lobby.gameList.get(0).checkOrientation(player1.x1, player1.y1, player1.x2, player1.y2));
            assertEquals("heaven", lobby.gameList.get(0).selectWord(player1.x1, player1.y1, player1.x2, player1.y2));
            System.out.println("|||| HORIZONTAL WORD TEST: PASSED ||||");


            //Player 2 selects a word // The word is martial (1, 2) (1, 8) //firstClick
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player2;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "m"; //first letter clicked is h
            S.iidd = player2.iD;
            S.victores = Integer.toString(player2.numberOfVictores);
            S.x = 1;
            S.y = 2;
            //Javascript handles this for us
            player2.x1 = S.x;
            player2.y1 = S.y;

            //update the lobby state
            lobby.updateLobby(S);

            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player2;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "l"; //first letter clicked is h
            S.iidd = player2.iD;
            S.victores = Integer.toString(player2.numberOfVictores);
            S.x = 1;
            S.y = 8;
            //Javascript handles this for us
            player2.x2 = S.x;
            player2.y2 = S.y;

            //update the lobby state
            lobby.updateLobby(S);

            //Are we broadcasting that player 2 has found this word
            //Since this is player 2 color will always be orange
            assertEquals(2, lobby.gameList.get(0).colorToShow);

            //Check if the word selected is martial //vertical
            //make sure we've selected the word heaven
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("vertical", lobby.gameList.get(0).checkOrientation(player2.x1, player2.y1, player2.x2, player2.y2));
            assertEquals("martial", lobby.gameList.get(0).selectWord(player2.x1, player2.y1, player2.x2, player2.y2));
            System.out.println("|||| VERTICAL WORD TEST: PASSED ||||");


            //PLayer 4 selects a word //agricultural (4, 15) (4, 4) //first Click //Vertical up
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player4;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "a"; //first letter clicked is a
            S.iidd = player4.iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 4;
            S.y = 15;
            //Javascript handles this for us
            player4.x1 = S.x;
            player4.y1 = S.y;

            //updaet the state of lobby
            lobby.updateLobby(S);

            //Second click for player 4
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player4;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "l"; //first letter clicked is l
            S.iidd = player4.iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 4;
            S.y = 4;
            //Javascript handles this for us
            player4.x2 = S.x;
            player4.y2 = S.y;

            //update the lobby state
            lobby.updateLobby(S);

            //Are we broadcasting that player 4 has found this word
            //Since this is player 3(in the order) color will always be blue
            assertEquals(3, lobby.gameList.get(0).colorToShow);

            //check for agricultural
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("vertical", lobby.gameList.get(0).checkOrientation(player4.x1, player4.y1, player4.x2, player4.y2));
            assertEquals("agricultural", lobby.gameList.get(0).selectWord(player4.x1, player4.y1, player4.x2, player4.y2));
            System.out.println("|||| VERTICAL UP WORD TEST: PASSED ||||");


            //PLayer 1 selects a word //magic (16, 6) (20, 10) //first Click //diagonal down
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "m"; //first letter clicked is m
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 16;
            S.y = 6;
            //Javascript handles this for us
            player1.x1 = S.x;
            player1.y1 = S.y;

            //update the lobby
            lobby.updateLobby(S);

            //Second click for player 1
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "c"; //first letter clicked is c
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 20;
            S.y = 10;
            //Javascript handles this for us
            player1.x2 = S.x;
            player1.y2 = S.y;

            lobby.updateLobby(S);

            //Are we broadcasting that player 1 has found this word
            //Since this is player 1(in the order) color will always be red
            assertEquals(1, lobby.gameList.get(0).colorToShow);

             //check for magic
             assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
             assertEquals("diagonal", lobby.gameList.get(0).checkOrientation(player1.x1, player1.y1, player1.x2, player1.y2));
             assertEquals("magic", lobby.gameList.get(0).selectWord(player1.x1, player1.y1, player1.x2, player1.y2));
             System.out.println("|||| DIAGONAL DOWN WORD TEST: PASSED ||||");


            // //PLayer 1 selects a word //crowd (24, 21) (28, 17) //first Click //diagonal up
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "c"; //first letter clicked is c
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 24;
            S.y = 21;
            //Javascript handles this for us
            player1.x1 = S.x;
            player1.y1 = S.y;

            lobby.updateLobby(S);

           //Second click for crowd by player 1
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player1;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "d"; //first letter clicked is m
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);
            S.x = 28;
            S.y = 17;
            //Javascript handles this for us
            player1.x2 = S.x;
            player1.y2 = S.y;

            lobby.updateLobby(S);

            //Since this is player 1(in the order) color will always be red
            assertEquals(1, lobby.gameList.get(0).colorToShow);

            //check for crowd
            assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("diagonal", lobby.gameList.get(0).checkOrientation(player1.x1, player1.y1, player1.x2, player1.y2));
            assertEquals("crowd", lobby.gameList.get(0).selectWord(player1.x1, player1.y1, player1.x2, player1.y2));
            System.out.println("|||| DIAGONAL UP WORD TEST: PASSED ||||");


            //Checking to see the conditions if the word isn't valid
            //PLayer 4 selects a word //The word is not valid // It is random jargon  kizvzw (4, 2) (9, 2)
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player4;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "k"; //first letter clicked is k
            S.iidd = player4.iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 4;
            S.y = 2;
            //Javascript handles this for us
            player4.x1 = S.x;
            player4.y1 = S.y;

            //We know what this does by now
            lobby.updateLobby(S);

            //Player 4 clicks a second time
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.player = player4;
            S.button = "boardClick";
            S.event = "gameEvent";
            S.message = "w"; //first letter clicked is w
            S.iidd = player4.iD;
            S.victores = Integer.toString(player4.numberOfVictores);
            S.x = 9;
            S.y = 2;
            //Javascript handles this for us
            player4.x2 = S.x;
            player4.y2 = S.y;

            //Update
            lobby.updateLobby(S);

            //check if kizvzw is a valid word
            assertEquals("resetBoard", lobby.gameList.get(0).boardButtonMessage);
            assertEquals("horizontal", lobby.gameList.get(0).checkOrientation(player4.x1, player4.y1, player4.x2, player4.y2));
            assertEquals("kizvzw", lobby.gameList.get(0).selectWord(player4.x1, player4.y1, player4.x2, player4.y2));
            System.out.println("|||| JARGON WORD TEST: PASSED ||||");

            //Since we got here without failing the color broadcast test. This means we are showing the right color for each player
            System.out.println("|||| COLOR BROADCAST TEST: PASSED ||||");

            //////////////////////////////////////////GAME EXIT CONDITIONS///////////////
            //The game ends here //We check for winners and check the exit conditions
            S = new ServerEvent("", "", null, 0, "", "", "");
            S.button = "victoryCheck";
            S.event = "gameEvent";
            S.player = player1;
            S.iidd = player1.iD;
            S.victores = Integer.toString(player1.numberOfVictores);

            //update the state of the lobby
            lobby.updateLobby(S);
            //Check if the game ended
            assertEquals("end", lobby.gameList.get(0).gameResponse);
            System.out.println("|||| GAME ENDED : PASSED ||||");

            //Check if the winner is player with the most points
            assertEquals("player1", lobby.gameList.get(0).winners.get(0).username);
            System.out.println("|||| WINNER CHECK : PASSED ||||");

            //The game is finshed now.
            //Is the game joinable?
            assertEquals(false, lobby.gameList.get(0).isAvailableToJoin);
            System.out.println("|||| GAME EXIT CONDITION : PASSED ||||");
            //////////////////////////////////////////////GAME ROOM TESTING ENDS HERE


        } ///////////////////////////////SEED 2 /////////////////////////////ENDS HERE

        if(env_variable != null)  //////////////////////////////////SEED 3///////
        if(env_variable.compareTo("3") == 0) //If the environmental variable is 3
        {
        //Horizontal word future (8,0) (13,0)
        //vertical down word seas (33, 6) (33, 9)
        //vertical up word musical (0, 27) (0, 21)
        //diagonal up word oscar (15, 13) (19, 9)
        //diagonal down word paste (2, 30) (6, 34)
        //For reference Players 1,2,4 are also in the lobby
        //Player 1 and PLayer 2 have created rooms
        //Player 5 joins the lobby
        lobby.addPlayer(player5.username, Integer.toString(player5.numberOfVictores));
        assertEquals(4, lobby.playerList.size());  //Are there 4 people in the lobby?
        //Player 6 joins the lobby
        lobby.addPlayer(player6.username, Integer.toString(player6.numberOfVictores));
        assertEquals(5, lobby.playerList.size());  //Are there 5 people in the lobby?

        //Let's make Player 5 and Player 6 join PLayer2's room
        //So we can test the rooms simultaneously
        //Player 5 joins the rooms created by Player 2
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = player5;            //who did it
        S.victores = Integer.toString(player5.numberOfVictores); //
        S.occurrence = 2; //Since we are joining player1's room

        //Set player1's game ID since javascript does that for us
        player5.iD = lobby.gameList.get(S.occurrence - 1).gameID;
        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 5 joined the room");


        //Player 6 joins the rooms created by Player 2
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "joinRoomButton";  //What button was pressed
        S.event = "lobbyEvent";       //what kind of event it was
        S.player = player6;            //who did it
        S.victores = Integer.toString(player6.numberOfVictores); //
        S.occurrence = 2; //Since we are joining player1's room

        //Set player1's game ID since javascript does that for us
        player6.iD = lobby.gameList.get(S.occurrence - 1).gameID;
        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("Player 6 joined the room");

        //Here we have Player1, Player2, Player4 in the first room
        //Player5, Player6 in the second room
        //Let's make sure that this information is true
        assertEquals(3, lobby.gameList.get(0).playerList.size()); //Are there 3 people in the first room?
        assertEquals(2, lobby.gameList.get(1).playerList.size()); //Are there 2 people in the second room?
        System.out.println("|||| ROOM SIZE TEST : PASSED ||||");

        //Let's start the game in room 2
        //Player 5 starts a game
        S = new ServerEvent("", "", null, 0, "", "", "");
        S.button = "startGame";  
        S.event = "gameEvent";        
        //timer = 0;  //This is on javaScript side
        S.player = player5; //Player 5 starts a game in room 2             
        S.iidd = player5.iD;
        S.victores = Integer.toString(player5.numberOfVictores);
        //update the state of the lobby
        lobby.updateLobby(S);
        System.out.println("The game has started.. in room 2");

        //At this point game 1 has already started //let's check if it is joinable
        assertEquals(true, lobby.gameList.get(0).gameHasStarted);
        assertEquals(false, lobby.gameList.get(0).isAvailableToJoin);
        //At this point game 2 has already started as well. //let's check if it is joinable
        assertEquals(true, lobby.gameList.get(1).gameHasStarted);
        assertEquals(false, lobby.gameList.get(1).isAvailableToJoin);

        /////////////////////Testing if 2 games can be played simultaneously

        //Let's check if two players can select the same word in two different rooms
        //Player 1 and Player 5 will select the word future at the same time different rooms
        //Player 1 selects a word // The word is future (8,0) (13,0) //firstClick //Room 1
         S = new ServerEvent("", "", null, 0, "", "", "");
         S.player = player1;
         S.button = "boardClick";
         S.event = "gameEvent";
         S.message = "f"; //first letter clicked is f
         S.iidd = player1.iD;
         S.victores = Integer.toString(player1.numberOfVictores);
         S.x = 8;
         S.y = 0;
         //Javascript handles this for us
         player1.x1 = S.x;
         player1.y1 = S.y;

         //update the lobby state to process the first click for player1
         lobby.updateLobby(S);

         //Player 5 also selects the word future // The word is future (8,0) (13,0) //firstClick //Room 2
          S = new ServerEvent("", "", null, 0, "", "", "");
          S.player = player5;
          S.button = "boardClick";
          S.event = "gameEvent";
          S.message = "f"; //first letter clicked is h
          S.iidd = player5.iD;
          S.victores = Integer.toString(player5.numberOfVictores);
          S.x = 8;
          S.y = 0;
        //Javascript handles this for us
         player5.x1 = S.x;
         player5.y1 = S.y;

          //update the lobby state to process the first click for player5
          lobby.updateLobby(S);

          //Processing Second click for player1
          S = new ServerEvent("", "", null, 0, "", "", "");
          S.player = player1;
          S.button = "boardClick";
          S.event = "gameEvent";
          S.message = "e"; //first letter clicked is f
          S.iidd = player1.iD;
          S.victores = Integer.toString(player1.numberOfVictores);
          S.x = 13;
          S.y = 0;
          //Javascript handles this for us
          player1.x2 = S.x;
          player1.y2 = S.y;

          //update the lobby
          lobby.updateLobby(S);

          //Processing Second click for player5
          S = new ServerEvent("", "", null, 0, "", "", "");
          S.player = player5;
          S.button = "boardClick";
          S.event = "gameEvent";
          S.message = "e"; //first letter clicked is h
          S.iidd = player5.iD;
          S.victores = Integer.toString(player5.numberOfVictores);
          S.x = 13;
          S.y = 0;
          //Javascript handles this for us
         player5.x2 = S.x;
         player5.y2 = S.y;

          //update the lobby
          lobby.updateLobby(S);

          //System.out.println("|||||||||||||||||" + player1.x1 + player1.y1 + player1.x2 + player1.y2);
          //Lets check if the horizontal word is correct in both rooms //future (8,0) (13,0)
          assertEquals("updateBoard", lobby.gameList.get(0).boardButtonMessage);
          assertEquals("horizontal", lobby.gameList.get(0).checkOrientation(player1.x1, player1.y1, player1.x2, player1.y2));
          assertEquals("future", lobby.gameList.get(0).selectWord(player1.x1, player1.y1, player1.x2, player1.y2));
          System.out.println("|||| HORIZONTAL WORD TEST: PASSED (ROOM 1)||||");

          assertEquals("updateBoard", lobby.gameList.get(1).boardButtonMessage);
          assertEquals("horizontal", lobby.gameList.get(1).checkOrientation(player5.x1, player5.y1, player5.x2, player5.y2));
          assertEquals("future", lobby.gameList.get(1).selectWord(player5.x1, player5.y1, player5.x2, player5.y2));
          System.out.println("|||| HORIZONTAL WORD TEST: PASSED (ROOM 2)||||");


        

        }
        ////////////////////////////////////////////////////////SEED 3////////////


            //Let's check if the player's color match
            //We always assign color when game is started in this specific order 
            // 1 - red  2- orange  3-blue  4-green
            for(int i = 0; i < lobby.gameList.get(0).playerList.size(); i++)
            {
                assertEquals(i + 1, lobby.gameList.get(0).playerList.get(i).color);
            }
            System.out.println("|||| PLAYER COLOR TEST : PASSED ||||");


    }



}