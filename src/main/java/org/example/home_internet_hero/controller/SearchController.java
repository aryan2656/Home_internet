package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.PageRanking;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.example.home_internet_hero.service.PageRanking.rankFiles;

@Controller
public class SearchController {

    private final PageRanking pageRanking;

    public SearchController(PageRanking pageRanking) {
        this.pageRanking = pageRanking;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Map.Entry<String, Integer>>> search(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        System.out.println("Search query: " + query);

        try {

            // Path to the directory containing text files
            File directory = new File("src/main/resources/url_text");

            // Get list of text files from the directory
            File[] textFiles = directory.listFiles();

//            if (textFiles == null || textFiles.length == 0) {
//                System.out.println("No text files found in directory: " + directory.getAbsolutePath());
//                return;
//            }

            List<Map.Entry<String, Integer>> rankedFiles = rankFiles(textFiles, query);

            return ResponseEntity.ok(rankedFiles);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}



