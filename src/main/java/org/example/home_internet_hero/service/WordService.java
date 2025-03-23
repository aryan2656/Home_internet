package org.example.home_internet_hero.service;

import org.example.home_internet_hero.model.AVLTreeNode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class WordService {

    private static final String DIRECTORY_PATH = "url_text/";

    private final Map<String, AVLTreeNode> wordMap = new HashMap<>();

    // Method to update word frequencies
    public void updateWordFrequency(String word) {
        wordMap.putIfAbsent(word, new AVLTreeNode(word));
        wordMap.get(word).setFrequency(wordMap.get(word).getFrequency() + 1);
    }

    // Method to get all words from files in DIRECTORY_PATH
    public List<String> getAllWords() {
        Set<String> allWords = new HashSet<>();
        try {
            Files.walk(Paths.get(DIRECTORY_PATH))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> allWords.addAll(readWordsFromFile(filePath.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(allWords);
    }

    // Method to read words from a file and update frequencies
    private Set<String> readWordsFromFile(File file) {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordArray = line.split("\\s+");
                for (String word : wordArray) {
                    word = word.trim().toLowerCase();
                    if (!word.isEmpty()) {
                        words.add(word);
                        updateWordFrequency(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    // Method to get word frequency from wordMap
    public int getWordFrequency(String word) {
        AVLTreeNode node = wordMap.get(word);
        return node != null ? node.getFrequency() : 0;
    }

    // Method to get all words in the map
    public Map<String, AVLTreeNode> getAllWordsMap() {
        return wordMap;
    }

    // Method to get spell suggestions based on prefix
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
