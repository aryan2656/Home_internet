package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.PageRanking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PageRankingController {

    private final PageRanking pageRanking;

    public PageRankingController(PageRanking pageRanking) {
        this.pageRanking = pageRanking;
    }

    @PostMapping("/rank")
    public ResponseEntity<List<Map.Entry<String, Integer>>> search(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        System.out.println("Received search query: " + query); // Debugging Log

        if (query == null || query.trim().isEmpty()) {
            System.out.println("Empty search query received.");
            return ResponseEntity.badRequest().build();
        }

        File directory = new File("src/main/resources/url_text");

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
            return ResponseEntity.ok(Collections.emptyList());
        }

        File[] textFiles = directory.listFiles((dir, name) -> name.endsWith(".txt")); // Only process .txt files

        if (textFiles == null || textFiles.length == 0) {
            System.out.println("No text files found in the directory.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        try {
            List<Map.Entry<String, Integer>> rankedFiles = pageRanking.rankFiles(textFiles, query);
            System.out.println("Ranked files: " + rankedFiles); // Debugging

            // Strip the .txt extension and return as links
            List<Map.Entry<String, Integer>> result = rankedFiles.stream()
                    .map(entry -> new AbstractMap.SimpleEntry<>(
                            entry.getKey().replace(".txt", ""), // Remove .txt extension
                            entry.getValue()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/rankPage")
    public String showHomePage() {
        return "pageRank";  // This will render src/main/resources/templates/pageRank.html
    }
}
