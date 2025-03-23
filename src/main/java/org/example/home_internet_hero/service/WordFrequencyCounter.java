package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordFrequencyCounter {

    private Map<String, Integer> wordFrequencyMap = new HashMap<>();
    private Map<String, Integer> searchFrequencyMap = new HashMap<>();

    // Load words from a given text file and count their frequencies
    public void loadWordsFromFile(File file) throws IOException {
        wordFrequencyMap.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Clean up the word
                    if (!word.isEmpty()) {
                        wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }
    }

    // Get frequency of a specific word from the file
    public int getWordFrequency(String word) {
        return wordFrequencyMap.getOrDefault(word.toLowerCase(), 0);
    }

    // Get top N frequent words
    public List<Map.Entry<String, Integer>> getTopFrequentWords(int n) {
        return wordFrequencyMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(n)
                .collect(Collectors.toList());
    }

    // Frequency count from a URL's content
    public int getFrequencyFromUrl(String url) {
        int frequency = 0;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String content = restTemplate.getForObject(url, String.class);
            if (content != null) {
                String[] words = content.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Clean up the word
                    if (!word.isEmpty()) {
                        wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frequency;
    }

    // Increment search frequency for a word
    public void incrementSearchFrequency(String word) {
        searchFrequencyMap.put(word, searchFrequencyMap.getOrDefault(word, 0) + 1);
    }

    // Get the search frequency of a word
    public int getSearchFrequency(String word) {
        return searchFrequencyMap.getOrDefault(word.toLowerCase(), 0);
    }
}
