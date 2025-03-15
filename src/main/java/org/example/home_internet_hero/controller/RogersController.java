package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.rogers.model.RogersPlans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RogersController {

    private List<RogersPlans> readCSV() {
        List<RogersPlans> plansList = new ArrayList<>();

        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/rogersPlans.csv"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] data;
            csvReader.readNext(); // Skip header row
            int count = 0; // Debugging: Count number of rows read

            while ((data = csvReader.readNext()) != null) {
                if (data.length == 6) {
                    plansList.add(new RogersPlans(data[0], data[1], data[2], data[3], data[4], data[5]));
                    count++; // Increment count for each valid row
                } else {
                    System.out.println("Skipping invalid row: " + String.join(",", data)); // Debugging
                }
            }

            System.out.println("Total Rows Read: " + count); // Debugging: Print the number of rows loaded

        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansList;
    }


    @GetMapping("/rogersData")
    public String showData(Model model) {
        List<RogersPlans> planList = readCSV();
        model.addAttribute("RogersPlans", planList);
        return "rogersData"; // Thymeleaf template
    }
}
