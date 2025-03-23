package org.example.home_internet_hero.model;

public class Hint {
    public String word;  // The suggested word
    public int distance; // The edit distance between the suggested word and the input word

    // Constructor
    public Hint(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    // Getter and Setter methods (optional if you need them)
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
