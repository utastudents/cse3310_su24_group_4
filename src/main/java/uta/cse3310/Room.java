package uta.cse3310;

import java.util.ArrayList;

public class Room {
    private String roomName;
    private ArrayList<Player> players = new ArrayList<>();
    private Game game;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public Room(String name) {
        // Here implements the constructor for room
    }

    // Here methods to operate rooms
    public void addPlayer(Player player) {
    }

    public void removePlayer(Player player) {
    }

    public static void addRoom(String roomName) {   
    }

    public static void removeRoom(String roomName) {
    }

    public String getRoomName() {
        return roomName;
    }

    public Game getGame() {
        return game;
    }


}
