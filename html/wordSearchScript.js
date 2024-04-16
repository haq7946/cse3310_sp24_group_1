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
 
const BoardStatetoDisplay = new Map(); //Eventually will be used for the 50 by 50 grid
 
connection.onmessage = function (evt){
    var msg;
    msg = evt.data;
        console.log("Message received: " + msg);
        const obj = JSON.parse(msg);
    

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
 
function nameFunction() {   //gets the username
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
 
function backToNameFunction() { //Navigate to room page
    console.log(Player.username.value + " left the game");
    Player.username = 'none';
    if(display == 2)
    { // Exit the game/reload the website
    location.reload();
    }
    display = 0;
    hideShow();
}
 
function backToLobbyFunction() { //Navigate to room page
    display = 1;
    console.log(Player.username.value + " left the room");
    hideShow();
}
 
function roomFunction() { //Navigate to room page
    display = 2;
    console.log(Player.username.value + " joined the room");
    P.status = "1"; //Player is ready to play
    connection.send(JSON.stringify(P));//Send player status that player is ready
    console.log(JSON.stringify(P));
    hideShow();
}
 
window.onload = function () {
    hideShow();
   
}
 
 
function createRoom() {
    // document.getElementById("room1").textContent = `${Player.nick.value}'s Room`;
    console.log(Player.username.value + " created a room");
    buildRooms();
    disableRoomButton();
}
 
function disableRoomButton()
{
    createRoomButton.style.display = 'none';
}
 
function enableRoomButton()
{
    createRoomButton.style.display = 'block';
}
 
function buildRooms(){ //Add player data (will eventually print data based on JSON data)
    var table = document.getElementById("rmTable")
 
    for(var i = 0; i < 1; i++)
    {
        var row = `<tr>
                        <td>${Player.username.value}'s Room<td>
                        <button id ="rmButton" class ="smallbutton button 2" onclick="roomFunction()" >Join room</button>
                  <tr />`
        table.innerHTML += row;
    }
 
    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}
 
function buildLeaderBoard(){
    var leaderboard = document.getElementById("leaderboard");
 
    for(var i = 0; i < 1; i++)
    {
        var leaderBoardRow = `<tr>
                        <td>${Player.username.value}     ${Player.score}<td>
                             <tr />`
        leaderboard.innerHTML += leaderBoardRow;
    }
}
 
 
 
//////////////////////////////////////////////////////////////////////////////////////////
////////////////////ROOOM//////////////////////////////////////////////////////////////
 
const WIDTH = 50;
const HEIGHT = 50;
const Buttons = new Array(WIDTH*HEIGHT);
let selected_letters = "";  
    for(let index=0;index<Buttons.length;index++) {
        let charCode = Math.round(65 + Math.random() * 25);
        Buttons[index]=String.fromCharCode(charCode);
        const button = document.createElement("button");
        button.setAttribute("id",index);
        button.setAttribute("onclick","change_color("+index+");");
        button.innerHTML = Buttons[index];
        if(index % 50 == 0) {
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
       document.getElementById("w3review").value="selected "+letter+" at coordinate ("+x+","+y+")\nselected letters="+selected_letters;
       let bcolor = document.getElementById(id).style.backgroundColor;
       if(bcolor == "orange")
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
 
    function updateButtons(_Buttons,sButtons) {
       for(let i=0;i<_Buttons.length;i++)
           _Buttons[i] = sButtons[i];
    }