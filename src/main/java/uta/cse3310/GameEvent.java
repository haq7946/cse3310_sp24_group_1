package uta.cse3310;
// User events during a game are sent from the webpage to the server

public class GameEvent extends Event
{
    String GameId; // the game ID on the server
    int x, y; // the coordinates of the button they click
    String button; // must be nameButton, createRoomButton, joinRoomButton, backButton, or exitGameButton
    GameEvent() 
    {
        
    }

    GameEvent(Player p, String GameId, int x, int y, String button) 
    {
        p = this.p;
        GameId = this.GameId;
        x = this.x;
        y = this.y;
        button = this.button;
    }


}
