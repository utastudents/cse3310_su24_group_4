package uta.cse3310;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlayerTest extends TestCase{
  
    public PlayerTest(String testname){
        super(testname);
    }
  
    public static Test suite() {
        return new TestSuite(PlayerTest.class);
    }

    public void testNameSetandGet() {
        String username = "username";
        Player player = new Player(username);
        
        assertEquals(username, player.getPlayerName());
        Player.playerList.clear();
    }

    public void testNameCheck() { // tests the unique username check
        String username = "username";
        Player player = new Player(username);
        ArrayList<Player> playerList = Player.playerList;

        String username2 = "username";
        Player player2 = new Player(username2);
        
        assertFalse(player2.getPlayerName() != null);
        Player.playerList.clear();
    }

    public void testRemovePlayer() {
        String username = "username";
        Player player = new Player(username);
        int playerID = player.playerID;
        Player.removePlayer(playerID);

        assertNull(Player.playerList.stream().filter(p -> p.playerID == playerID).findFirst().orElse(null));
        Player.playerList.clear();
    }

    public void testGetPlayerByName() {
        String username = "username";
        Player player = new Player(username);

        Player retrievedPlayer = player.getPlayerByName(username);
        
        assertEquals(player, retrievedPlayer);
        Player.playerList.clear();
    }
}
