package org.example.home_internet_hero.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

class AVLNode {
    String word;
    int height;
    AVLNode left, right;

    AVLNode(String word) {
        this.word = word;
        this.height = 1;
    }
}

public class AVLTree {
    private AVLNode root;

    // Get height of a node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Rotate Right
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    // Rotate Left
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // Balance Factor
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Insert a word into AVL Tree
    private AVLNode insert(AVLNode node, String word) {
        if (node == null) return new AVLNode(word);

        if (word.compareTo(node.word) < 0)
            node.left = insert(node.left, word);
        else if (word.compareTo(node.word) > 0)
            node.right = insert(node.right, word);
        else
            return node; // Duplicate words are ignored

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Perform rotations if needed
        if (balance > 1 && word.compareTo(node.left.word) < 0) return rotateRight(node);
        if (balance < -1 && word.compareTo(node.right.word) > 0) return rotateLeft(node);
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void insert(String word) {
        root = insert(root, word);
    }

    // Get words with given prefix
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        searchPrefix(root, prefix, result);
        return result;
    }

    private void searchPrefix(AVLNode node, String prefix, List<String> result) {
        if (node == null) return;
        if (node.word.startsWith(prefix)) result.add(node.word);
        if (node.word.compareTo(prefix) > 0) searchPrefix(node.left, prefix, result);
        if (node.word.compareTo(prefix) < 0) searchPrefix(node.right, prefix, result);
    }

    // Load words from text files in 'url_text' directory
    public void loadWordsFromFiles(String directoryPath) {
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try (BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                String[] words = line.toLowerCase().split("\\W+");
                                for (String word : words) {
                                    if (!word.isEmpty()) insert(word);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
