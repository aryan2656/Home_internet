package org.example.home_internet_hero.service;

import org.example.home_internet_hero.model.AVLTree;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.*;

@Service
public class WordFrequencyCounter {

    private AVLTree wordFrequencyTree = new AVLTree();

    // Fetch and process words from a URL
    public void loadWordsFromURL(String url, Model model) {
        try {
            // Fetch the content from the URL
            Document doc = Jsoup.connect(url).get();
            String text = doc.body().text(); // Extract text from the body of the webpage

            // Clean and split the text into words
            String[] words = text.split("[^a-zA-Z]+");

            // Process each word and update the frequency count in the AVL tree
            for (String word : words) {
                if (!word.isEmpty()) {
                    word = word.toLowerCase(); // Case insensitivity
                    wordFrequencyTree.insert(word); // Insert word into AVL tree
                }
            }

            // Add success message
            model.addAttribute("message", "Successfully scanned and counted word frequencies from the URL.");
        } catch (IOException e) {
            model.addAttribute("error", "Error fetching content from the URL: " + e.getMessage());
        }
    }

    // Get the top N frequent words
    public List<Map.Entry<String, Integer>> getTopFrequentWords(int topN) {
        List<Map.Entry<String, Integer>> sortedList = wordFrequencyTree.getSortedWordList();
        List<Map.Entry<String, Integer>> topWords = new ArrayList<>();

        // Select the top N words
        for (int i = 0; i < Math.min(topN, sortedList.size()); i++) {
            topWords.add(sortedList.get(i));
        }

        return topWords;
    }
}
