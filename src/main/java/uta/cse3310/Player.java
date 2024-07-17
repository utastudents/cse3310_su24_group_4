package uta.cse3310;

import java.util.*;

public class Player{
    String playerName;
    int playerScore;
    int playerID;
    static ArrayList<Player> playerList = new ArrayList<Player>();
    PlayerType type;

    public Player(String playerName){
        // Check if username already exists
        if(verifyUsername(playerName)){
            this.playerName = playerName;
            this.playerID = playerList.size() + 1; 
            playerList.add(this);
        } else {
            this.playerName = null; // Indicates username was not unique
        }
    }


    // Check if there exists the same playername
    public static boolean verifyUsername(String playerName){
        for (Player player: playerList) {
            if(playerName.equals(player.getPlayerName())){
                return false;
            }
        }
        return true;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        // Here implements the method that set up playerName
    } 

    public static void removePlayer(int playerID) {
        // Remove if the playerId matches
        playerList.removeIf(player -> player.playerID == playerID);
    }

    public PlayerType getType() {
        return type;
    }


    public void setType(PlayerType type) {
        // Here implements the method that sets up the player type
    }
}
