package org.example.home_internet_hero.controller;

import jakarta.annotation.PostConstruct;
import org.example.home_internet_hero.service.WordFrequencyCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class FrequencyController {

    @Autowired
    private WordFrequencyCounter wordFrequencyCounter;

    // Show frequency count page
    @GetMapping("/frequencyCount")
    public String showFrequencyCountPage(Model model) {
        // Fetch the top frequent words
        List<Map.Entry<String, Integer>> topWords = wordFrequencyCounter.getTopFrequentWords(10);
        model.addAttribute("topWords", topWords);
        return "frequencyCount"; // This should map to the Thymeleaf HTML template you provided
    }

    @PostConstruct
    public void initialize() {
        File folder = new File("src/main/resources/url_text");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        wordFrequencyCounter.loadWordsFromFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    @PostMapping("/searchWord")
    public String searchWord(@RequestParam String word, Model model) {
        int frequency = wordFrequencyCounter.getWordFrequency(word);
        wordFrequencyCounter.incrementSearchFrequency(word);
        int searchFrequency = wordFrequencyCounter.getSearchFrequency(word);

        model.addAttribute("word", word);
        model.addAttribute("frequency", frequency);
        model.addAttribute("searchFrequency", searchFrequency);
        model.addAttribute("topWords", wordFrequencyCounter.getTopFrequentWords(10));

        return "frequencyCount";
    }

}
