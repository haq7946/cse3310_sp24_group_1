package uta.cse3310;

public class Player 
{
    private String username;
    private int score;
    private int color;
    private int status;
    private int numberOfVictores;

    public Player(String nick, int colorP)
    {
        setUsername(nick);
        setColor(colorP);
        setScore(0);
        setStatus(0);
        setVictories(0);
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


}
