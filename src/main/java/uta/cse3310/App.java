
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.time.Duration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
  private Instant startTime;

  // Here applies the latency
  private List<Long> latencies = new ArrayList<>();

  public App(int port) {
    super(new InetSocketAddress(port));
    lobby = new Lobby();
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

    stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

    JsonObject openMessage = new JsonObject();
    connectionId++;
    openMessage.addProperty("type", "connectionID");
    openMessage.addProperty("connectionID", connectionId);
    conn.send(openMessage.toString());
  }

  @Override
  // REQUIREMENT: The system should have onClose() 
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    // Here implements the method when the client closes the server
    
    // Identifies the user who closes
    Player player = connectionPlayerMap.get(conn);
    if (player != null) {
      // Remove player from the global list, lobby, and the map
      
      // Remove the conn from the record
      connectionPlayerMap.remove(conn);

      // Remove this player from the player list as well
      Player.removePlayer(player.playerID);
      // Remove from the lobby's player list
      lobby.removePlayer(player.getPlayerName());

      System.out.println("Player " + player.getPlayerName() + " is out.");

      // Update all clients with the new player list
      ArrayList<String> playerNames = lobby.getPlayerNames();

      // Send this to the front and it'll update it
      Gson gson = new Gson();
      JsonObject updatePlayers = new JsonObject();
      updatePlayers.addProperty("type", "fetchPlayerList");
      // send the data with the updated player list
      updatePlayers.add("players", gson.toJsonTree(playerNames));
      // send this globally
      broadcast(updatePlayers.toString());
    }
  }

  @Override
  // REQUIREMENT: the system should have onMessage() 
  public void onMessage(WebSocket conn, String message) {

     // Here implements the method when the client takes actions
     long currentTime = System.currentTimeMillis();

      // Add current time to the each message
     
     try{
      JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();


      //Adding New Player
    if (jsonMessage.has("name")) {
        String username = jsonMessage.get("name").getAsString();
        boolean isUnique = Player.verifyUsername(username);
        if (isUnique) {
          Player newPlayer = new Player(username); 
          // create map that records conn and Player
          connectionPlayerMap.put(conn, newPlayer);
          // Add new players in the lobby
          lobby.players.add(newPlayer);

          // send this data to front in the case of success
          JsonObject successMessage = new JsonObject();
          successMessage.addProperty("type", "UsernameSuccess");
          
          successMessage.addProperty("currentTime", currentTime);
          successMessage.addProperty("msg", "Username is successfully added.");
          
          // Send it to this client
          conn.send(successMessage.toString());
        } else {
          // send the error message if the username already exists
          JsonObject errorMessage = new JsonObject();
          errorMessage.addProperty("type", "UsernameError");
          errorMessage.addProperty("currentTime", currentTime);
          errorMessage.addProperty("msg", "Username is not unique.");
           // Send it to this client
          conn.send(errorMessage.toString());
        }
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchPlayersList")) {
          // Here get the list of player names from the lobby
          ArrayList<String> playerNames = lobby.getPlayerNames();

          Gson gson = new Gson();
          // Send the updated data of playernames to the front
          JsonObject playerListMessage = new JsonObject();
          playerListMessage.addProperty("type", "fetchPlayerList");
          playerListMessage.addProperty("currentTime", currentTime);
          // Add the list of playernames in the data that'll be sent
          playerListMessage.add("players", gson.toJsonTree(playerNames));
          // Globally send it
          broadcast(playerListMessage.toString());
      }


      //Adding New Room
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("addRoom")) {
        // Get the username who wants to add a new game
        String playerName = jsonMessage.get("playerName").getAsString();
        // Adds a new room for a game with the username
        Room newRoom = Room.addRoom(playerName);
        // Attach the data
        conn.setAttachment(newRoom.getGame());
        // Get the sending data
        ArrayList<JsonObject> roomsInfoList = Room.fetchRoomsInfo();
        Gson gson = new Gson();

        // Send this 
        JsonObject roomsData = new JsonObject();
        roomsData.addProperty("type", "roomList");
        roomsData.addProperty("currentTime", currentTime);
        // Attach all the list of rooms and each room information
        roomsData.add("rooms", gson.toJsonTree(roomsInfoList));
        // Send it globally
        broadcast(roomsData.toString());
    }

    // Updating the room info when user joined
    else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchRooms")) {
      // get the list of rooms
      ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
      Gson gson = new Gson();
      JsonObject roomsMessage = new JsonObject();
      // Send it to the front
      roomsMessage.addProperty("type", "roomList");
      roomsMessage.addProperty("currentTime", currentTime);
      roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
      broadcast(roomsMessage.toString());
   }

   // When the user clicked join button
    else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("joinRoom")) {
        String playerName = jsonMessage.get("playerName").getAsString();
        String roomName = jsonMessage.get("roomName").getAsString();
        System.out.println("Player " + playerName + " is trying to join room " + roomName);
        Room room = Room.getRoomByName(roomName);
        Player player = connectionPlayerMap.get(conn);
        if (player == null) {
            player = new Player(playerName);
            connectionPlayerMap.put(conn, player);
        }
        room.addPlayer(player);
  }

  else if(jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchRoomPlayerList")) {
    String roomName = jsonMessage.get("roomName").getAsString();
    Room room = Room.getRoomByName(roomName);
    ArrayList<String> roomPlayerNames = room.getPlayersInRoom();
    Gson gson = new Gson();
      // Send the updated data of playernames to the front
    JsonObject playerRoomMsg = new JsonObject();
    playerRoomMsg.addProperty("type", "fetchRoomPlayerList");
    playerRoomMsg.addProperty("currentTime", currentTime);
      // Add the list of playernames in the data that'll be sent
    playerRoomMsg.add("roomplayers", gson.toJsonTree(roomPlayerNames));
      // Globally send it
    broadcast(playerRoomMsg.toString());
  }

  else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("leaveRoom")) {
    if (Room.getRoomByPlayer(connectionPlayerMap.get(conn)) != null) {
        Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
        room.removePlayer(connectionPlayerMap.get(conn));
    }
  }


  // When users remove the room 
  else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("removeRoom")) {
    String playerName = jsonMessage.get("playerName").getAsString();
    // Removes the room by searching the name of the room which is the player who created
    Room.removeRoom(playerName);
    // Send this message to the front
    ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
    Gson gson = new Gson();
    JsonObject roomsMessage = new JsonObject();
    roomsMessage.addProperty("type", "roomList");
    roomsMessage.addProperty("currentTime", currentTime);
    roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
    broadcast(roomsMessage.toString());
  }


     }catch (Exception e) {
      e.printStackTrace();
    }
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
    System.out.println("Server started");
    setConnectionLostTimeout(0);
    stats = new Statistics();  // Stats starts
    startTime = Instant.now();
  }


  public static void main(String[] args) {

    String HttpPort = System.getenv("HTTP_PORT");
    // REQUIREMENT: the port used for the http server shall be optionally provided by an evironment variable
    int port = 9004;
    if (HttpPort!=null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server

    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server

    // RQUIREMENT: the port used for Websocket server shall be the http port + 100 
    port = 9104;
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
