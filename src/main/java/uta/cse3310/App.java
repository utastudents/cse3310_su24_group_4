
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


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Vector;


public class App extends WebSocketServer {

  // All games currently underway on this server are stored in
  // the vector ActiveGames
  private Vector<Game> ActiveGames = new Vector<Game>();

  private int gameID;
  private int connectionId;
  public ArrayList<Integer> playerIDs;
  public ArrayList<Player> players = new ArrayList<Player>();
  private Lobby lobby;
  public static Map<WebSocket, Player> connectionPlayerMap = new HashMap<>();
  private Statistics stats;

  public App(int port) {
    super(new InetSocketAddress(port));
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  @Override
  // REQUIREMENT: the system should have onOpen()
  // When a client opens the web site call this method
  public void onOpen(WebSocket conn, ClientHandshake handshake) { 
    // Here implements the method when the client opens the server
  }

  @Override
  // REQUIREMENT: The system should have onClose() 
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    // Here implements the method when the client closes the server
  }

  @Override
  // REQUIREMENT: the system should have onMessage() 
  public void onMessage(WebSocket conn, String message) {
     // Here implements the method when the client takes actions
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    // prints out the events that happen on terminal at the same time
    System.out.println(conn + ": " + message);
  }

  @Override
  // REQUIREMENT: the system should have onError()
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // Here deals with errors
    }
  }

  @Override
  // REQUIREMENT: the system should have onStart()
  public void onStart() {
    // Here implements the method when the server starts
  }


  public static void main(String[] args) {

    String HttpPort = System.getenv("HTTP_PORT");
    // REQUIREMENT: the port used for the http server shall be optionally provided by an evironment variable
    int port = 9080;
    if (HttpPort!=null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server

    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server

    // RQUIREMENT: the port used for Websocket server shall be the http port + 100 
    port = 9180;
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort!=null) {
      port = Integer.valueOf(WSPort);
    }

    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);

  }
}
