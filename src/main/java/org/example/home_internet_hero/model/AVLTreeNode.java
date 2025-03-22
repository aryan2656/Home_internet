package org.example.home_internet_hero.model;

// Node class for AVL Tree
public class AVLTreeNode {
    public String word;
    public int frequency;
    int height;
    AVLTreeNode left, right;

    public AVLTreeNode(String word) {
        this.word = word.toLowerCase();
        this.frequency = 1;
        this.height = 1;
        this.left = this.right = null;
    }
}
