package org.example.home_internet_hero.service;

import org.example.home_internet_hero.model.AVLTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class WordService {

    private static final String DIRECTORY_PATH = "url_text/"; // Folder where text files are stored
    private final Map<String, AVLTreeNode> wordMap;  // Store word and frequency data

    @Autowired
    public WordService() {
        this.wordMap = new HashMap<>(); // Initialize an empty map for storing words
    }

    // Load all words from the directory
    public List<String> getAllWords() {
        Set<String> allWords = new HashSet<>();
        try {
            Files.walk(Paths.get(DIRECTORY_PATH))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> allWords.addAll(readWordsFromFile(filePath.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert Set to List before returning
        return new ArrayList<>(allWords);
    }

    // Method to read words from a single file
    private Set<String> readWordsFromFile(File file) {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordArray = line.split("\\s+"); // Split words by spaces
                for (String word : wordArray) {
                    word = word.trim().toLowerCase();  // Normalize word to lowercase
                    if (!word.isEmpty()) {
                        words.add(word);
                        updateWordFrequency(word); // Update the frequency count for each word
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    // Update the frequency of a word in the wordMap
    private void updateWordFrequency(String word) {
        wordMap.putIfAbsent(word, new AVLTreeNode(word));  // Add the word if not present
        wordMap.get(word).setFrequency(wordMap.get(word).getFrequency() + 1);  // Increment frequency
    }

    // Search for a word in the map and return its frequency
    public int getWordFrequency(String word) {
        AVLTreeNode node = wordMap.get(word);
        if (node != null) {
            return node.getFrequency();  // Return the frequency of the word
        }
        return 0;  // Return 0 if the word is not found
    }

    // Fetch all words in the map (for displaying purposes)
    public Map<String, AVLTreeNode> getAllWordsMap() {
        return wordMap;
    }

    // Additional method to search for suggestions for incomplete words
    public List<String> getSpellSuggestions(String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (String word : wordMap.keySet()) {
            if (word.startsWith(prefix)) {
                suggestions.add(word);
            }
        }
        return suggestions;
    }
}
