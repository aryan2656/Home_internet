package org.example.home_internet_hero.model;

public class AVLTreeNode {
    public String word;
    public int frequency;
    public AVLTreeNode left;
    public AVLTreeNode right;

    // Constructor to initialize word and frequency
    public AVLTreeNode(String word) {
        this.word = word;
        this.frequency = 1;  // Default frequency to 1 when a word is inserted
    }

    // Optionally, constructor to initialize both word and frequency
    public AVLTreeNode(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    // Getters and setters for word and frequency
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
