package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.SpellCheckingService;
import org.example.home_internet_hero.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class WordController {

    private final WordService wordService;
    private final SpellCheckingService spellCheckingService;

    @Autowired
    public WordController(WordService wordService, SpellCheckingService spellCheckingService) {
        this.wordService = wordService;
        this.spellCheckingService = spellCheckingService;
    }

    @GetMapping("/all")
    public List<String> getAllWords() {
        return wordService.getAllWords();
    }

    @GetMapping("/frequency/{word}")
    public int getWordFrequency(@PathVariable String word) {
        return wordService.getWordFrequency(word);
    }

    @GetMapping("/suggestions")
    public List<String> getSpellSuggestions(@RequestParam String prefix) {
        // Step 1: Get a list of words based on prefix from the WordService
        List<String> wordSuggestions = wordService.getSpellSuggestions(prefix);

        // Step 2: If WordService doesn't return results, use SpellCheckingService
        if (wordSuggestions.isEmpty()) {
            wordSuggestions = spellCheckingService.getSpellSuggestions(prefix, wordService.getAllWords());
        }

        return wordSuggestions;
    }

    @GetMapping("/spell-correction")
    public List<String> getSpellCorrectionSuggestions(@RequestParam String word) {
        // Step 1: Get spell check suggestions from WordService
        List<String> allWords = wordService.getAllWords();

        // Step 2: Use SpellCheckingService to find the best matches
        return spellCheckingService.getSpellSuggestions(word, allWords);
    }

    @GetMapping("/debug")
    public Map<String, String> debug() {
        return Map.of("Word Map", wordService.getAllWordsMap().toString());
    }
}
