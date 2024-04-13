package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent 
{
    String GameId; // the game ID on the server
    PlayerType PlayerIdx; // REDPLAYER, ORANGEPLAYER, GREENOPLAYER, or BLACKPLAYER
    int Button; // button number from 0 to 8

    UserEvent() 
    {

    }

    UserEvent(String _GameId, PlayerType _PlayerIdx, int _Button) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
    }
}
