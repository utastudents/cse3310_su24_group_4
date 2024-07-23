package uta.cse3310;

import java.util.HashSet;
import java.util.Set;

public class Word {
    public String wordStr;
    private int stake;

    Set<Integer> vowelRevealed;
    Set<Integer> consRevealed;

    //public static Map<Character, Integer> vowelMap = new HashMap<>();
    //public static Map<Character, Integer> consMap = new HashMap<>();

    public Word(String word, int stake) {
        this.wordStr = word;
        this.stake = stake;
        vowelRevealed = new HashSet<>();
        consRevealed = new HashSet<>();

    }

    public String getWord() {
        return wordStr;
    }

    public int getStake(){
        return stake;
    }

    public String getLetter(int i) {
        char c = this.wordStr.charAt(i);
        String s = String.valueOf(c);
        return s;
    }

    public void guesseWord(String guess){
        // Here implements the method to check if the player's guess matches with the word
    }

    public int getVowel(){
        //char c = this.word.charAt(0);
        
        String vowelsChar = "aeiouAEIOU";
        for (int i = 0; i < wordStr.length(); i++) {
            char currentChar = wordStr.charAt(i);
            if (vowelsChar.indexOf(currentChar) != -1 && !vowelRevealed.contains(i)) {
                // Add the vowel that was bought 
                vowelRevealed.add(i);
                return i;
            }
        }
        return -1;
    }

    public int getCons(){
        //char c = this.word.charAt(0);
        
        String vowelsChar = "aeiouAEIOU";
        for (int i = 0; i < wordStr.length(); i++) {
            char currentChar = wordStr.charAt(i);
            if (vowelsChar.indexOf(currentChar) == -1 && !consRevealed.contains(i)) {
                // Add the bought cons
                consRevealed.add(i);
                return i;
            }
        }
        return -1;
    }






}
