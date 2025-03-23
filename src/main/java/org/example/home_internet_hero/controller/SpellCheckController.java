package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.AVLTree;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/spellcheck")
public class SpellCheckController {
    private final AVLTree spellChecker;

    public SpellCheckController() {
        this.spellChecker = new AVLTree();
        spellChecker.loadWordsFromFiles("url_text"); // Load words from text files
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam String input) {
        List<String> suggestions = spellChecker.getWordsWithPrefix(input.toLowerCase());
        return ResponseEntity.ok(suggestions);
    }
}
