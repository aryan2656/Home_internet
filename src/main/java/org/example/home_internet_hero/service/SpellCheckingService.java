package org.example.home_internet_hero.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpellCheckingService {

    // Cuckoo Hashing for Spell Checking
    static class CuckooHashingHashTable {
        String[] t1, t2;
        int space;
        int Max_Iterate_Limit = 40;

        public CuckooHashingHashTable(int space) {
            this.space = space;
            t1 = new String[space];
            t2 = new String[space];
            Arrays.fill(t1, null);
            Arrays.fill(t2, null);
        }

        private int hash1(String key) {
            return Math.abs(key.hashCode()) % space;
        }

        private int hash2(String key) {
            return Math.abs((key.hashCode() / space)) % space;
        }

        public void insertion(String key) {
            int count = 0;
            int tableNumber = 1;
            while (count < Max_Iterate_Limit) {
                if (tableNumber == 1) {
                    int hashKey = hash1(key);
                    if (t1[hashKey] == null) {
                        t1[hashKey] = key;
                        return;
                    } else {
                        String temporary = t1[hashKey];
                        t1[hashKey] = key;
                        key = temporary;
                        tableNumber = 2;
                    }
                } else {
                    int hashKey = hash2(key);
                    if (t2[hashKey] == null) {
                        t2[hashKey] = key;
                        return;
                    } else {
                        String temporary = t2[hashKey];
                        t2[hashKey] = key;
                        key = temporary;
                        tableNumber = 1;
                    }
                }
                count++;
            }
        }
    }

    // Edit Distance function
    public static int EditDistance(String w1, String w2) {
        int length1 = w1.length();
        int length2 = w2.length();

        int[][] dpTable = new int[length1 + 1][length2 + 1];

        for (int x = 0; x <= length1; x++) {
            for (int y = 0; y <= length2; y++) {
                if (x == 0) {
                    dpTable[x][y] = y;
                } else if (y == 0) {
                    dpTable[x][y] = x;
                } else if (w1.charAt(x - 1) == w2.charAt(y - 1)) {
                    dpTable[x][y] = dpTable[x - 1][y - 1];
                } else {
                    dpTable[x][y] = 1 + Math.min(dpTable[x - 1][y],
                            Math.min(dpTable[x][y - 1], dpTable[x - 1][y - 1]));
                }
            }
        }

        return dpTable[length1][length2];
    }

    // Get spelling suggestions based on edit distance
    public List<String> getSpellSuggestions(String inputWord, List<String> dictionary) {
        List<String> suggestions = new ArrayList<>();
        CuckooHashingHashTable cuckooHashing = new CuckooHashingHashTable(80);

        // Insert words into cuckoo hash table
        for (String word : dictionary) {
            cuckooHashing.insertion(word);
        }

        // Find alternate words based on edit distance
        List<AlternateWords> alternateSuggestions = new ArrayList<>();
        for (String word : dictionary) {
            int distance = EditDistance(inputWord.toLowerCase(), word.toLowerCase());
            if (distance <= 7) { // Adjust edit distance threshold
                alternateSuggestions.add(new AlternateWords(word, distance));
            }
        }

        // Sort alternate suggestions by distance
        MergeSortMethod.sort(alternateSuggestions);

        // Limit suggestions to the first 5 results
        for (int i = 0; i < Math.min(5, alternateSuggestions.size()); i++) {
            suggestions.add(alternateSuggestions.get(i).word);
        }

        return suggestions;
    }

    // A class to store alternate words with edit distance
    static class AlternateWords {
        String word;
        int distance;

        public AlternateWords(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }
    }

    // Merge sort implementation for sorting alternate words by distance
    static class MergeSortMethod {
        public static void sort(List<AlternateWords> alternateWords) {
            if (alternateWords.size() < 2) {
                return;
            }
            int mid = alternateWords.size() / 2;
            List<AlternateWords> l = new ArrayList<>(alternateWords.subList(0, mid));
            List<AlternateWords> r = new ArrayList<>(alternateWords.subList(mid, alternateWords.size()));

            sort(l);
            sort(r);
            merge(alternateWords, l, r);
        }

        private static void merge(List<AlternateWords> alternateWords, List<AlternateWords> l, List<AlternateWords> r) {
            int i = 0, j = 0, k = 0;
            while (i < l.size() && j < r.size()) {
                if (l.get(i).distance <= r.get(j).distance) {
                    alternateWords.set(k++, l.get(i++));
                } else {
                    alternateWords.set(k++, r.get(j++));
                }
            }
            while (i < l.size()) {
                alternateWords.set(k++, l.get(i++));
            }
            while (j < r.size()) {
                alternateWords.set(k++, r.get(j++));
            }
        }
    }
}
