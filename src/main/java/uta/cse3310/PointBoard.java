package uta.cse3310;

public class PointBoard 
{
    private Player[] scoreBoard;

    public PointBoard(Player[] playerlst)
    {
        setScoreBoard(playerlst);
    }

    public Player[] getScoreBoard()
    {
        // int totalPlayers = Player.getTotalNumOfPlayers();

        //     System.out.println("....Score Board.....");
        // for(int i = 0; i < totalPlayers; i++)
        // {
        //     System.out.println("\n");
        //     System.out.println(scoreBoard[i].getUsername() + " : " + scoreBoard[i].getVictories());
        // }

        return scoreBoard;
    }

    public void setScoreBoard(Player[] playerList)
    {
        int totalPlayers = Player.getTotalNumOfPlayers();
        for(int i = 0; i < totalPlayers; i++)
        {
            scoreBoard[i] = new Player();
            scoreBoard[i].setUsername(playerList[i].getUsername()); 
            scoreBoard[i].setScore(playerList[i].getScore());
        }
    }
    
}
