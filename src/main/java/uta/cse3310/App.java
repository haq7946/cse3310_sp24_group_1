// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class App extends WebSocketServer {
  // There will only be one lobby we just keep accesing it
  private int connectionId = 0;
  private Lobby myLobby;

  public App(int port) {
    super(new InetSocketAddress(port));
    myLobby = new Lobby();
  }

  public static void main(String[] args) {
    String HttpPort = System.getenv("HTTP_PORT");
    int port = 9001;
    if (HttpPort != null) {
      port = Integer.valueOf(HttpPort);
    }
    // Set up the http server
    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server
    port = 9101;
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort != null) {
      port = Integer.valueOf(WSPort);
    }

    App myApp = new App(port);
    myApp.setReuseAddr(true);
    myApp.start();
    System.out.println("websocket Server started on port: " + port);
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) 
  {
    connectionId++;
    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    ServerData SD = new ServerData();

    LobbyEvent L = new LobbyEvent();
    // Check if an existing lobby is active

    // allows the websocket to give us the Game when a message arrives
    conn.setAttachment(myLobby); // Set the attachment to the lobby

    Gson gson = new Gson();
    // Note only send to the single connection
    // Send the lobby event
    //conn.send(gson.toJson(L));
    //System.out.println(gson.toJson(L));
    System.out.println("This is a test");

    // The state of the game has changed, so lets send it to everyone
    String jsonString;
    jsonString = gson.toJson(SD);

    System.out.println(jsonString);
    broadcast(jsonString);
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");
    // Retrieve the lobby tied to the websocket connection
    Lobby L = new Lobby();
    // L.gameList = new ArrayList<Game>();
    // L.playerList = new ArrayList<Player>();
    L = conn.getAttachment();
    L = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    // System.out.println("< " + Duration.between(startTime,
    // Instant.now()).toMillis() + " " + "-" + " " + escape(message));

    // Bring in the data from the webpage
    // Lobby Event is passed from Json to gson [Web to code]
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    LobbyEvent L = gson.fromJson(message, LobbyEvent.class);
    System.out.println("Have I got here?");
    System.out.println();

    // Get our lobby object
    Lobby LO = conn.getAttachment();
    // Update the lobby as needed //Extract information as needed
    LO.updateLobby(L);
    //conn.send(gson.toJson(L));



    // Update Event with players and other information

    // send out the game state every time
    // to everyone
    String jsonString;
    jsonString = gson.toJson(L);

    // System.out.println("> " + Duration.between(startTime,
    // Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
    System.out.println(jsonString);
    broadcast(jsonString);
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    System.out.println("Server started!");
    setConnectionLostTimeout(0);
  }
}