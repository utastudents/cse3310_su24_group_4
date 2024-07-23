package uta.cse3310;

import java.util.ArrayList;
import java.util.Map;

import org.java_websocket.WebSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Room {
    private String roomName;
    private ArrayList<Player> players = new ArrayList<>();
    private Game game;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public Room(String name) {
        // Initialize room name 
        this.roomName = name;
        // Initialize player list
        this.players = new ArrayList<>();
        // Initialize a game
        this.game = new Game();
    }

    // Here methods to operate rooms
    public void addPlayer(Player player) {
        System.out.println("Adding player " + player.getPlayerName() + " to room " + this.roomName);
        this.players.add(player);
        player.playerScore = 100;
        this.game.playerList.add(player);
        PlayerType[] colors = {PlayerType.ONE, PlayerType.TWO, PlayerType.THREE, PlayerType.FOUR};
        player.setType(colors[players.size() - 1]);
        System.out.println("Player " + player.getPlayerName() + " added to room " + this.roomName + " with color " + player.getType());
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.game.playerList.remove(player);
        System.out.println("Player " + player.getPlayerName() + " removed from room " + this.roomName);
        // If no players left
        if (this.players.isEmpty()) {
            rooms.remove(this);
            System.out.println("Room " + this.roomName + " removed as it is now empty");
        }
    }

    public static Room addRoom(String roomName) {
        // Room name will be the player who created
        Room newRoom = new Room(roomName);
        // Adds in the list
        rooms.add(newRoom);
        System.out.println("Room " + newRoom.getRoomName() + " created");
        return newRoom;
    }

    // Create a sending data to the front 
    public static ArrayList<JsonObject> fetchRoomsInfo() {
        ArrayList<JsonObject> roomInfoList = new ArrayList<>();
        for (Room room : rooms) {
            JsonObject roomInfo = new JsonObject();
            roomInfo.addProperty("name", room.getRoomName());
            roomInfo.addProperty("playerCount", room.players.size());
            roomInfo.addProperty("isFull", room.players.size() >= 4);
            roomInfoList.add(roomInfo);
        }
        return roomInfoList;
    }

    // Get player names in the room
    
    public ArrayList<String> getPlayersInRoom() {
        ArrayList<String> roomPlayers = new ArrayList<>();
        for(Player player : players){
            roomPlayers.add(player.playerName);
        }
        return roomPlayers;
    }
   


    // Remove the room by the name of the player who created and the room size is zero
    public static void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getRoomName().equals(roomName)/* && room.players.size() == 0 */);
        // display it on terminal
        System.out.println("Room " + roomName + " removed");
    }


    public void setgame(Game game){
        this.game = game;
    }

    public String getRoomName() {
        return roomName;
    }


    // Here get the specific game in this room
    public Game getGame() {
        return game;
    }


    public static Room getRoomByPlayer(Player player) {
        for (Room room : rooms) {
            if (room.players.contains(player)) {
                System.out.println("Player " + player.getPlayerName() + " is in room " + room.getRoomName());
                return room;
            }
        }
        return null;
    }

    public static Room getRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                System.out.println("Room " + roomName + " found");
                return room;
            }
        }
        return null;
    }

    // Differentiate for each room to send the message 
    public void broadcastToRoom(String message) {
        // players in the room 
        for (Player player : players) {
            for (Map.Entry<WebSocket, Player> entry : App.connectionPlayerMap.entrySet()) {
                // send the message to each player in the room
                if (entry.getValue().equals(player)) {
                    WebSocket conn = entry.getKey();
                    if (conn != null) {
                        conn.send(message);
                    }
                }
            }
        }
    }

    // Here creates the message for updating scores in the specific room
    public void updateScoreToRoom(){
        
        ArrayList<JsonObject> scores = game.getPlayerScores();
        
        Gson gson = new Gson();
        JsonObject scoreMessage = new JsonObject();
        scoreMessage.addProperty("type", "updateScores");
        scoreMessage.add("scores", gson.toJsonTree(scores)); 
        
        broadcastToRoom(scoreMessage.toString());
    }

    public void updatePuzzleToRoom(String playerName, ActionType action, Word word){
        
        JsonObject puzzleMsg = new JsonObject();
        puzzleMsg.addProperty("type", "fetchPuzzle");

        switch (action) {
            case BUYVOWEL:
                int vowelIdx = game.buyVowel(playerName, word);
                puzzleMsg.addProperty("vowelIdx", vowelIdx);
                break;
            case BUYCONSONANT:
                int consIdx = game.buyCons(playerName, word);
                puzzleMsg.addProperty("consIdx", consIdx);
                break;
        
            default:
                break;
        }
        puzzleMsg.addProperty("word", word.wordStr);
        broadcastToRoom(puzzleMsg.toString());
    }




}
