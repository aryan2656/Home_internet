package org.example.home_internet_hero.service;

import java.util.*;

public class Trie {
    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        Set<String> documentIds; // Store document references

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
            documentIds = new HashSet<>();
        }
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word along with its document ID
    public void insert(String word, String documentId) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.documentIds.add(documentId); // Store the reference to the document
    }

    // Search for a word and return the list of document IDs
    public Set<String> search(String word) {
        TrieNode current = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return Collections.emptySet();
            }
            current = current.children.get(ch);
        }
        return current.isEndOfWord ? current.documentIds : Collections.emptySet();
    }
}
