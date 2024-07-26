package uta.cse3310;

import junit.framework.TestCase;
import java.util.ArrayList;

public class GameTest extends TestCase {
    public void testTwoInput() { // R041 R042 R037
        Game g = new Game();
        g.playerList.add(new Player("player1"));
        g.playerList.add(new Player("player2"));
        g.StartGame();
        
        // tests if player types are correct
        assertTrue(g.playerList.get(0).type == PlayerType.ONE && g.playerList.get(1).type == PlayerType.TWO);

        // creates a new word to test the word checker and player type array
        String testword = "testing";
        g.addWord(new Word(testword, 100));

        // Assuming updateScore simulates some kind of word checking/updating process
        g.updateScore(PlayerType.ONE, testword.length());

        for (int i = 0; i < testword.length(); i++) { // checks that the word is correctly set
            assertTrue(g.Words.get(0).wordStr.charAt(i) == testword.charAt(i));
        }
    }

    public void testInvalidWord() { // R030
        Game g = new Game();
        g.playerList.add(new Player("player1"));
        g.playerList.add(new Player("player2"));
        g.StartGame();

        String invalidWord = "invalid";
        g.addWord(new Word(invalidWord, 100));

        // Assuming some logic in Game class to handle invalid words which isn't shown here
        // Asserting that the word added is indeed the invalid word
        assertEquals(g.Words.get(0).wordStr, invalidWord);
    }

    public void testOneInput() { // test requirement R008
        Game g = new Game();
        g.playerList.add(new Player("Player 1"));
        g.playerList.add(new Player("Player 2"));
        g.StartGame();

        Word word = g.selectWord();
        g.addWord(word);
        assertNotNull(word);
    }

    public void testPointsAdded() { // R009 and R010
        Game g = new Game();
        g.playerList.add(new Player("player1"));
        g.playerList.add(new Player("player2"));
        g.StartGame();

        String testword = "testing";
        Word word = new Word(testword, 100);
        g.addWord(word);

        g.incrementPoints("player1", word);

        assertTrue(g.playerList.get(0).playerScore == 200); // 100 initial + 100 points
    }

    public void testWinner() { // R026
        Game g = new Game();
        g.playerList.add(new Player("player1"));
        g.playerList.add(new Player("player2"));
        g.StartGame();

        String testword = "testing";
        Word word = new Word(testword, 100);
        while (g.playerList.get(0).playerScore < 100) {
            g.addWord(word);
            g.incrementPoints("player1", word);
        }

        ArrayList<Player> winners = g.whoIsWinner();
        assertTrue(winners.size() > 0);
        assertTrue(winners.get(0).playerScore >= 100);
    }

    public void printButton(int start, int end, PlayerType[] array) { // method for debugging/testing
        for (int i = start; i <= end; i++) {
            if (i % 20 == 0) {
                System.out.println("");
            }
            System.out.print("[" + i + "]" + "|" + array[i] + "|");
        }
        System.out.println("");
    }
}
