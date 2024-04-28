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
    //Did the game make it unable to join
    public void testStartGame() {
        game.addPlayer(player.username, Integer.toString(player.numberOfVictores));
        game.startGame();
        assertFalse(game.isAvailableToJoin());
    }
    //Is the word in the wordbank
    public void testCheckValidWord() {
        bank.getWordBank().add(new Word("gamer"));
        assertTrue(game.checkValidWord("gamer", bank));
        assertFalse(game.checkValidWord("gamerr", bank));
    }
    //Does it search and set the availability correctly
    public void testCrossOutWord() {
        bank.getWordBank().add(new Word("gamer"));
        bank.getWordBank().add(new Word("gamerr"));
        bank.getWordBank().add(new Word("pen"));
        game.crossOutWord("gamerr", bank);
        assertTrue(bank.getWordBank().get(0).getAvailability());
        assertFalse(bank.getWordBank().get(1).getAvailability());
        assertTrue(bank.getWordBank().get(2).getAvailability());
    }
    //Are we selecting the words from the board correctly
    public void testSelectWord(){
        game.startGame();
        game.board.boardArray[0][0] = 'a';
        game.board.boardArray[0][1] = 'b';
        game.board.boardArray[1][0] = 'c';
        game.board.boardArray[1][1] = 'd';
        //You selected a tile but its not really anything so make it null
        assertEquals("", game.selectWord(0, 0, 0, 0)); 
        //All the orientations
        assertEquals("ab", game.selectWord(0, 0, 1, 0));
        assertEquals("ac", game.selectWord(0, 0, 0, 1));
        assertEquals("ad", game.selectWord(0, 0, 1, 1));
        assertEquals("ca", game.selectWord(0, 1, 0, 0));
        assertEquals("cb", game.selectWord(0, 1, 1, 0));
    }
    //Are we recognizing coordinate points correctly
    public void testCheckOrientation(){
        assertEquals("vertical", game.checkOrientation(5, 3, 5, 1));
        assertEquals("horizontal", game.checkOrientation(1, 5, 3, 5));
        assertEquals("diagonal", game.checkOrientation(1, 1, 2, 2));
        assertEquals("diagonal", game.checkOrientation(2, 2, 1, 1));
        assertEquals("", game.checkOrientation(500, 0, 1, 1));
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
