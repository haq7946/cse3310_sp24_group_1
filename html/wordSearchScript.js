


// var connection = null;
// var serverUrl;
// serverUrl = "ws://" + window.location.hostname + ":9880";
// connection = new WebSocket(serverUrl);

class Player {
    nick;
    color;
}   //Players unique nick

var namePage = document.getElementById("namePage");
var lobbyPage = document.getElementById("lobbyPage");
var roomPage = document.getElementById("roomPage");
document.getElementById("rmButton").style.display = 'none'; ///Room button
var display = 0;

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
    Player.nick = document.querySelector("#nick");
    console.log(Player.nick.value);

    if (nick.value != "") {
        display = 1;  //navigates user to next page
        hideShow();
    }
    else {
        document.getElementById("errorMsg").innerHTML = "Error: Please enter a proper username.";
    }
}

function backToNameFunction() { //Navigate to room page
    display = 0;
    console.log(Player.nick.value + " left the game");
    Player.nick = 'none';
    hideShow();
}

function backToLobbyFunction() { //Navigate to room page
    display = 1;
    console.log(Player.nick.value + " left the room");
    hideShow();
}

function roomFunction() { //Navigate to room page
    display = 2;
    console.log(Player.nick.value + " joined the room");
    hideShow();
}

window.onload = function () {
    hideShow();

}


function createRoom() {
    // document.getElementById("room1").textContent = `${Player.nick.value}'s Room`;
    console.log(Player.nick.value + " created a room");
    buildRooms();
}

function buildRooms(){ //Add player data
    var table = document.getElementById("rmTable")

    for(var i = 0; i < 1; i++)
    {
        var row = `<tr>
                        <td>${Player.nick.value}'s Room<td>
                        <button id ="rmButton" class ="smallbutton button 2" onclick="roomFunction()" >Join room</button>
                  <tr />`
        table.innerHTML += row;
    }

    document.getElementById("rmButton").style.display = 'block';  //Display the room button
}



//////////////////////////////////////////////////////////////////////////////////////////
////////////////////ROOOM//////////////////////////////////////////////////////////////

const WIDTH = 50;
const HEIGHT = 50;
const Buttons = new Array(WIDTH*HEIGHT);
let selected_letters = "";
let idx =  Math.round(0 + Math.random() * 4); 
const PlayerToColor = new Map([[0,"red"],[1,"orange"],[2,"green"],[3,"black"]]);
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

       if(bcolor == PlayerToColor.get(idx))
          document.getElementById(id).style.backgroundColor = "blue";
       else
          document.getElementById(id).style.backgroundColor = PlayerToColor.get(idx);

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
