package org.example.home_internet_hero.service;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.ui.Model;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AVLTree {

    // Root node of the AVL Tree
    private Node root;

    public void countWordsFromFile(@NotNull Path file) throws IOException {
        BufferedReader br = Files.newBufferedReader(file);
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split("[^a-zA-Z]+");  // Split by non-alphabetic characters
            for (String word : words) {
                if (!word.isEmpty()) {
                    insert(word.trim().toLowerCase()); // Insert each word into AVL Tree
                }
            }
        }
        br.close();
    }


    // Inner class for AVL Tree node
    private static class Node {
        String word;
        int frequency;
        Node left, right;
        int height;

        Node(String word) {
            this.word = word;
            this.frequency = 1;
            this.height = 1;
        }
    }

    /**
     * Insert a word into the AVL Tree.
     */
    public void insert(String word) {
        root = insert(root, word);
    }

    private Node insert(Node node, String word) {
        if (node == null) {
            return new Node(word);
        }

        if (word.compareTo(node.word) < 0) {
            node.left = insert(node.left, word);
        } else if (word.compareTo(node.word) > 0) {
            node.right = insert(node.right, word);
        } else {
            node.frequency++;  // Word already exists, just increment the frequency
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return balance(node);
    }

    // Balance the tree to maintain AVL properties
    private Node balance(Node node) {
        int balanceFactor = getBalanceFactor(node);

        // Left heavy
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);  // Left-Right case
            }
            return rotateRight(node);  // Left-Left case
        }

        // Right heavy
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);  // Right-Left case
            }
            return rotateLeft(node);  // Right-Right case
        }

        return node;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Get the top N frequent words.
     */
    public List<Map.Entry<String, Integer>> getTopFrequentWords(int topN) {
        List<Map.Entry<String, Integer>> topWords = new ArrayList<>();
        inorderTraversal(root, topWords);

        // Sort by frequency in descending order
        topWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Get top N frequent words
        return topWords.subList(0, Math.min(topN, topWords.size()));
    }

    private void inorderTraversal(Node node, List<Map.Entry<String, Integer>> result) {
        if (node != null) {
            inorderTraversal(node.left, result);
            result.add(new AbstractMap.SimpleEntry<>(node.word, node.frequency));
            inorderTraversal(node.right, result);
        }
    }

    /**
     * Load words from a CSV file and insert them into the tree.
     */
    public void loadWordsFromCSV(String csvFile, Model model) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(",");
            for (String word : words) {
                insert(word.trim().toLowerCase()); // Insert each word into AVL Tree
            }
        }
        br.close();
        model.addAttribute("message", "CSV file processed successfully.");
    }

    /**
     * Load words from a URL and insert them into the tree.
     */
    public void loadWordsFromURL(String urlString, Model model) throws IOException {
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            String[] words = inputLine.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    insert(word.trim().toLowerCase()); // Insert each word into AVL Tree
                }
            }
        }
        in.close();
        model.addAttribute("message", "URL processed successfully.");
    }
}
