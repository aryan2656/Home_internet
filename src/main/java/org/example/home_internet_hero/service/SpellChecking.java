package org.example.home_internet_hero.service;

import java.util.ArrayList;
import java.util.List;

public class SpellChecking {

    public static class CuckooHashingHashTable {
        String[] t1, t2;
        int space;
        int Max_Iterate_Limit = 40;

        public CuckooHashingHashTable(int space) {
            this.space = space;
            t1 = new String[space];
            t2 = new String[space];
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

    public static class AlternateWords {
        String word;
        int distance;

        public AlternateWords(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }
    }

    public static class MergeSortMethod {
        public static void sort(List<AlternateWords> alternateWords) {
            if (alternateWords.size() < 2) {
                return;
            }
            int mid = alternateWords.size() / 2;
            List<AlternateWords> left = new ArrayList<>(alternateWords.subList(0, mid));
            List<AlternateWords> right = new ArrayList<>(alternateWords.subList(mid, alternateWords.size()));

            sort(left);
            sort(right);
            merge(alternateWords, left, right);
        }

        private static void merge(List<AlternateWords> alternateWords, List<AlternateWords> left, List<AlternateWords> right) {
            int i = 0, j = 0, k = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i).distance <= right.get(j).distance) {
                    alternateWords.set(k++, left.get(i++));
                } else {
                    alternateWords.set(k++, right.get(j++));
                }
            }
            while (i < left.size()) {
                alternateWords.set(k++, left.get(i++));
            }
            while (j < right.size()) {
                alternateWords.set(k++, right.get(j++));
            }
        }
    }
}
