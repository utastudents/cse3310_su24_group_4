package uta.cse3310;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LobbyTest extends TestCase {

    public LobbyTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LobbyTest.class);
    }

    public void testAddPlayer() {
        Lobby lobby = new Lobby();
        lobby.addPlayer("Alice");
        assertTrue(lobby.getPlayerNames().contains("Alice"));
    }

    public void testRemovePlayer() {
        Lobby lobby = new Lobby();
        lobby.addPlayer("Bob");
        lobby.removePlayer("Bob");
        assertFalse(lobby.getPlayerNames().contains("Bob"));
    }

    public void testGetPlayerNames() {
        Lobby lobby = new Lobby();
        lobby.addPlayer("Alice");
        lobby.addPlayer("Bob");
        ArrayList<String> playerNames = lobby.getPlayerNames();
        assertEquals(2, playerNames.size());
        assertTrue(playerNames.contains("Alice"));
        assertTrue(playerNames.contains("Bob"));
    }
}
