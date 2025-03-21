package org.example.home_internet_hero.controller;

import com.opencsv.CSVReader;
import org.example.home_internet_hero.Fido.model.FidoPlans;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FidoController {

    private List<FidoPlans> readCSV() {
        List<FidoPlans> plansList = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/fidoPlans.csv"))) {
            if (isr == null) {
                throw new RuntimeException("CSV file not found!");
            }
            try (CSVReader csvReader = new CSVReader(isr)) {
                String[] data;
                csvReader.readNext(); // Skip header row
                while ((data = csvReader.readNext()) != null) {
                    String planName = (data.length > 0) ? data[0] : "Unknown Plan";
                    String price = (data.length > 1) ? data[1] : "N/A";
                    String speed = (data.length > 2) ? data[2] : "N/A";
                    String offer = (data.length > 3) ? data[3] : "N/A";

                    plansList.add(new FidoPlans(planName, price, speed, offer));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansList;
    }


    @GetMapping("/fidoData")
    public String showData(Model model) {
        List<FidoPlans> planList = readCSV();
        model.addAttribute("fidoPlans", planList);
        return "fidoData"; // Loads the Thymeleaf template
    }
}
