package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PageRanking {

    public static List<Map.Entry<String, Integer>> rankFiles(File[] textFiles, String query) {
        List<Map.Entry<String, Integer>> rankedFiles = new ArrayList<>();

        for (File file : textFiles) {
            int occurrenceCount = countOccurrencesInFile(file, query);
            rankedFiles.add(new AbstractMap.SimpleEntry<>(file.getName(), occurrenceCount));
        }

        // Sort files by occurrence count in descending order
        rankedFiles.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        return rankedFiles;
    }

    private static int countOccurrencesInFile(File file, String query) {
        int count = 0;
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            int index = 0;
            while ((index = content.indexOf(query, index)) != -1) {
                count++;
                index += query.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
