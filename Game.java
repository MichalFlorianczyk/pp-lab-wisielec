import java.util.*;

public class Game {
    private String word;
    private char[] guessedWord;
    private Set<Character> guessedLetters;
    private int attemptsLeft;
    private int gamesLost; // zmienne odpowiadające za statystyki
    private int gamesWon;
    private int attemptsToWin; // liczba prób do wygrania

    public Game(String word, int attempts) {
        this.word = word;
        this.guessedWord = new char[word.length()];
        Arrays.fill(guessedWord, '_');
        this.guessedLetters = new HashSet<>();
        this.attemptsLeft = attempts;
        this.gamesLost = 0;
        this.gamesWon = 0;
        this.attemptsToWin = 0;
    }

    public boolean guessLetter(char letter) {
        guessedLetters.add(letter);
        boolean correctGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedWord[i] = letter;
                correctGuess = true;
            }
        }
        if (!correctGuess) {
            attemptsLeft--;
        }
        return correctGuess;
    }

    public boolean isGameWon() {
        return word.equals(new String(guessedWord));
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0;
    }

    public String getGuessedWord() {
        return new String(guessedWord);
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void incrementGamesLost() {
        gamesLost++;
    }

    public void incrementGamesWon() {
        gamesWon++;
    }

    public void setAttemptsToWin(int attemptsToWin) {
        this.attemptsToWin = attemptsToWin;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getAttemptsToWin() {
        return attemptsToWin;
    }

}