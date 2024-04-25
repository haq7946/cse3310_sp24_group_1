class Player {
    username;
    score;
    color;
    status;
    numberOfVictories;
    gameId;
}   //Players unique nick

class ServerData {
    playerList;
    player;
    playerName;
    disableResponse;

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

class GameEvent {
    GameId;
    x;
    y;
    button;
}

class ServerEvent {
    button;
    event;
    player;
    message;
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
P.gameId = "none";  //This is our specific client's gameID
connection.onmessage = function (evt) {
    var msg;
    msg = evt.data;
//    console.log("Message received: " + msg);
    updateRooms(evt);
    updateLeaderBoard(evt);
    updateLobbyChat(evt);
    const obj = JSON.parse(msg);             //passing the server data into a variable //In this case it is lobby.java

    if (obj.serverResponse === "gameIdResponse") {
        //We display the game info that is passed from JSON
        for (var i = 0; i < obj.playerList.length; i++) {
            console.log(P.username);
            console.log(P.gameId);
            if (obj.playerList[i].username === P.username) {
                P.gameId = obj.playerList[i].iD;
                console.log(P.gameId);
            }

        }

        for (var i = 0; i < obj.gameList.length; i++) {
            if (P.username === obj.gameMakers[i].username) {
                startButton.style.display = 'block';
            }
            if (P.gameId === obj.gameList[i].gameID) {
                console.log(obj.gameList[i].gameResponse);
                if (obj.gameList[i].gameResponse === "start") {
                    fillBoard(obj.gameList[i].board);
                    S = new ServerEvent();   //Creating a server event
                    S.button = "boardResponse";  //what was pressed
                    S.event = "gameEvent";         //what kind of event
                    S.player = P;
                    //connection.send(Json.stringify(S));
                }

            }
            else if(P.gameId === "nothing")
            {
                emptyBoard();
            }
        }
    }

}

////////////////////////////////////////////////////
var display = 0;   //This variable controls the pages //0 - namepage   1- lobby  2 - room
var namePage = document.getElementById("namePage"); //Main page
var lobbyPage = document.getElementById("lobbyPage"); //Lobby Page
var roomPage = document.getElementById("roomPage"); //Game Page
////////////////////////////////////////////////////
document.getElementById("rmButton").style.display = 'none'; ///Room button
var startButton = document.getElementById("startGameButton"); //Start game button
startButton.style.display = 'none';
//////////////////////////////////////////////////
var createRoomButton = document.getElementById("createRoom");  //Create Room
function hideShow(evt)   //This function hides and shows pages
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

function sendChat()
{
    let chat = document.querySelector("#roomChatBox");
    console.log(chat.value);

    S = new ServerEvent();
    S.button = "sendChat";
    S.event = "chatEvent";
    S.player = P;
    S.iidd = P.gameId;
    S.message = chat.value;
    connection.send(JSON.stringify(S));
    console.log(JSON.stringify(S));
}

function backToNameFunction() { //Navigate to name page
    console.log(Player.username.value + " left the game");  //Just prints to console that the player left
    S = new ServerEvent();
    S.event = "lobbyEvent";
    S.button = "backButton";
    S.player = P;
    connection.send(JSON.stringify(S));  //Send 
    console.log("Message sent: " + JSON.stringify(S));
    display = 0;   //Change the global variable
    hideShow();    //Change the page
}

function backToLobbyFunction() { //Kicks players out of the game
    display = 1;
    console.log(Player.username.value + " left the room");
    S = new ServerEvent();
    S.button = "backToLobbyButton";
    S.event = "lobbyEvent";
    S.player = P;
    S.iidd = P.gameId;
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
    disableRoomButton();  //Disables the create room button. once the room is created
}

function startGameFunction() {
    console.log(Player.username.value + " wants to start a game");
    S = new ServerEvent();   //Creating a server event
    S.button = "startGame";  //what was pressed
    S.event = "gameEvent";         //what kind of event
    console.log(P.gameId);
    S.player = P;              //who did it
    S.iidd = P.gameId;
    resetBGColor();
    connection.send(JSON.stringify(S));  //Send 
    console.log("Message sent: " + JSON.stringify(S));

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
    for (var i = 0; i < 1; i++)  //NOTE: This is here for now, without it we would't even have a game screen
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
    while (table.rows.length != 0) //Empty out the table before updating
    {
        table.deleteRow(0);
    }
    for (var i = 0; i < obj.gameList.length; i++)  //Iterate through gamelist and gamemaker to create
    {
        var row = `<tr>
                            <td>${obj.gameMakers[i].username}'s Room<td>
                            <button id ="rmButton" class ="smallbutton button 2" onclick=roomFunction(${i + 1}) >Join room</button>
                      <tr />`
        table.innerHTML += row;
    }

    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}


function updateLeaderBoard(evt) {  //This function builds leaderboard
    var leaderboard = document.getElementById("leaderboard");
    var msg = evt.data;
    const obj = JSON.parse(evt.data);
    console.log("The number of people in this lobby is " + obj.playerList.length); //Debugging
    while (leaderboard.rows.length != 0) //Empty out the table before updating
    {
        leaderboard.deleteRow(0);
    }
    for (var i = 0; i < obj.playerList.length; i++)  //Iterate through playerlist to create
    {
        var row = `<tr>
                        <td>${obj.playerList[i].username}  ${obj.playerList[i].score}<td>
                  <tr />`
        leaderboard.innerHTML += row;
    }

}

function sendLobbyChat()
{
    let chat = document.querySelector("#chatButtonLobby");
    console.log(chat.value);

    S = new ServerEvent();
    S.button = "sendChatLobby";
    S.event = "chatEvent";
    S.player = P;
    S.message = chat.value;
    connection.send(JSON.stringify(S));
    console.log(JSON.stringify(S));
}

function updateLobbyChat(evt)
{
    var chat = document.getElementById("chatting");
    var msg = evt.data;
    const obj = JSON.parse(evt.data);
    console.log("The number of chat in this lobby is: " + obj.playerChat.length); //Debugging
    // while (chat.rows.length != 0) //Empty out the table before updating
    // {
    //     chat.deleteRow(0);
    // }
    for (var i = 0; i < obj.playerChat.length; i++)  //Iterate through playerlist to create
    {
        var row = `<tr>
                        <td>${obj.playerChat[i]}<td>
                  <tr />`
        chat.innerHTML += row;
    }

}

function incrementPlayerScore(playerId){
    if (Players[playerId]) {
        Players[playerId].score += 1; // Increment the player's score by 1
        console.log(Players[playerId].username + " scored a point. Total score: " + Players[playerId].score);
    
        let S = new ServerEvent();
        S.event = "scoreUpdate";
        S.player = P;
        S.newScore = P.score;

        connection.send(JSON.stringify(S));
        console.log("Score update sent: " + JSON.stringify(S));
    }
    else{
        console.log("Player ID not found.");
    }
}

//////////////////////////////////////////////////////////////////////////////////////////
////////////////////ROOOM//////////////////////////////////////////////////////////////
//This code is used to generate the board in a room
//This is all just present for now
//It will be changed to display the board once we send game information
const WIDTH = 35;
const HEIGHT = 35;
const Buttons = new Array(WIDTH * HEIGHT);
const PlayerToColor = new Map([[0,"royalblue"],[1,"black"],[2,"brown"],[3,"green"],[4,"blue"],[5,"orange"]]);
let selected_letters = "";
var placeHolder;
var startCoordinate = -1;
var endCoordinate = -1;
var idx = P.gameId;
var word = "";
let counter = 0;
if(P.gameId === "none") idx = 2;
for(let i = 0; i < WIDTH; i++)
{
    for(let j = 0; j < HEIGHT; j++)
    {
        let button = document.createElement("button");
        button.style.width = 5;
        button.setAttribute("id", counter);
//        console.log(counter);
        button.setAttribute("onclick", "change_color(" + counter + ");");
        button.innerHTML = "?";
        if (counter % WIDTH == 0) 
        {
            linebreak = document.createElement("br");
            demo.appendChild(linebreak);
        }
        demo.appendChild(button);
        counter = counter + 1;
    }
}

function fillBoard(board)
{
    let something = 0;
    let arr = new Array(WIDTH);
    for(let index = 0; index < WIDTH; index++)
    {
        arr = board.boardArray[index];
        for(let jindex = 0; jindex < HEIGHT; jindex++)
        {
            let charCode = arr[jindex];
            var buttonid = document.getElementById(something);

            buttonid.innerHTML = charCode; 
            something = something + 1;
        }
    }
}

function emptyBoard()
{
    for (let i = 0; i < (WIDTH * HEIGHT); i++)
    {
        var buttonid = document.getElementById(i);
        buttonid.innerHTML = "?";
    }
}
function resetBGColor() {
    for (let i = 0; i < (WIDTH * HEIGHT); i++)
    {
        var buttonid = document.getElementById(i);
        buttonid.style.backgroundColor = "red";
    }

}

// function board(board) {
//     let something = 0;
//     /////////////////////2500
//     let arr = new Array(WIDTH);
//     for (let index = 0; index < 50; index++) {
//         //let charCode = Math.round(65 + Math.random() * 25);  //This is generating random letters //We will just plop words from the server data
//         arr = board.boardArray[index];
//         for (let jindex = 0; jindex < 50; jindex++) {
//             let charCode = arr[jindex];  //Read the JSON STRING to initialize //Make a loop and read board
//             console.log(arr[jindex]);
//             Buttons[something] = charCode;//String.fromCharCode(charCode);      //This is setting the buttons to those random letters
//             let button = document.createElement("button");     //This grabs the html element where we want to add the 50 by 50 grid
//             placeHolder = button;
//             button.style.width = 5;
//             button.setAttribute("id", something);
//             button.setAttribute("onclick", "change_color(" + something + ");");
//             button.innerHTML = Buttons[something];
//             if (something % 50 == 0) {
//                 linebreak = document.createElement("br");
//                 demo.appendChild(linebreak);
//             }
//             demo.appendChild(button);
//             something = something + 1;
//         }
//     }

//     function buildBoard(evt) {

//     }

// }


function destroyBoard()
{
    let area = document.getElementById("demo");
    area.remove();
}

function change_color(id) {
    let x = id % WIDTH;
    let y = Math.floor(id / HEIGHT);
    const letter = document.getElementById(id).innerHTML;
    selected_letters += letter
    document.getElementById("w3review").value = "selected " + letter + " at coordinate (" + y + "," + x + 
    ")\n";

    let bcolor = document.getElementById(id).style.backgroundColor;
    if (bcolor == PlayerToColor.get(idx))
        document.getElementById(id).style.backgroundColor = "red";
    else
        document.getElementById(id).style.backgroundColor = PlayerToColor.get(idx);
    if(startCoordinate == -1) 
      startCoordinate = id;
    else if(endCoordinate == -1)
      endCoordinate = id;
    if(startCoordinate != -1 && endCoordinate != -1) {
      let result =highlightWord(startCoordinate,endCoordinate,idx);
      if(result==-1) {
        document.getElementById(startCoordinate).style.backgroundColor = "red"; 
        document.getElementById(endCoordinate).style.backgroundColor = "red";
      }
      else {
        document.getElementById("w3review").value += "selected word=" + word;
        word = "";
      }
      startCoordinate = endCoordinate = -1;
    }
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


    function getDirection(v1, v2) {
       let x = v1 % WIDTH;
       let y = Math.floor(v1 / WIDTH);
       let x2 = v2 % HEIGHT;
       let y2 = Math.floor(v2 / HEIGHT);
       if((v1<0) || (v2<0)) return -2;
       if(y==y2)
       {
         if(x2-x>0)
           return 1;
         else
           return 2;
       }
       else if (x==x2)
       {
         if(y-y2>0)
           return 3;
         else
           return 4;
       } 
       else if(Math.abs(x2-x) == Math.abs(y2-y))
       {
         if((x2-x)>0 && (y-y2)>0)
           return 5;
         else if((x2-x)<0 && (y-y2)>0)
           return 6;
         else if((x2-x)>0 && (y-y2)<0)
           return 7;
         else if((x2-x)<0 && (y-y2)<0)
           return 8;
       }
       else
         return -1;
    }

function highlightWord(startCoordinate,endCoordinate,idx) {
       direction = getDirection(startCoordinate,endCoordinate);
       if(direction==-1) return -1;
       if(direction==1) {
         for(let i=startCoordinate;i<=endCoordinate;i++) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
         }
       }    
       else if(direction==2) {      
         for(let i=startCoordinate;i>=endCoordinate;i--) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==3) {    
         for(let i=startCoordinate;i>=endCoordinate;i -=HEIGHT) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==4) {     
         for(let i=startCoordinate;i<=endCoordinate;i +=HEIGHT) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==5) {     
         for(let i=startCoordinate;i>=endCoordinate;i -=HEIGHT-1) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==6) {     
         for(let i=startCoordinate;i>=endCoordinate;i -=HEIGHT+1) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==7) {     
         for(let i=startCoordinate;i<=endCoordinate;i +=HEIGHT+1) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
       else if(direction==8) {     
         for(let i=startCoordinate;i<=endCoordinate;i +=HEIGHT-1) {
           document.getElementById(i).style.backgroundColor = PlayerToColor.get(idx);
           word += document.getElementById(i).innerHTML;
           }
       }
//       StrikethroughWord(word);
//       word="";
    }

