package org.example.home_internet_hero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BellController {

    @GetMapping("/bellData")
    public String fido() {
        return "bellData";
    }
}
