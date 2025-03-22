package org.example.home_internet_hero.model;

import org.example.home_internet_hero.service.SpellCheckingService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.*;
import java.util.*;

@Component
public class AVLTree {

    private AVLTreeNode root;

    // Method to get the sorted word list based on frequency
    public List<Map.Entry<String, Integer>> getSortedWordList() {
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>();
        collectWords(root, wordList);
        wordList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Sort by frequency
        return wordList;
    }

    // Helper method to collect word-frequency pairs
    private void collectWords(AVLTreeNode node, List<Map.Entry<String, Integer>> wordList) {
        if (node != null) {
            wordList.add(new AbstractMap.SimpleEntry<>(node.getWord(), node.getFrequency())); // Add word and frequency
            collectWords(node.left, wordList);
            collectWords(node.right, wordList);
        }
    }

    // Method to load words from all text files in the 'url_text' folder inside resources
    public void loadWordsFromResources(Model model) {
        try {
            ClassPathResource resource = new ClassPathResource("url_text");
            File folder = resource.getFile();

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            loadWordsFromFile(file);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to load words from a single file
    private void loadWordsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[^a-zA-Z]+"); // Split by non-alphabetical characters
                for (String word : words) {
                    if (!word.isEmpty()) {
                        insert(word.toLowerCase()); // Insert word into AVL tree (case-insensitive)
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to insert words into the AVL Tree
    public void insert(String word) {
        root = insertRec(root, word);
    }

    // Recursive insertion method for AVL tree
    private AVLTreeNode insertRec(AVLTreeNode node, String word) {
        if (node == null) {
            return new AVLTreeNode(word);
        }

        if (word.compareTo(node.getWord()) < 0) {
            node.left = insertRec(node.left, word);
        } else if (word.compareTo(node.getWord()) > 0) {
            node.right = insertRec(node.right, word);
        } else {
            node.setFrequency(node.getFrequency() + 1); // Increment frequency if word already exists
            return node;
        }

        node = balance(node); // Balance the tree after insertion
        return node;
    }

    // Balancing method to maintain AVL tree properties
    private AVLTreeNode balance(AVLTreeNode node) {
        int balance = getBalance(node);

        // Left heavy
        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }
        // Right heavy
        else if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }

        return node;
    }

    // Left rotation
    private AVLTreeNode rotateLeft(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;
        y.left = x;
        x.right = T2;
        return y;
    }

    // Right rotation
    private AVLTreeNode rotateRight(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;
        x.right = y;
        y.left = T2;
        return x;
    }

    // Method to get the height of a node
    private int height(AVLTreeNode node) {
        return node == null ? 0 : Math.max(height(node.left), height(node.right)) + 1;
    }

    // Method to get the balance factor of a node
    private int getBalance(AVLTreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Method to get suggestions for the input word
    public Set<AVLTreeNode> getSuggestions(String prefix) {
        Set<AVLTreeNode> suggestions = new HashSet<>();
        getSuggestionsRec(root, prefix, suggestions);
        return suggestions;
    }

    // Helper method to recursively collect suggestions
    private void getSuggestionsRec(AVLTreeNode node, String prefix, Set<AVLTreeNode> suggestions) {
        if (node == null) {
            return;
        }

        if (node.getWord().startsWith(prefix)) {
            suggestions.add(node); // Add node if word matches prefix
        }

        getSuggestionsRec(node.left, prefix, suggestions);
        getSuggestionsRec(node.right, prefix, suggestions);
    }

    // Method to check if a word exists in the AVL tree
    public boolean isWordCorrect(String word) {
        return isWordCorrectRec(root, word);
    }

    // Recursive method to check if the word exists
    private boolean isWordCorrectRec(AVLTreeNode node, String word) {
        if (node == null) {
            return false;
        }

        if (word.compareTo(node.getWord()) < 0) {
            return isWordCorrectRec(node.left, word);
        } else if (word.compareTo(node.getWord()) > 0) {
            return isWordCorrectRec(node.right, word);
        } else {
            return true; // Word found in the tree
        }
    }

    // Spell checking suggestions logic
    public List<String> getSpellSuggestions(String inputWord, List<String> dictionary) {
        return new SpellCheckingService().getSpellSuggestions(inputWord, dictionary);
    }

    // Constructor
    public AVLTree() {
        this.root = null;
    }

    // Search for a word in the tree and return its node if found
    public AVLTreeNode search(String word) {
        return searchRec(root, word.toLowerCase());
    }

    // Recursive search method
    private AVLTreeNode searchRec(AVLTreeNode node, String word) {
        if (node == null || node.getWord().equals(word)) {
            return node;
        }

        if (word.compareTo(node.getWord()) < 0) {
            return searchRec(node.left, word);
        }

        return searchRec(node.right, word);
    }
}
