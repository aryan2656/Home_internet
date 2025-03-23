package org.example.home_internet_hero.model;

import java.util.*;

public class AVLTree {

    private Node root;

    // Node class to represent each word in the AVL tree
    private static class Node {
        String word;
        int frequency;
        Node left, right;

        public Node(String word) {
            this.word = word;
            this.frequency = 1; // Initially the word's frequency is 1
        }
    }

    // Insert a word into the AVL Tree
    public void insert(String word) {
        root = insertRecursive(root, word);
    }

    private Node insertRecursive(Node node, String word) {
        if (node == null) {
            return new Node(word);
        }

        if (word.compareTo(node.word) < 0) {
            node.left = insertRecursive(node.left, word);
        } else if (word.compareTo(node.word) > 0) {
            node.right = insertRecursive(node.right, word);
        } else {
            // If the word already exists, increment its frequency
            node.frequency++;
            return node;
        }

        // Balance the tree after insertion
        return balance(node);
    }

    // Balance the AVL tree
    private Node balance(Node node) {
        int balanceFactor = height(node.left) - height(node.right);

        if (balanceFactor > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                return rotateRight(node); // Left heavy
            } else {
                node.left = rotateLeft(node.left); // Left-right heavy
                return rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (height(node.right.right) >= height(node.right.left)) {
                return rotateLeft(node); // Right heavy
            } else {
                node.right = rotateRight(node.right); // Right-left heavy
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Perform right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        return x;
    }

    // Perform left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        return y;
    }

    // Calculate the height of the node
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // Retrieve the frequency of a specific word
    public int getWordFrequency(String word) {
        Node node = search(word);
        if (node != null) {
            return node.frequency;
        }
        return 0; // Word not found
    }

    // Search for a word in the AVL tree
    private Node search(String word) {
        return searchRecursive(root, word);
    }

    private Node searchRecursive(Node node, String word) {
        if (node == null || node.word.equals(word)) {
            return node;
        }

        if (word.compareTo(node.word) < 0) {
            return searchRecursive(node.left, word);
        } else {
            return searchRecursive(node.right, word);
        }
    }

    // Get the list of word-frequency pairs in sorted order
    public List<Map.Entry<String, Integer>> getSortedWordList() {
        List<Map.Entry<String, Integer>> wordList = new ArrayList<>();
        inOrderTraversal(root, wordList);
        return wordList;
    }

    // In-order traversal to get words in sorted order
    private void inOrderTraversal(Node node, List<Map.Entry<String, Integer>> wordList) {
        if (node != null) {
            inOrderTraversal(node.left, wordList);
            wordList.add(new AbstractMap.SimpleEntry<>(node.word, node.frequency));
            inOrderTraversal(node.right, wordList);
        }
    }
}
