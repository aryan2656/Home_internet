package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.Plans.model.Plans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private List<Plans> plansList = new ArrayList<>();

    // Load CSV data when the controller is initialized
    public LoginController() {
        loadPlansFromCSV();
    }

    private void loadPlansFromCSV() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("plans.csv");
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found!");
            }

            Reader reader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;
            csvReader.readNext(); // Skip header

            while ((nextRecord = csvReader.readNext()) != null) {
                if (nextRecord.length < 7) {
                    System.err.println("Skipping invalid row: " + String.join(",", nextRecord));
                    continue;
                }

                Plans plan = new Plans(
                        nextRecord[0], // provider
                        nextRecord[1], // planName
                        nextRecord[2], // price
                        nextRecord[3], // downloadSpeed
                        nextRecord[4], // uploadSpeed
                        nextRecord[5], // features
                        nextRecord.length > 6 ? nextRecord[6] : "" // Handle missing imageUrl
                );

                plansList.add(plan);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("plans", plansList);
        return "home"; // This will load home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // This will load login.html
    }
}
