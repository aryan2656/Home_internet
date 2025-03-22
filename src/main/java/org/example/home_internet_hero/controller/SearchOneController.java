package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.SpellCheckingService;
import org.example.home_internet_hero.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchOneController {

    private final WordService wordService;
    private final SpellCheckingService spellCheckingService;

    @Autowired
    public SearchOneController(WordService wordService, SpellCheckingService spellCheckingService) {
        this.wordService = wordService;
        this.spellCheckingService = spellCheckingService;
    }

    @GetMapping("/search/load")
    public String loadWordsFromUrl(Model model) {
        model.addAttribute("allWords", wordService.getAllWords());
        return "index"; // Return to the same page after loading words
    }

    @GetMapping("/search/complete")
    public String completeWord(@RequestParam String prefix, Model model) {
        List<String> suggestions = spellCheckingService.getSpellSuggestions(prefix, wordService.getAllWords());
        model.addAttribute("completionResults", suggestions);
        model.addAttribute("prefix", prefix); // Include the prefix so that it is pre-filled in the input field
        return "index"; // Return to the same page with suggestions
    }

    @GetMapping("/search/frequency")
    public String getWordFrequency(@RequestParam String word, Model model) {
        int frequency = wordService.getWordFrequency(word);
        model.addAttribute("wordFrequency", frequency);  // Pass the frequency to the view
        model.addAttribute("searchedWord", word); // Add the word to show in the input field
        return "index";  // Return to the same page with the frequency result
    }

    @GetMapping("/search/check")
    public String checkSpelling(@RequestParam String word, Model model) {
        List<String> suggestions = spellCheckingService.getSpellSuggestions(word, wordService.getAllWords());
        model.addAttribute("spellCheckResult", suggestions);
        model.addAttribute("checkedWord", word); // Include the word checked for spelling
        return "index"; // Return to the same page with the spell check results
    }
}
