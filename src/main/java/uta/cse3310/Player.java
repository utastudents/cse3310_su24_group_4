package uta.cse3310;

import java.util.*;

public class Player{
    String playerName;
    int playerScore;
    int playerID;
    static ArrayList<Player> playerList = new ArrayList<Player>();
    PlayerType type;

    public Player(String playerName){
        // Here implements the Player constructor
    }



    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        // Here implements the method that set up playerName
    } 

    public static void removePlayer(int playerID) {
        // Here implements the method that removes the player
    }

    public PlayerType getType() {
        return type;
    }


    public void setType(PlayerType type) {
        // Here implements the method that sets up the player type
    }
}
