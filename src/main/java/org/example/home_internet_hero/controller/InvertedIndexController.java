package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.InvertedIndex;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class InvertedIndexController {

    private final InvertedIndex invertedIndex;

    public InvertedIndexController(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
        initializeIndex(); // Automatically load files on startup
    }

    // Load and process all text files in the directory on startup
    private void initializeIndex() {
        File directory = new File("src/main/resources/url_text");
        File[] textFiles = directory.listFiles();

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in directory: " + directory.getAbsolutePath());
            return;
        }

        for (File file : textFiles) {
            if (file.isFile()) {
                try {
                    invertedIndex.processFile(file.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Error processing file: " + file.getAbsolutePath());
                }
            }
        }
        System.out.println("Indexing completed.");
    }

    // Search for a word in indexed files, sorted by occurrences (descending)
    @PostMapping("/rankPage")
    public ResponseEntity<Map<String, Integer>> searchWord(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        System.out.println("Searching word: " + query);

        Map<String, Integer> occurrences = invertedIndex.search(query);

        if (occurrences.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Sort occurrences in descending order
        Map<String, Integer> sortedOccurrences = occurrences.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Descending order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

        return ResponseEntity.ok(sortedOccurrences);
    }

    // Get top K files with highest occurrences of a word, sorted in descending order
    @PostMapping("/top-files")
    public ResponseEntity<List<String>> getTopKFiles(@RequestParam String word, @RequestParam(defaultValue = "5") int k) {
        System.out.println("Searching word: " + word);
        List<String> topFiles = invertedIndex.getTopKFiles(word, k);

        if (topFiles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(topFiles);
    }

    // Manually reprocess all text files (if needed)
    @PostMapping("/reindex")
    public ResponseEntity<String> reindexFiles() {
        initializeIndex();
        return ResponseEntity.ok("Reindexing completed.");
    }

    @GetMapping("/invertedIndex")
    public String showHomePage() {
        return "invertedIndex";  // This will render src/main/resources/templates/pageRank.html
    }
}
