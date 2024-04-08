

// var connection = null;
// var serverUrl;
// serverUrl = "ws://" + window.location.hostname + ":9880";
// connection = new WebSocket(serverUrl);

let nick;   //Players unique nick

function nameFunction() {   //gets the username 
    nick = document.querySelector("#nick");
    console.log(nick.value);

    if (nick.value != "") {
        window.location.assign("wordSearchLobby.html");  //navigates user to next page
    }
    else {
        document.getElementById("errorMsg").innerHTML = "Error: Please enter a proper username.";
    }
}

function roomFunction(){ //Navigate to room page
    window.location.assign("wordSearchRoom.html");
}

function backToNameFunction(){ //Navigate to room page
    window.location.assign("wordSearchName.html");
}
function backToLobbyFunction(){ //Navigate to room page
    window.location.assign("wordSearchLobby.html");
}

window.onload = function(){
    for (let node of document.querySelectorAll("td")) {
        if(node.textContent != "") continue;
        let charCode = Math.round(65 + Math.random() * 25)
        node.textContent = String.fromCharCode(charCode);
    }
}
