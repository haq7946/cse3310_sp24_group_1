package uta.cse3310;

public class LobbyEvent extends Event
{
    String button; // must be nameButton, createRoomButton, joinRoomButton, backButton, or exitGameButton
    LobbyEvent()
    {
        
    }
    LobbyEvent(Player p, String button)
    {
        p = this.p;
        button = this.button;
    }
}
