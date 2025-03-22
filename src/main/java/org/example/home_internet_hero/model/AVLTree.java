package org.example.home_internet_hero.model;

import java.io.*;
import java.net.*;
import java.util.*;

// AVL Tree class for word storage and search
public class AVLTree {
    private AVLTreeNode root;

    private int height(AVLTreeNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(AVLTreeNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private AVLTreeNode rotateRight(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private AVLTreeNode rotateLeft(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private AVLTreeNode insert(AVLTreeNode node, String word) {
        if (node == null) return new AVLTreeNode(word);

        if (word.compareTo(node.word) < 0) {
            node.left = insert(node.left, word);
        } else if (word.compareTo(node.word) > 0) {
            node.right = insert(node.right, word);
        } else {
            node.frequency++;
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Perform rotations if unbalanced
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

    private void findWordsWithPrefix(AVLTreeNode node, String prefix, List<AVLTreeNode> results) {
        if (node == null) return;
        if (node.word.startsWith(prefix)) results.add(node);
        findWordsWithPrefix(node.left, prefix, results);
        findWordsWithPrefix(node.right, prefix, results);
    }

    public PriorityQueue<AVLTreeNode> getSuggestions(String prefix) {
        List<AVLTreeNode> results = new ArrayList<>();
        findWordsWithPrefix(root, prefix, results);
        PriorityQueue<AVLTreeNode> maxHeap = new PriorityQueue<>((a, b) -> b.frequency - a.frequency);
        maxHeap.addAll(results);
        return maxHeap;
    }
}
