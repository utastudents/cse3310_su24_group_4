package uta.cse3310;

import junit.framework.Test; 
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RoomTest extends TestCase {

    public RoomTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RoomTest.class);
    }

    public void testRoomCreation() {
        String roomName = "TestRoom";
        Room room = new Room(roomName);
        assertEquals(roomName, room.getRoomName());
    }

    public void testAddAndRemovePlayer() {
        Room room = new Room("TestRoom");
        Player player = new Player("Player1");
        room.addPlayer(player);
        assertEquals(1, room.getGame().playerList.size());

        room.removePlayer(player);
        assertEquals(0, room.getGame().playerList.size());
    }

    public void testRoomAdditionAndRemoval() {
        String roomName = "NewRoom";
        Room.addRoom(roomName);
        assertNotNull(Room.getRoomByName(roomName));

        Room.removeRoom(roomName);
        assertNull(Room.getRoomByName(roomName));
    }
}
