package org.example.home_internet_hero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        System.out.println("Search query: " + query);
        return ResponseEntity.ok("Received query: " + query);
    }
}

