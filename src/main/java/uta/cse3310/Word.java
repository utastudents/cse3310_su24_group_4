package uta.cse3310;

public class Word {
    private String word;
    private boolean guessed;


    public Word(String word) {
        this.word = word;
        this.guessed = false;
    }

    public String getWord() {
        return word;
    }

    public String getLetter(int i) {
        char c = this.word.charAt(i);
        String s = String.valueOf(c);
        return s;
    }

    public void guesseWord(String guess){
        // Here implements the method to check if the player's guess matches with the word
    }
}
