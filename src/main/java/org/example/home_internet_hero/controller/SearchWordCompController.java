package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.WordCompService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/words")
public class SearchWordCompController {
    private final WordCompService wordService;

    public SearchWordCompController(WordCompService wordService) {
        this.wordService = wordService;
    }

    // Serve the search page (search.html)
    @GetMapping("/search")
    public String showSearchPage(Model model) {
        model.addAttribute("message", "Welcome to the search page");
        return "search";  // This will return search.html if using Thymeleaf
    }

    // Load all text files from "url_text/" folder
    @PostMapping("/load")
    public String loadWords() {
        wordService.loadAllFiles();
        return "redirect:/api/words/search";  // Redirect to show the search page again
    }

    // Get autocomplete suggestions
    @GetMapping("/autocomplete")
    public @ResponseBody List<String> autocomplete(@RequestParam String prefix) {
        return wordService.getSuggestions(prefix);
    }

    // Search for a word in loaded files
    @GetMapping("/searchResults")
    public @ResponseBody List<Map<String, String>> search(@RequestParam String query) {
        return wordService.searchWord(query);
    }
}
