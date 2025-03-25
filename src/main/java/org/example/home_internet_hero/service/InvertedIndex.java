package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class InvertedIndex {
    private static final int R = 256; // ASCII Characters
    private TrieNode root;

    // Trie Node with a HashMap to store occurrences (file ID → count)
    private static class TrieNode {
        Map<String, Integer> fileOccurrences = new HashMap<>(); // Filename -> Word Count
        TrieNode[] next = new TrieNode[R];
    }

    public InvertedIndex() {
        root = new TrieNode();
    }

    // Insert a word from a file into the Trie
    public void insert(String word, String filename) {
        root = insert(root, word.toLowerCase(), filename, 0);
    }

    private TrieNode insert(TrieNode x, String word, String filename, int d) {
        if (x == null) x = new TrieNode();
        if (d == word.length()) {
            x.fileOccurrences.put(filename, x.fileOccurrences.getOrDefault(filename, 0) + 1);
            return x;
        }
        char c = word.charAt(d);
        x.next[c] = insert(x.next[c], word, filename, d + 1);
        return x;
    }

    // Search for a word and return occurrences in files
    public Map<String, Integer> search(String word) {
        TrieNode x = search(root, word.toLowerCase(), 0);
        return (x == null) ? new HashMap<>() : x.fileOccurrences;
    }

    private TrieNode search(TrieNode x, String word, int d) {
        if (x == null) return null;
        if (d == word.length()) return x;
        char c = word.charAt(d);
        return search(x.next[c], word, d + 1);
    }

    // Get top k files with the highest word occurrences
    public List<String> getTopKFiles(String word, int k) {
        Map<String, Integer> fileOccurrences = search(word);
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue() // Sort by occurrences (max heap)
        );

        pq.addAll(fileOccurrences.entrySet());

        List<String> topKFiles = new ArrayList<>();
        while (!pq.isEmpty() && k-- > 0) {
            topKFiles.add(pq.poll().getKey());
        }
        return topKFiles;
    }

    // Read words from a text file and insert into Trie
    public void processFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+"); // Split by non-word characters
            for (String word : words) {
                if (!word.isEmpty()) insert(word, filename);
            }
        }
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        InvertedIndex searchEngine = new InvertedIndex();

        File directory = new File("src/main/resources/url_text");

        // Get list of text files from the directory
        File[] textFiles = directory.listFiles();

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in directory: " + directory.getAbsolutePath());
            return;
        }

// Store file names in a String array
        String[] fileNames = new String[textFiles.length];
        for (int i = 0; i < textFiles.length; i++) {
            fileNames[i] = textFiles[i].getAbsolutePath(); // Store full path
        }

// Print file names (Optional for debugging)
        System.out.println("Files found: " + Arrays.toString(fileNames));

// Process each file
        for (String fileName : fileNames) {
            searchEngine.processFile(fileName);
        }

        // User Input for Searching
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to search: ");
        String keyword = scanner.next();
        scanner.close();

        // Display occurrences
        Map<String, Integer> occurrences = searchEngine.search(keyword);
        if (occurrences.isEmpty()) {
            System.out.println("No occurrences found for: " + keyword);
        } else {
            System.out.println("Occurrences of '" + keyword + "': " + occurrences);
        }

        // Display Top K files
        int k = 2;
        List<String> topFiles = searchEngine.getTopKFiles(keyword, k);
        System.out.println("Top " + k + " files for '" + keyword + "': " + topFiles);
    }
}
