package org.example.home_internet_hero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FidoController {

    @GetMapping("/fidoData")
    public String fido() {
        return "fidoData";
    }
}
