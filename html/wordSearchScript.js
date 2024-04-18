class Player {
    username;
    score;
    color;
    status;
    numberOfVictories;
}   //Players unique nick

class Lobby {
    playerList;
    player;
    playerName;

    gameList;
    game;
    GameID;

    nameButton;
    createRoomButton;
    joinRoomButton;
    backButton;
    exitGameButton;

    displayRooms;
    displayLeaderboard;

    rooms_size;
    leaderboard_size;

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



var connection = null;   //Connection
var serverUrl;           //url server
serverUrl = "ws://" + window.location.hostname + ":9101";
//Create the connection with the server
connection = new WebSocket(serverUrl);  //connection variable to send and recieve
console.log(connection);
//globalLobby.playerChat = new Array();

connection.onopen = function (evt) {    //open function
    console.log("open");
}
connection.onclose = function (evt) {   //close function
    console.log("close");
}

globalLobby = null;                    //A global variable to access events can be changed
connection.onmessage = function (evt) {
    var msg;
    msg = evt.data;
    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);             //passinf the server data into a variable
    globalLobby = obj;                       //It can be accessed directly or via a variable

    if(globalLobby.displayLeaderboard == true)  //Here's where we update stuff that is recieved from the back end
    {
        buildLeaderBoard();
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
        P = new Player();   //Creates a player variable to store the player name and player attributes
        P.username = Player.username.value; //Setting the player name that was captured when we click submit
        
        globalLobby.player = P;                  //These will change
        globalLobby.nameButton = "true";        //These will change
        globalLobby.createRoomButton = "false"; //These will change
        globalLobby.joinRoomButton = "false";   //These will change
        globalLobby.backButton = "false";       //These will change
        globalLobby.exitGameButton = "false";   //These will change

        connection.send(JSON.stringify(globalLobby));  //Sending the player back
        console.log(JSON.stringify(globalLobby))       //Showing what we sent into the console

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
    globalLobby.button = "backButton";
    connection.send(JSON.stringify(globalLobby));  
    console.log(JSON.stringify(globalLobby));
    hideShow();
}

function roomFunction() { //Navigate to room page  //Go to room from lobby
    display = 2;
    console.log(Player.username.value + " joined the room");
    globalLobby.nameButton = "false";
    globalLobby.createRoomButton = "false";
    globalLobby.joinRoomButton = "true";
    globalLobby.backButton = "false";
    globalLobby.exitGameButton = "false";
    connection.send(JSON.stringify(globalLobby));//Send player status that player is ready
    console.log(JSON.stringify(globalLobby));
    hideShow();
}

window.onload = function () {  //This function hides and shows the page when the website loads in
    hideShow();
}


function createRoom(evt) //This creates a room and gives an option to join room
{
    // document.getElementById("room1").textContent = `${Player.nick.value}'s Room`;
    console.log(Player.username.value + " created a room");

    globalLobby.nameButton = "false";
    globalLobby.createRoomButton = "true";
    globalLobby.joinRoomButton = "false";
    globalLobby.backButton = "false";
    globalLobby.exitGameButton = "false";
    
    connection.send(JSON.stringify(globalLobby));
    console.log(JSON.stringify(globalLobby));
    //Will be eventually moved to on message to update based on what JSON recieves
    buildRooms();   //Builds the rooms
    disableRoomButton();  //Disables the create room button. once the room is created
}

function disableRoomButton() {  //disable create room button
    createRoomButton.style.display = 'none';
}
 
function enableRoomButton() {  //enable create rooom button
    createRoomButton.style.display = 'block';
}

function buildRooms() { //This function builds various rooms in the lobby based on how many rooms are created in the lobby
    var table = document.getElementById("rmTable") //This variable grabs the id from html to specify where we wanna build the room
    for (var i = 0; i < 1; i++) {
        var row = `<tr>
                        <td>Room ${i}<td>
                        <button id ="rmButton" class ="smallbutton button 2" onclick="roomFunction()" >Join room</button>
                  <tr />`
        table.innerHTML += row; //This basically adds rooms and makes a list
    }
    buildLeaderBoard();  //This builds leaderboard 
    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}

function buildLeaderBoard() {  //This function builds leaderboard
    var leaderboard = document.getElementById("leaderboard");

    for (var i = 0; i < globalLobby.leaderboard_size; i++) 
    {
        var leaderBoardRow = `<tr>
                        <td>${globalLobby.playerList[i].username} <td>
                             <tr />`
        leaderboard.innerHTML += leaderBoardRow;
    }

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