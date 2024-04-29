package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LobbyTest extends TestCase
{
    Lobby lobby = new Lobby();
    Player player = new Player("gamer");
    Player player2 = new Player("gamer");
    Player player3 = new Player("gamerr");
    public LobbyTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(LobbyTest.class);
    }
    //Testing general lobby functions
    public void testLobbyFunctions() 
    {
        //Adding a player
        lobby.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        assertEquals(1, lobby.playerList.size());
        lobby.addPlayer(player2.username, Integer.toString(player2.numberOfVictores));
        lobby.addPlayer(player3.username, Integer.toString(player3.numberOfVictores));
        assertEquals(2, lobby.playerList.size());
        //Removing a player
        ServerEvent S = new ServerEvent("", "", null, 0, "", "", "");
        S.event = "lobbyEvent";
        S.button = "backButton";
        S.victores = "0";
        S.player = lobby.playerList.get(0);
        lobby.updateLobby(S);    
        assertEquals(1, lobby.playerList.size());
        assertEquals("gamerr", lobby.playerList.get(0).username);
        //Player 3 chatting
        S.event = "chatEvent";
        S.button = "sendChat";
        S.player = lobby.playerList.get(0);
    }
    //Testing general room functions
    public void testRoomFunctions()
    {
        lobby.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        lobby.addPlayer(player3.username, Integer.toString(player3.numberOfVictores));
        ServerEvent S = new ServerEvent("", "", null, 0, "", "", "");
        S.event = "lobbyEvent";
        //Making room
        S.button = "createRoomButton";
        S.victores = "0";
        S.player = lobby.playerList.get(0);
        lobby.updateLobby(S);
        assertEquals(1, lobby.gameList.size());
        //Adding players 1 and 3 into room 1
        S.button = "joinRoomButton";
        S.occurrence = 1;
        lobby.updateLobby(S);
        assertEquals(1, lobby.gameList.get(0).playerList.size());
        S.player = lobby.playerList.get(1);
        lobby.updateLobby(S);
        assertEquals(2, lobby.gameList.get(0).playerList.size());
        //Testing if id match system works
        assertEquals(lobby.playerList.get(0).iD, lobby.gameList.get(0).gameID);
        //Removing player 1 from game
        S.button = "backToLobbyButton";
        S.iidd = lobby.playerList.get(0).iD;
        S.player = lobby.playerList.get(0);
        lobby.updateLobby(S);
        assertEquals(1, lobby.gameList.get(0).playerList.size());
        assertEquals("gamerr", lobby.gameList.get(0).playerList.get(0).username);
        //Player 3 chatting by themselves, we would normally already have the player stored in P
        //From javascript and then sent via ServerEvent instead of accessing via get
        S.iidd = lobby.playerList.get(1).iD;
        S.player = lobby.playerList.get(1);
        S.button = "sendChat";
        S.event = "chatEvent";
        S.message = "Bing Chilling";
        //Only message right now should be the "Game created" message
        assertEquals("Game created", lobby.gameList.get(0).playerChat.get(0));
        lobby.updateLobby(S);
        //Checking after message sent
        assertEquals(2, lobby.gameList.get(0).playerChat.size());
        assertEquals("gamerr : Bing Chilling", lobby.gameList.get(0).playerChat.get(1));
    }

    public void testGameFunctions()
    {
        //Setting up for game
        lobby.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        lobby.addPlayer(player3.username, Integer.toString(player3.numberOfVictores));
        ServerEvent S = new ServerEvent("", "", null, 0, "", "", "");
        S.event = "lobbyEvent";
        S.button = "createRoomButton";
        S.victores = "0";
        S.player = lobby.playerList.get(0);
        lobby.updateLobby(S);
        S.button = "joinRoomButton";
        S.occurrence = 1;
        lobby.updateLobby(S);
        S.player = lobby.playerList.get(1);
        lobby.updateLobby(S);
        S.iidd = lobby.playerList.get(1).iD;
        //////////////////////////////////////////// Real testing now
        //Player 3 starts the game
        S.event = "gameEvent";
        S.button = "startGame";
        assertTrue(lobby.gameList.get(0).isAvailableToJoin);
        lobby.updateLobby(S);
        assertFalse(lobby.gameList.get(0).isAvailableToJoin);
        //Player 3 clicks
        S.button = "boardClick";
        assertFalse(lobby.gameList.get(0).playerList.get(1).firstClick);
        lobby.updateLobby(S);
        assertTrue(lobby.gameList.get(0).playerList.get(1).firstClick);
        //Player 3 should win; only first player can check victory
        S.player = lobby.playerList.get(0);
        S.button = "victoryCheck";
        lobby.gameList.get(0).playerList.get(1).score++;
        lobby.updateLobby(S);
        assertEquals("gamerr", lobby.gameList.get(0).winners.get(0).username);
    }
}
