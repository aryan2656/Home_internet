package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.koodo.model.KoodoPlans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KoodoController {

    private List<KoodoPlans> readCSV() {
        List<KoodoPlans> plansList = new ArrayList<>();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/koodoPlans.csv"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] data;
            csvReader.readNext(); // Skip header row
            while ((data = csvReader.readNext()) != null) {
                if (data.length == 4) {
                    plansList.add(new KoodoPlans(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansList;
    }


    @GetMapping("/koodoData")
    public String showData(Model model) {
        List<KoodoPlans> planList = readCSV();
        model.addAttribute("KoodoPlans", planList);
        return "koodoData"; // Loads the Thymeleaf template
    }
}
