

// var connection = null;
// var serverUrl;
// serverUrl = "ws://" + window.location.hostname + ":9880";
// connection = new WebSocket(serverUrl);

let nick;

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
