package uta.cse3310;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.JsonObject;


// this class operates an instance of Games
public class Game {

    public int GameId;
    ArrayList<Player> playerList = new ArrayList<Player>();
    public PlayerType currentTurn;
    public String[] msg;
    public boolean gameStarted = false;

    ArrayList<Word> Words = new ArrayList<Word>();

    int round;
    int count;
   
    public Game(){
        // Set round and
        // count for the order of player 
        this.round = 1;
        this.count = 1;
    } 

    public Game(ArrayList<Player> playerList, int gameID){
        GameId = gameID;
        this.playerList = playerList;
        this.Words = new ArrayList<>();
    }

    public void restartGame(){
        this.round = 1;
        this.count = 1;
        this.Words.clear();
        for (Player player : playerList) { // Initialize player scores
            player.playerScore = 100;
            System.out.println("Player " + player.playerName + " Score : "+ player.playerScore);
        }
        playerIdentify();
        for (Player player : playerList) { // Initialize player scores
            System.out.println("Player " + player.playerName + " PlayerType : "+ player.type);
        }
    }

    public void StartGame() {
        // Here implements the method when the game starts
       if(playerList.size() >= 2 && !gameStarted){
        gameStarted = true;
        playerIdentify();
        System.out.println("Game has started");
       }
    }

        // Here get the individual word
    public Word selectWord(){

        Random random = new Random();

        ArrayList<String> words = getWordCollection("words.txt");

        if (words.isEmpty()) {
            System.err.println("No words found in the file or the file is empty.");
            return null; // Or handle this case appropriately
        }

        // Get the random word
        int randomIndex = random.nextInt(words.size());

        String selectedWord = words.get(randomIndex);


        // Get the random point for the word
        // From a range in 100 - 200
        int randomNumber = random.nextInt(101) + 100;

        Word word = new Word(selectedWord, randomNumber);

        return word;
    }




    // Here adds the new word in the array
    public void addWord(Word word){
        this.Words.add(word);
    }

    // Here replace the current word with new one
    public void replaceAnother(int index, Word anotherWord){
        Words.set(index, anotherWord);
    }

    public Word getWord(int index){
        Word word = Words.get(index);
        return word;
    }

    public int getRound(){
        return round;
    }

    public ArrayList<String> wordsSet(){
        ArrayList<String> wordStrings = new ArrayList<>();
        for(Word word : Words){
            wordStrings.add(word.wordStr);
        }
        return wordStrings;
    }

    public static ArrayList<String> getWordCollection(String filename) {
        ArrayList<String> wordCollection = new ArrayList<>();
        
        try (Scanner scan = new Scanner(new FileReader(filename))) {
            while (scan.hasNext()) {
                String word = scan.next().trim().toLowerCase();
                //System.out.println("Read word: " + word);  // Debug print
                
                // We pick words with length 6
                if (word.length() == 6) {
                    wordCollection.add(word);
                    //System.out.println("Added word: " + word);  // Debug print
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            // Optionally rethrow the exception or handle it as needed
            // throw new RuntimeException(e);
        }
        System.out.println("Total words collected: " + wordCollection.size());  // Debug print
        return wordCollection;
    }


    
    public void playerIdentify() {
        // Assigns unique player number
        int counter = 0;
        for(Player player: playerList){
            switch (counter) {
                case 0:
                    player.type = PlayerType.ONE;
                    counter++;
                    break;
               
                case 1:
                    player.type = PlayerType.TWO;
                    counter++;
                    break;
           
                case 2:
                    player.type = PlayerType.THREE;
                    counter++;
                    break;
                case 3:
                    player.type = PlayerType.FOUR;
                    counter++;
                    break;


                default:
                    break;
            }
        }
    }


    // Get the current player in the appropriate order
    public Player currentPlayer(int count){
        Player curretPlayer;
        int currentTurn = count % playerList.size();
        // Set the index inside of the scope
        int index = currentTurn-1;
        // If it's negative one means the edge of the list
        if(index == -1){
            index = playerList.size()-1;
        }
        curretPlayer = playerList.get(currentTurn);
        return curretPlayer;
    }

    // Here formulate Json message about the score
    public ArrayList<JsonObject> getPlayerScores() {
        ArrayList<JsonObject> scores = new ArrayList<>();
        for (Player player : playerList) {
            JsonObject scoreDetail = new JsonObject();
            scoreDetail.addProperty("playerName", player.getPlayerName());
            scoreDetail.addProperty("points", player.playerScore);
            //scoreDetail.addProperty("round", round);
            scores.add(scoreDetail);
        }
        return scores;
    }

    // Find out who is the winner in the final round
    public ArrayList<Player> whoIsWinner(){

         ArrayList<Player> highestScorers = new ArrayList<>();
        int max = 0;

        for(Player player : playerList){
            if(player.playerScore > max){
                max = player.playerScore;
            }
        }

        // Get player who get the highest score
        for (Player player : playerList) {
            if (player.playerScore == max) {
                highestScorers.add(player);
            }
        }
        // In case, there are more than one person who has the highest
        return highestScorers;
    }

    // UpdateScore
    public void updateScore(PlayerType playerType, int points) {
        for (Player player : playerList) {
            if (player.type == playerType) {
                player.playerScore += points;
                break;
            }
        }
    }

    // When the player got the right word
    public void incrementPoints(String PlayerName, Word word){
        for(Player player : playerList){
            if(player.getPlayerName().equals(PlayerName)){
                player.playerScore += word.getStake();
                System.out.println("Player " + PlayerName + " got $" + word.getStake());
            }
        }
    }


    // Set the price of a vowel as $30
    public int buyVowel(String PlayerName, Word word){
        for (Player player : playerList){
            if((player.getPlayerName().equals(PlayerName)) && (player.playerScore > 30)){
                
                //System.out.println("Player's money :" + player.playerScore);
                int v = word.getVowel();
                if(v == -1){
                    System.out.println("No vowel anymore");
                    // if index was -1 => No vowel anymore
                    return -1;
                }
                player.playerScore -= 30;
                return v;
            }
        }
        System.out.println("No enough money");
        // If index was -2 => No enough moeny
        return -2;
    }

    // Consonants are $20
    public int buyCons(String PlayerName, Word word){
        for (Player player : playerList){
            if((player.getPlayerName().equals(PlayerName)) && (player.playerScore > 30)){
                
                int c = word.getCons();
                //System.out.println("Index of cons: " + c);
                if(c == -1){
                    System.out.println("No consonants anymore");
                    // If index was -3 => No consonants anymore
                    return -3;
                }
                player.playerScore -= 30;
                return c;
            }
        }
        System.out.println("No enough money");
        // If index was -2 => No enough moeny
        return -2;
    }
}
