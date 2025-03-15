package org.example.home_internet_hero.controller;

import com.opencsv.CSVReader;
import org.example.home_internet_hero.ATT.model.ATTPlans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ATTController {

    private List<ATTPlans> readCSV() {
        List<ATTPlans> plansList = new ArrayList<>();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/ATTPlans.csv"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] data;
            csvReader.readNext(); // Skip header row
            while ((data = csvReader.readNext()) != null) {
                // Print out the data being read to verify if it's correct
                System.out.println("Read row: " + String.join(", ", data));

                if (data.length == 5) {
                    plansList.add(new ATTPlans(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansList;
    }

    @GetMapping("/attData")
    public String showData(Model model) {
        List<ATTPlans> planList = readCSV();
        model.addAttribute("ATTPlans", planList);

        // Check if the data is correctly passed to the model
        System.out.println("Plans loaded: " + planList.size());

        return "attData"; // Name of the Thymeleaf template
    }
}
