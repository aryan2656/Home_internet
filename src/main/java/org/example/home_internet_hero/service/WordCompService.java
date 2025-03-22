package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class WordCompService {
    private final Map<String, List<String>> fileIndex = new HashMap<>();
    private static final String DIRECTORY_PATH = "url_text/"; // Folder containing text files

    // Load all text files from the folder
    public void loadAllFiles() {
        try {
            Files.list(Paths.get(DIRECTORY_PATH))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(path -> loadWordsFromFile(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load words from a specific file
    public void loadWordsFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            fileIndex.put(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Autocomplete suggestions
    public List<String> getSuggestions(String prefix) {
        Set<String> suggestions = new TreeSet<>();
        for (List<String> words : fileIndex.values()) {
            for (String word : words) {
                if (word.startsWith(prefix)) {
                    suggestions.add(word);
                }
            }
        }
        return new ArrayList<>(suggestions);
    }

    // Search for a word in all loaded files
    public List<Map<String, String>> searchWord(String query) {
        List<Map<String, String>> results = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : fileIndex.entrySet()) {
            String filePath = entry.getKey();
            long count = entry.getValue().stream().filter(line -> line.contains(query)).count();

            if (count > 0) {
                Map<String, String> result = new HashMap<>();
                result.put("file", filePath);
                result.put("occurrences", String.valueOf(count));
                results.add(result);
            }
        }
        return results;
    }
}
