package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.Plans.model.Plans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.opencsv.CSVReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/plans")
public class PlanController {

    private List<Plans> plansList = new ArrayList<>();

    // Load CSV data when the controller is initialized
    public PlanController() {
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
                        nextRecord[0],  // provider
                        nextRecord[1],  // planName
                        nextRecord[2],  // price
                        nextRecord[3],  // downloadSpeed
                        nextRecord[4],  // uploadSpeed
                        nextRecord[5],  // features
                        "" // Set imageUrl as empty since it's missing
                );

                plansList.add(plan);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @GetMapping
    public String getAllPlans(Model model) {
        model.addAttribute("plans", plansList);
        return "plans"; // Renders plans.html from templates
    }

    @GetMapping("/{id}")
    public String getPlanById(@PathVariable Long id, Model model) {
        for (Plans plan : plansList) {
            if (plan.getId().equals(id)) {
                model.addAttribute("plan", plan);
                return "plan-details.html"; // Renders plan-details.html
            }
        }
        return "error"; // Redirect to error page if the plan is not found
    }
}
