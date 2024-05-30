import java.io.*;
import java.util.*;

public class WordRepository {
    private List<String> words; // Lista słów

    public WordRepository() {
        words = new ArrayList<>(); // Inicjalizacja listy słów
        loadWords(); // Załadowanie słów z pliku
    }

    // Metoda ładująca słowa z pliku
    private List<String> loadWords() {
        List<String> loadedWords = new ArrayList<>(); // Tymczasowa lista do przechowywania załadowanych słów
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/words.txt"))) { // Otwieranie
                                                                                                       // pliku do
                                                                                                       // odczytu
            String line;
            while ((line = br.readLine()) != null) { // Odczyt linii z pliku
                loadedWords.add(line.trim()); // Dodanie odczytanego słowa do listy po przycięciu białych znaków
            }
        } catch (IOException e) {
            e.printStackTrace(); // Obsługa wyjątku IO
        }
        words = loadedWords; // Przypisanie załadowanych słów do pola klasy
        return loadedWords; // Zwrócenie załadowanych słów (choć nie jest to używane)
    }

    // Metoda zwracająca losowe słowo z listy
    public String getRandomWord() {
        Random random = new Random(); // Obiekt klasy Random do generowania losowych liczb
        return words.get(random.nextInt(words.size())); // Zwrócenie losowego słowa z listy
    }

    // Metoda dodająca nowe słowo do listy i zapisująca je do pliku
    public void addWord(String word) {
        words.add(word); // Dodanie nowego słowa do listy
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/words.txt", true))) { // Otwieranie
                                                                                                             // pliku do
                                                                                                             // zapisu w
                                                                                                             // trybie
                                                                                                             // dopisywania
            bw.newLine(); // Dodanie nowej linii
            bw.write(word); // Zapisanie nowego słowa
            bw.newLine(); // Dodanie nowej linii
        } catch (IOException e) {
            e.printStackTrace(); // Obsługa wyjątku IO
        }
    }

    // Metoda usuwająca słowo z listy i zapisująca zmiany do pliku
    public boolean removeWord(String word) {
        if (words.remove(word)) { // Usunięcie słowa z listy
            saveWords(); // Zapisanie zmian do pliku
            return true; // Zwrócenie wartości true w przypadku powodzenia
        }
        return false; // Zwrócenie wartości false w przypadku niepowodzenia
    }

    // Metoda edytująca istniejące słowo i zapisująca zmiany do pliku
    public boolean editWord(String oldWord, String newWord) {
        if (words.contains(oldWord)) { // Sprawdzenie, czy lista zawiera stare słowo
            int index = words.indexOf(oldWord); // Znalezienie indeksu starego słowa
            words.set(index, newWord); // Zamiana starego słowa na nowe
            saveWords(); // Zapisanie zmian do pliku
            return true; // Zwrócenie wartości true w przypadku powodzenia
        }
        return false; // Zwrócenie wartości false w przypadku niepowodzenia
    }

    // Metoda zwracająca kopię listy słów
    public List<String> getWordList() {
        return new ArrayList<>(words); // Zwrócenie nowej listy zawierającej słowa (kopia)
    }

    // Metoda zapisująca wszystkie słowa do pliku
    void saveWords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/words.txt"))) { // Otwieranie
                                                                                                       // pliku do
                                                                                                       // zapisu
            for (String word : words) { // Iteracja przez wszystkie słowa na liście
                bw.write(word); // Zapisanie słowa do pliku
                bw.newLine(); // Dodanie nowej linii po każdym słowie
            }
        } catch (IOException e) {
            e.printStackTrace(); // Obsługa wyjątku IO
        }
    }
}
