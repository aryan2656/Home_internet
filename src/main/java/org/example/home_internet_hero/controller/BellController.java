package org.example.home_internet_hero.controller;

import com.opencsv.CSVReader;
import org.example.home_internet_hero.Bell.model.BellPlans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BellController {

    private List<BellPlans> readCSV() {
        List<BellPlans> plansList = new ArrayList<>();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/BellPlans.csv"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] data;
            csvReader.readNext(); // Skip header row
            while ((data = csvReader.readNext()) != null) {
                if (data.length == 4) {
                    plansList.add(new BellPlans(data[0], data[1], data[2], data[3]));
                } else if (data.length == 3) { // Handle plans with missing upload/download speeds
                    plansList.add(new BellPlans(data[0], data[1], "N/A", "N/A"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansList;
    }

    @GetMapping("/bellData")
    public String showData(Model model) {
        List<BellPlans> planList = readCSV();
        model.addAttribute("BellPlans", planList);
        return "bellData"; // Loads the Thymeleaf template
    }
}
