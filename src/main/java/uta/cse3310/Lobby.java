package uta.cse3310;

import java.util.ArrayList;

public class Lobby {

    public ArrayList<Player> players;

    public Lobby(){
        players = new ArrayList<Player>();
    }

    public void addPlayer(String playerName){
        // Here implements the method that adds player into the lobby
        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
    }

    public void removePlayer(String playerName) {
        // Here implements the method that removes player
        players.removeIf(player -> playerName.equals(player.getPlayerName()));
        Room.removeRoom(playerName);
    }


    public ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getPlayerName());
        }
        return playerNames;
    }


    


}
