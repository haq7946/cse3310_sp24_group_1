package uta.cse3310;

//THIS CLASS IS COMPLETE

public class Player 
{
    public String username;
    public int score;
    public int color;
    public int status;
    public int numberOfVictores;
    public String iD; //Game id
    public String gameId; //Idk why we have two id's in the code but putting here just in case

    //Storing player board clicks 
    public int x1; public int y1;
    public int x2; public int y2;

    //If this is true than we can proceed with game logic
    public boolean firstClick;

    //Track number of players
    public static int numOfPlayers;

    public Player(String nick, int colorP)
    {
        setUsername(nick);
        setColor(colorP);
        setScore(0);
        setStatus(0);
        setVictories(0);
        numOfPlayers++;
    }

    public Player(String nick)
    {
        setUsername(nick);
        setScore(0);
        setStatus(0);
        setVictories(0);
        firstClick = false;
        numOfPlayers++;
    }

    public Player()
    {
        setScore(0);
        setVictories(0);
        setStatus(0);
        numOfPlayers++;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String name)
    {
        username = name;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int scr)
    {
        score = scr;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int clr)
    {
        color = clr;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int stat)
    {
        status = stat;
    }

    public int getVictories()
    {
        return numberOfVictores;
    }

    public void setVictories(int victory)
    {
        numberOfVictores = victory;
    }

    public static int getTotalNumOfPlayers() //This will return total num of players in the whole game
    {
        return numOfPlayers;
    }
}
