class Player {
    username;
    score;
    color;
    status;
    numberOfVictories;
}   //Players unique nick

class ServerData {
    playerList;
    player;
    playerName;

    gameList;
    game;
    GameID;

    wordGridX;
    wordGridY;
}

class Game {
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

class GameEvent
{
    GameId;
    x;
    y;
    button;
}

class ServerEvent
{
    button;
    event;
    player;
}





var connection = null;   //Connection
var serverUrl;           //url server
serverUrl = "ws://" + window.location.hostname + ":9101";
//Create the connection with the server
connection = new WebSocket(serverUrl);  //connection variable to send and recieve
console.log(connection);

connection.onopen = function (evt) {    //open function
    console.log("open");
}
connection.onclose = function (evt) {   //close function
    console.log("close");
}

//Our specific client
P = new Player();
var gameId = -1;  //This is our specific client's gameID
connection.onmessage = function (evt) {
    var msg;
    msg = evt.data;
    console.log("Message received: " + msg);
    updateRooms(evt);
    const obj = JSON.parse(msg);             //passing the server data into a variable //In this case it is lobby.java
    if(gameId == obj.gameList.GameID)
    {
        //We display the game info that is passed from JSON
    }
}

////////////////////////////////////////////////////
var display = 0;   //This variable controls the pages //0 - namepage   1- lobby  2 - room
var namePage = document.getElementById("namePage"); //Main page
var lobbyPage = document.getElementById("lobbyPage"); //Lobby Page
var roomPage = document.getElementById("roomPage"); //Game Page
////////////////////////////////////////////////////
document.getElementById("rmButton").style.display = 'none'; ///Room button
//////////////////////////////////////////////////
var createRoomButton = document.getElementById("createRoom");  //Create Room

function hideShow()   //This function hides and shows pages
{
    if (display == 0) {  //0 to show name page and hide other pages
        lobbyPage.style.display = 'none';     //Hides the lobby page
        roomPage.style.display = 'none';      //Hides the room page
        namePage.style.display = 'block';     //shows the namepage
    }
    if (display == 1) { //1 to show lobby page and hide other pages
        namePage.style.display = 'none';      //hides the name page
        roomPage.style.display = 'none';      //hides the room page
        lobbyPage.style.display = 'block';    //shows the lobby page
    }
    if (display == 2) { //2 to show room page and hide other pages
        namePage.style.display = 'none';      //hides the name page  
        lobbyPage.style.display = 'none';     //hides the lobbypage
        roomPage.style.display = 'block';     //shows the roompage

    }
}

function nameFunction() //This is basically what happens when we press submit (This is submit button)
{   //gets the username
    Player.username = document.querySelector("#nick");  //Get the ID from html     //This is for debug
    console.log(Player.username.value);                 //Show the name that was captured  //This is for debug

    if (Player.username.value != "")  //If the user doesn't put anything 
    {
        //Send data to back-end port 9101
        S = new ServerEvent();
        P.username = Player.username.value; //Setting the player name that was captured when we click submit
        S.button = "nameButton";
        S.event = "lobbyEvent";
        S.player = P;

        connection.send(JSON.stringify(S));  //Sending the Event back back ServerEvent(nameButton, lobbyEvent, Player)
        console.log(JSON.stringify(S));       //Showing what we sent into the console

        display = 1;  //navigates user to next page    //This is a variable that lets me change the page specifically hide and show pages
        hideShow();   //Function that hides and shows pages
    }
    else   //Show an error that user didn't put the name
    {
        document.getElementById("errorMsg").innerHTML = "Error: Please enter a proper username.";
        console.log("User didn't specify name");
    }
}

function backToNameFunction() { //Navigate to name page
    console.log(Player.username.value + " left the game");  //Just prints to console that the player left
    Player.username = 'none';  //This just clear the name

    if (display == 2) { // Exit the game/reload the website
        location.reload(); //Reloads the website
    }
    display = 0;   //Change the global variable
    hideShow();    //Change the page
}

function backToLobbyFunction() { //Navigate back to room page  //go to lobby from room
    display = 1;    
    console.log(Player.username.value + " left the room");
    S = new ServerEvent();
    S.button = "backButton";
    S.event = "lobbyEvent";
    S.player = P;
    connection.send(JSON.stringify(S));  
    console.log(JSON.stringify(S));
    hideShow();
}

function roomFunction(number) { //Navigate to room page  //Go to room from lobby
    display = 2;
    console.log("join button that was pressed is " + number);
    console.log(Player.username.value + " joined the room");
    S = new ServerEvent();
    S.button = "joinRoomButton";  //What button was pressed
    S.event = "lobbyEvent";       //what kind of event it was
    S.player = P;            //who did it
    S.occurrence = number;
    connection.send(JSON.stringify(S));//Send player status that player is ready
    console.log(JSON.stringify(S));
    hideShow();
}

window.onload = function () {  //This function hides and shows the page when the website loads in
    hideShow();
}


function createRoom() //This creates a room and gives an option to join room
{
    // document.getElementById("room1").textContent = `${Player.nick.value}'s Room`;
    console.log(Player.username.value + " created a room");
    S = new ServerEvent();   //Creating a server event
    S.button = "createRoomButton";  //what was pressed
    S.event = "lobbyEvent";         //what kind of event
    S.player = P;              //who did it
    connection.send(JSON.stringify(S));  //Send 
    console.log("Message sent: " + JSON.stringify(S));      //Show what was sent into the console

    buildRooms();
  //  disableRoomButton();  //Disables the create room button. once the room is created
}

function disableRoomButton() {  //disable create room button
    createRoomButton.style.display = 'none';
}
 
function enableRoomButton() {  //enable create rooom button
    createRoomButton.style.display = 'block';
}

previous_size = null;
function buildRooms(evt) { //This function builds various rooms in the lobby based on how many rooms are created in the lobby
    var table = document.getElementById("rmTable") //This variable grabs the id from html to specify where we wanna build the room
    //Write code to build rooms
    //message = new Lobby();
    //message = msg;
    for(var i = 0; i < 1; i++)  //NOTE: This is here for now, without it we would't even have a game screen
    {
        var row = `<tr>
                        <td>${Player.username.value}'s Room<td>
                        <button id ="rmButton" class ="smallbutton button 2" onclick=roomFunction()" >Join room</button>
                  <tr />`
        table.innerHTML += row;
    }
}
//P.S. I know this is probably just logic but if it works it works -Bryan
    function updateRooms(evt) { //This function updates the rooms for all players
        var table = document.getElementById("rmTable") 
        var msg = evt.data;
        const obj = JSON.parse(evt.data);
        console.log("The number of roomss in this lobby is " + obj.gameList.length); //Debugging
        while(table.rows.length != 0) //Empty out the table before updating
        {
            table.deleteRow(0);
        }
        for(var i = 0; i < obj.gameList.length; i++)  //Iterate through gamelist and gamemaker to create
        {
            var row = `<tr>
                            <td>${obj.gameMakers[i].username}'s Room<td>
                            <button id ="rmButton" class ="smallbutton button 2" onclick=roomFunction(${i + 1}) >Join roomba</button>
                      <tr />`
            table.innerHTML += row;
        }
    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}


function buildLeaderBoard(evt) {  //This function builds leaderboard
    var leaderboard = document.getElementById("leaderboard");
    var size = Object.keys( evt.playerList ).length;
    //Write code to update leaderboard

}




//////////////////////////////////////////////////////////////////////////////////////////
////////////////////ROOOM//////////////////////////////////////////////////////////////
//This code is used to generate the board in a room
//This is all just present for now
//It will be changed to display the board once we send game information
const WIDTH = 50;
const HEIGHT = 50;
const Buttons = new Array(WIDTH * HEIGHT);
let selected_letters = "";
for (let index = 0; index < Buttons.length; index++) {
    let charCode = Math.round(65 + Math.random() * 25);  //This is generating random letters //We will just plop words from the server data
    Buttons[index] = String.fromCharCode(charCode);      //This is setting the buttons to those random letters
    const button = document.createElement("button");     //This grabs the html element where we want to add the 50 by 50 grid
    button.setAttribute("id", index);
    button.setAttribute("onclick", "change_color(" + index + ");");
    button.innerHTML = Buttons[index];
    if (index % 50 == 0) {
        linebreak = document.createElement("br");
        demo.appendChild(linebreak);
    }
    demo.appendChild(button);
}
function change_color(id) {
    let x = id % WIDTH;
    let y = Math.floor(id / HEIGHT);
    const letter = document.getElementById(id).innerHTML;
    selected_letters += letter
    document.getElementById("w3review").value = "selected " + letter + " at coordinate (" + x + "," + y + ")\nselected letters=" + selected_letters;
    let bcolor = document.getElementById(id).style.backgroundColor;
    if (bcolor == "orange")
        document.getElementById(id).style.backgroundColor = "blue";
    else
        document.getElementById(id).style.backgroundColor = "black";
}

function saveToFile() {
    /*
            const fs = require('node:fs');
            fs.writeFileSync("data.txt", JSON.stringify(Buttons));
     
    */
}

function updateButtons(_Buttons, sButtons) {
    for (let i = 0; i < _Buttons.length; i++)
        _Buttons[i] = sButtons[i];
}

function updateState() //Will be used later to update the state of the game with server data
{

}