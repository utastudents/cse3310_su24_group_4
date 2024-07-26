package uta.cse3310;

import junit.framework.TestCase;

public class WordTest extends TestCase {

    private Word word;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize Word object before each test
        word = new Word("HELLO", 10);
    }

    public void testConstructor() {
        assertNotNull("Word object should be initialized", word);
        assertEquals("Word should be 'HELLO'", "HELLO", word.getWord());
        assertEquals("Stake should be 10", 10, word.getStake());
    }

    public void testGetLetter() {
        assertEquals("Letter at index 0 should be 'H'", "H", word.getLetter(0));
        assertEquals("Letter at index 1 should be 'E'", "E", word.getLetter(1));
        assertEquals("Letter at index 4 should be 'O'", "O", word.getLetter(4));
    }

    public void testGetVowel() {
        assertEquals("First vowel should be at index 1", 1, word.getVowel());
        assertEquals("Second vowel should be at index 4", 4, word.getVowel());
        assertEquals("No more vowels should be left", -1, word.getVowel());
    }

    public void testGetCons() {
        assertEquals("First consonant should be at index 0", 0, word.getCons());
        assertEquals("Second consonant should be at index 2", 2, word.getCons());
        assertEquals("Third consonant should be at index 3", 3, word.getCons());
        assertEquals("No more consonants should be left", -1, word.getCons());
    }

    public void testGuesseWord() {
        // Assuming the guesseWord method is implemented, add appropriate test here
        // Example: word.guesseWord("HELLO");
        // assertTrue("Guess should be correct", word.isCorrectGuess()); // hypothetical method
    }
}
