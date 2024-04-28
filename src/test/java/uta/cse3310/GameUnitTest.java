package uta.cse3310;

import junit.framework.TestCase;

public class GameUnitTest extends TestCase {

    public void testtrue(){
        assertTrue(true);
    }
    Game game = new Game();
    WordBank bank = new WordBank();
    Player player = new Player("gamer");
    Player player2 = new Player("gamerr");
    Player player3 = new Player("gamer");
    Player player4 = new Player("a");
    Player player5 = new Player("b");
    Player player6 = new Player("c");
    //Did the constructor work
    public void testGameInitialization() {
        assertNotNull(game.getGameID());
        assertEquals(0, game.getNumberOfPlayers());
        assertTrue(game.isAvailableToJoin());
    }
    //Checking for dupe names as well as counter for # of players
    public void testAddRemovePlayer() {
        game.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        assertEquals(1, game.getNumberOfPlayers());
        game.addPlayer(player3.username, Integer.toString(player3.numberOfVictores));
        assertEquals(1, game.getNumberOfPlayers());
        game.addPlayer(player2.username, Integer.toString(player2.numberOfVictores));
        game.addPlayer(player4.username, Integer.toString(player4.numberOfVictores));
        game.addPlayer(player5.username, Integer.toString(player5.numberOfVictores));
        game.addPlayer(player6.username, Integer.toString(player6.numberOfVictores));
        assertEquals(4, game.getNumberOfPlayers());
        game.removePlayer(player);
        assertEquals(3, game.getNumberOfPlayers());
    }
    //Did the game initialize everyone's score to 0, checking conditions
    public void testStartGame() {
        player.score = 10;
        game.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        game.startGame();
        assertFalse(game.isAvailableToJoin());
    }

    public void testCheckValidWord() {
        bank.getWordBank().add(new Word("gamer"));
        assertTrue(game.checkValidWord("gamer", bank));
        assertFalse(game.checkValidWord("gamerr", bank));
    }

    public void testCrossOutWord() {
        bank.getWordBank().add(new Word("gamer"));
        bank.getWordBank().add(new Word("gamerr"));
        bank.getWordBank().add(new Word("pen"));
        game.crossOutWord("gamerr", bank);
        assertTrue(bank.getWordBank().get(0).getAvailability());
        assertFalse(bank.getWordBank().get(1).getAvailability());
        assertTrue(bank.getWordBank().get(2).getAvailability());
    }
    //Testing old check winner condition
    //Remember player object inside/outside game is NOT the same
    public void testCheckWinner() {
        Player player1 = new Player("gamer");
        Player player2 = new Player("gamerr");
        game.addPlayer(player1.username, Integer.toString(player1.numberOfVictores));
        game.addPlayer(player2.username, Integer.toString(player2.numberOfVictores));
        game.getPlayerList().get(0).setScore(10);
        game.getPlayerList().get(1).setScore(20);
        Player winner = game.checkWinner();
        assertNotNull(winner);
        assertEquals(winner, game.getPlayerList().get(1));
        assertEquals(20, winner.getScore());
    }
}
