import java.io.*;
import java.util.*;

public class WordRepository {
    private List<String> words;

    public WordRepository() {
        words = new ArrayList<>();
        loadWords();
    }

    private List<String> loadWords() {
        List<String> loadedWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                loadedWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedWords;
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public void addWord(String word) {
        words.add(word);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/words.txt", true))) {
            bw.newLine();
            bw.write(word);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeWord(String word) {
        if (words.remove(word)) {
            saveWords();
            return true;
        }
        return false;
    }

    public boolean editWord(String oldWord, String newWord) {
        if (words.contains(oldWord)) {
            int index = words.indexOf(oldWord);
            words.set(index, newWord);
            saveWords();
            return true;
        }
        return false;
    }

    public List<String> getWordList() {
        return new ArrayList<>(words);
    }

    private void saveWords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/words.txt"))) {
            for (String word : words) {
                bw.write(word);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
