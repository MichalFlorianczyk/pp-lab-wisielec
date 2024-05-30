import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private WordRepository wordRepository;
    private Scanner scanner;
    private int totalGamesPlayed;
    private int totalGamesWon;
    private int totalGamesLost;
    private int totalAttemptsToWin;

    public UserInterface() {
        this.wordRepository = new WordRepository();
        this.scanner = new Scanner(System.in);
        this.totalGamesPlayed = 0;
        this.totalGamesWon = 0;
        this.totalGamesLost = 0;
        this.totalAttemptsToWin = 0;
    }

    public void start() {
        System.out.println("Witaj w grze w wisielca!");
        while (true) {
            System.out.println("1. Zagraj");
            System.out.println("2. Edytuj listę słów");
            System.out.println("3. Statystyki");
            System.out.println("4. Wyjście");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                playGame();
            } else if (choice == 2) {
                manageWords();
            } else if (choice == 3) {
                displayStatistics();
            } else if (choice == 4) {
                break;
            }
        }
    }

    private void playGame() {
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Trudny (6 prób)");
        System.out.println("2. Średni (12 prób)");
        System.out.println("3. Łatwy (24 próby)");
        int difficultyChoice = Integer.parseInt(scanner.nextLine());
        int attempts = getAttemptsForDifficulty(difficultyChoice);

        String word = wordRepository.getRandomWord();
        Game game = new Game(word, attempts);
        while (!game.isGameOver() && !game.isGameWon()) {
            System.out.println("Zgadnij literę: ");
            char letter = scanner.nextLine().charAt(0);
            game.guessLetter(letter);
            System.out.println("Słowo: " + game.getGuessedWord());
            System.out.println("Pozostałe próby: " + game.getAttemptsLeft());
        }
        if (game.isGameWon()) {
            System.out.println("Gratulacje! Wygrałeś!");
            totalGamesWon++;
            totalAttemptsToWin += attempts - game.getAttemptsLeft();
        } else {
            totalGamesLost++;
            System.out.println("Przegrałeś. Słowo to: " + word);
        }
        totalGamesPlayed++;
    }

    private int getAttemptsForDifficulty(int difficultyChoice) {
        if (difficultyChoice == 1) {
            return 6;
        } else if (difficultyChoice == 2) {
            return 12;
        } else if (difficultyChoice == 3) {
            return 24;
        } else {
            throw new IllegalArgumentException("Nieprawidłowy wybór poziomu trudności.");
        }
    }

    private void manageWords() {
        System.out.println("1. Dodaj słowo");
        System.out.println("2. Usuń słowo");
        System.out.println("3. Edytuj słowo");
        System.out.println("4. Wyświetl listę słów");
        System.out.println("5. Powrót");

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            addWord();
        } else if (choice == 2) {
            removeWord(null);
        } else if (choice == 3) {
            editWord();
        } else if (choice == 4) {
            displayWordList();
        } else if (choice == 5) {
            return;
        } else {
            System.out.println("Nieprawidłowy wybór.");
        }
    }

    private void addWord() {
        System.out.println("Podaj słowo do dodania: ");
        String word = scanner.nextLine();
        wordRepository.addWord(word);
        System.out.println("Słowo dodane.");
    }

    public boolean removeWord(String word) {
        List<String> words = wordRepository.getWordList();
        boolean removed = words.remove(word);
        if (removed) {
            wordRepository.saveWords();
        }
        return removed;
    }

    private void editWord() {
        System.out.println("Podaj słowo do edycji: ");
        String oldWord = scanner.nextLine();
        System.out.println("Podaj nowe słowo: ");
        String newWord = scanner.nextLine();
        if (wordRepository.editWord(oldWord, newWord)) {
            System.out.println("Słowo zedytowane.");
        } else {
            System.out.println("Słowo nie istnieje w bazie.");
        }
    }

    private void displayWordList() {
        List<String> wordList = wordRepository.getWordList();
        System.out.println("Lista słów:");
        for (String word : wordList) {
            System.out.println(word);
        }
    }

    private void displayStatistics() {
        System.out.println("Statystyki gier:");
        System.out.println("Liczba rozegranych gier: " + totalGamesPlayed);
        System.out.println("Liczba wygranych gier: " + totalGamesWon);
        System.out.println("Liczba przegranych gier: " + totalGamesLost);
        if (totalGamesWon > 0) {
            double averageAttemptsToWin = (double) totalAttemptsToWin / totalGamesWon;
            System.out.println("Średnia liczba prób potrzebna do wygranej gry: " + averageAttemptsToWin);
        } else {
            System.out.println("Średnia liczba prób potrzebna do wygranej gry: Brak wygranych gier.");
        }
    }
}
