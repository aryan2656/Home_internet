package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class PageRanking {

    // Boyer-Moore Algorithm: Bad Character Heuristic
    public static Map<Character, Integer> badCharacterHeuristic(String pattern) {
        Map<Character, Integer> badChar = new HashMap<>();
        int m = pattern.length();

        for (int i = 0; i < m - 1; i++) {
            badChar.put(pattern.charAt(i), i);
        }
        badChar.put(pattern.charAt(m - 1), m - 1);

        return badChar;
    }

    // Boyer-Moore Search Algorithm to count keyword occurrences
    public static int boyerMooreSearch(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        if (m > n) return 0;

        Map<Character, Integer> badChar = badCharacterHeuristic(pattern);
        int count = 0;
        int i = 0;

        while (i <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }

            if (j == -1) {
                count++;
                i += m;  // Shift by pattern length if full match
            } else {
                i += Math.max(1, j - badChar.getOrDefault(text.charAt(i + j), -1));
            }
        }

        return count;
    }

    // Read text file and return its content as a string
    public static String readTextFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    // Rank text files based on keyword frequency
    public static List<Map.Entry<String, Integer>> rankFiles(File[] textFiles, String keyword) throws IOException {
        List<Map.Entry<String, Integer>> fileRankings = new ArrayList<>();

        for (File file : textFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String content = readTextFile(file.getAbsolutePath());
                int occurrences = boyerMooreSearch(content, keyword);
                fileRankings.add(new AbstractMap.SimpleEntry<>(file.getName(), occurrences));
            }
        }

        // Sort files by occurrence count in descending order
        fileRankings.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        return fileRankings;
    }

    public static void main(String[] args) {
        try {
            // Path to the directory containing text files
            File directory = new File("src/main/resources/url_text");

            // Get list of text files from the directory
            File[] textFiles = directory.listFiles();

            if (textFiles == null || textFiles.length == 0) {
                System.out.println("No text files found in directory: " + directory.getAbsolutePath());
                return;
            }

            // Get user input for keyword
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the word you want to search: ");
            String keyword = sc.next();
            sc.close();

            // Rank files based on keyword occurrences
            List<Map.Entry<String, Integer>> rankedFiles = rankFiles(textFiles, keyword);



            // Display ranked files
            int rank = 1;
            for (Map.Entry<String, Integer> entry : rankedFiles) {
                System.out.println("Rank " + rank + ": " + entry.getKey() + " (Occurrences: " + entry.getValue() + ")");
                rank++;
            }

        } catch (IOException e) {
            System.err.println("Error processing text files: " + e.getMessage());
        }
    }
}
