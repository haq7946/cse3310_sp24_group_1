class Player {
    username;
    score;
    color;
    status;
    numberOfVictories;
}   //Players unique nick

class Lobby {
    gameList
    playerList
    //playerChat
    button
}

class Game{
    gameID;
    playerList;
    playerChat;
    numberOfPlayers;
    board;
    bank;
    isAvailableToJoin;
    gameStatus; //wtf does this do


    wordList;
    completedWordList;
}


var connection = null;
var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9101";
//Create the connection with the server
connection = new WebSocket(serverUrl);
console.log(connection);

globalLobby = new Lobby();
globalLobby.playerList = new Array();
globalLobby.gameList = new Array();
//globalLobby.playerChat = new Array();

connection.onopen = function (evt) {
    console.log("open");
}
connection.onclose = function (evt) {
    console.log("close");
}

connection.onmessage = function (evt){
    var msg;
    msg = evt.data;
        console.log("Message received: " + msg);
        const obj = JSON.parse(msg);

    console.log(obj.playerList);
    console.log(obj.gameList);

}


////////////////////////////////////////////////////
var namePage = document.getElementById("namePage"); //Main page
var lobbyPage = document.getElementById("lobbyPage"); //Lobby Page
var roomPage = document.getElementById("roomPage"); //Game Page
////////////////////////////////////////////////////
document.getElementById("rmButton").style.display = 'none'; ///Room button
var display = 0;                                            //Debug purposes and control
//////////////////////////////////////////////////
var createRoomButton = document.getElementById("createRoom");  //Create Room

function hideShow() {
    if (display == 0) {
        lobbyPage.style.display = 'none';
        roomPage.style.display = 'none';
        namePage.style.display = 'block';
    }
    if (display == 1) {
        namePage.style.display = 'none';
        roomPage.style.display = 'none';
        lobbyPage.style.display = 'block';
    }
    if (display == 2) {
        namePage.style.display = 'none';
        lobbyPage.style.display = 'none';
        roomPage.style.display = 'block';

    }
}

window.onload = function () {
    hideShow();
    
}

function disableRoomButton()
{
    createRoomButton.style.display = 'none';
}

function enableRoomButton()
{
    createRoomButton.style.display = 'block';
}

function nameFunction()
{
    Player.username = document.querySelector("#nick");
    console.log(Player.username.value);

    if (Player.username.value != "") {
        //Send data to back-end port 9101
        P = new Player();
        P.username = Player.username.value;
        P.score = "0";
        P.color = "0";
        P.status = "0";  //0 = notready and 1 = ready //
        P.numberOfVictories = "0";

        globalLobby.playerList.push(P);
        globalLobby.button = "nameButton";
        connection.send(JSON.stringify(globalLobby));
        console.log(JSON.stringify(globalLobby))

        display = 1;  //navigates user to next page
        hideShow();
    }
    else 
    {
        document.getElementById("errorMsg").innerHTML = "Error: Please enter a proper username.";
        console.log("User didn't specify name");
    }
}

function createRoom() {
    // document.getElementById("room1").textContent = `${Player.nick.value}'s Room`;
    console.log(Player.username.value + " created a room");
    globalLobby.button = "createRoomButton";
    G = new Game();  //Create a new Game
    globalLobby.gameList.push(G);  //Push the game into the game list

    connection.send(JSON.stringify(globalLobby));  //Send the information 
    console.log(JSON.stringify(globalLobby.gameList));
    //Will be eventually moved to on message to update based on what JSON recieves
    //Need to build a room for this game
    buildRooms(globalLobby.gameList);
    disableRoomButton();
}

function buildRooms(evt){ //Add player data (will eventually print data based on JSON data)
    var table = document.getElementById("rmTable")
    //message = new Lobby();
    //message = msg;
    var list = evt;
    for(var i = 0; i < list.length; i++)
    {
        var row = `<tr>
                        <td>Game Room ${i}<td>
                        <button id ="rmButton" class ="smallbutton button 2" onclick="roomFunction(list.gameId)" >Join room</button>
                  <tr />`
        table.innerHTML += row;
    }

    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}

function roomFunction(evt) { //Navigate to room page
    display = 2;
    console.log(Player.username.value + " joined the room" + "GameId: " + evt);
    hideShow();
}