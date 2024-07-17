package uta.cse3310;

import java.util.ArrayList;

// this class operates an instance of Games
public class Game {

    public int GameId;
    ArrayList<Player> playerList = new ArrayList<Player>();
    public PlayerType currentTurn;
    public String[] msg;
    public boolean gameStarted = false;
   
   
    public Game(){} // Allows empty 

    public Game(ArrayList<Player> playerList, int gameID){
        GameId = gameID;
        this.playerList = playerList;
    }

    public void StartGame() {
       // Here implements the method when the game starts
    }

    public void Update(UserEvent U) {
        // Here implements the method when the game updates
    }

}
