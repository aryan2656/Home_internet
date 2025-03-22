package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.Plans.model.Plans;
import org.example.home_internet_hero.service.PatternFinding;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082") // Adjust if needed
public class PatternMatching {

    private final PatternFinding patternFinding;

    public PatternMatching(PatternFinding patternFinding) {
        this.patternFinding = patternFinding;
    }

    @PostMapping("/searchpattern")
    public List<Plans> searchPlans(@RequestBody SearchRequest request) {
        return patternFinding.searchPlans(request.getQuery());
    }
}

class SearchRequest {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
