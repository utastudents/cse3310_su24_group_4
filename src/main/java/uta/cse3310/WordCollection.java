package uta.cse3310;


import java.util.Vector;

public class WordCollection {


    private Vector<String> totalList = new Vector<>();
    private Vector<Word> WordList = new Vector<>();
    private Vector<Word> foundWords = new Vector<>();
    private int numwords;

    public WordCollection() {
        // Here Constructor
    }

    // Here is the method that gets 
    public String[] getWordString() {
        String[] result = new String[this.WordList.size()];
        int i = 0;
        for(Word w: this.WordList) {
           result[i] = w.getWord();
           i++;
        }
        return result;
     }
}
