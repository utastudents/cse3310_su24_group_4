package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WordTest extends TestCase {

    public WordTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(WordTest.class);
    }

    public void testWordCreation() {
        String word = "example";
        int stake = 150;
        Word wordObj = new Word(word, stake);
        assertEquals(word, wordObj.getWord());
        assertEquals(stake, wordObj.getStake());
    }

    public void testGetLetter() {
        String word = "example";
        Word wordObj = new Word(word, 150);
        assertEquals("e", wordObj.getLetter(0));
        assertEquals("x", wordObj.getLetter(1));
        assertEquals("m", wordObj.getLetter(3));
    }

    public void testGetVowel() {
        String word = "example";
        Word wordObj = new Word(word, 150);
        
        // Checking that vowels are correctly identified and their indexes returned
        int index = wordObj.getVowel();
        assertTrue(index == 0 || index == 2 || index == 4);
        
        index = wordObj.getVowel();
        assertTrue(index == 0 || index == 2 || index == 4);

        index = wordObj.getVowel();
        assertTrue(index == 0 || index == 2 || index == 4);

        // No more vowels left to be revealed
        assertEquals(-1, wordObj.getVowel());
    }

    public void testGetCons() {
        String word = "example";
        Word wordObj = new Word(word, 150);

        // Checking that consonants are correctly identified and their indexes returned
        int index = wordObj.getCons();
        assertTrue(index == 1 || index == 3 || index == 5 || index == 6);

        index = wordObj.getCons();
        assertTrue(index == 1 || index == 3 || index == 5 || index == 6);

        index = wordObj.getCons();
        assertTrue(index == 1 || index == 3 || index == 5 || index == 6);

        index = wordObj.getCons();
        assertTrue(index == 1 || index == 3 || index == 5 || index == 6);

        // No more consonants left to be revealed
        assertEquals(-1, wordObj.getCons());
    }
}
