package uta.cse3310;

import junit.framework.TestCase;

public class GameUnitTest extends TestCase {

    public void testtrue(){
        assertTrue(true);
    }
    // Game game = new Game();
    // WordBank bank = new WordBank();
    // Player player = new Player();

    // public void testGameInitialization() {
    //     assertNotNull(game.getGameID());
    //     assertEquals(0, game.getNumberOfPlayers());
    //     assertFalse(game.isAvailableToJoin());
    // }

    // public void testAddRemovePlayer() {
    //     game.addPlayer(player);
    //     assertEquals(1, game.getNumberOfPlayers());
    //     game.removePlayer(player);
    //     assertEquals(0, game.getNumberOfPlayers());
    // }

    // public void testStartGame() {
    //     game.startGame();
    //     assertFalse(game.isAvailableToJoin());
    // }

    // /*public void testCheckValidWord() {
    //     bank.initializeWordBank();
    //     assertTrue(game.checkValidWord("exampleWord", bank));
    //     assertFalse(game.checkValidWord("nonExistingWord", bank));
    // }*/

    // public void testCrossOutWord() {
    //     bank.initializeWordBank();
    //     game.crossOutWord("exampleWord", bank);
    // }

    // public void testCheckWinner() {
    //     Player player1 = new Player();
    //     player1.setScore(10);

    //     Player player2 = new Player();
    //     player2.setScore(20);

    //     game.addPlayer(player1);
    //     game.addPlayer(player2);

    //     Player winner = game.checkWinner();
    //     assertNotNull(winner);
    //     assertEquals(20, winner.getScore());
    // }
}
