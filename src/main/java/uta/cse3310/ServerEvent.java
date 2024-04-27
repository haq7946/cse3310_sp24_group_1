package uta.cse3310;

public class ServerEvent 
{
    String button;  //what kind of button is being pressed
    // nameButton  - The player clicks the "submit button"
    // createRoomButton - The player clicks the "create room button"
    // joinRoomButton -   The player clicks the "join room button".
    // backButton - The player clicks the "back button"
    // leaveRoomButton -  The player clicks the "start game/ play again button in the room"
    // exitButton - The player clicks the "exit game button"

    String event; //what kind of event is going on
    // lobbyEvent - everything that happens in a lobby
    // gameEvent -  everything that happens in a game
    
    Player player;  //The player who is doing the clicking
    int occurrence; //Mainly for join button; each one is different!
    String iidd;
    String message;
    String victores;
    int x; int y; //Coordinates of the button pressed (on the board)
    
    ServerEvent(String button, String event, Player player, int occurrence, String iidd, String message, String victores)
    {
        button = this.button;
        event = this.event;
        player = this.player;
        occurrence = this.occurrence;
        iidd = this.iidd;
        message = this.message;
        victores = this.victores;
    };


}
