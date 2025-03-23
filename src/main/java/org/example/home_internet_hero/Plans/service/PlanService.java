package org.example.home_internet_hero.Plans.service;

import jakarta.annotation.PostConstruct;
import org.example.home_internet_hero.Plans.model.Plans;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.opencsv.*;

@Service
public class PlanService {

    private List<Plans> plansList = new ArrayList<>();

    @PostConstruct
    public void loadPlans() {
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
                if (nextRecord.length < 7) continue;

                Plans plan = new Plans(
                        nextRecord[0], // Provider
                        nextRecord[1], // Plan Name
                        nextRecord[2], // Price
                        nextRecord[3], // Download Speed
                        nextRecord[4], // Upload Speed
                        nextRecord[5], // Features
                        nextRecord[6]  // Image URL (if available)
                );

                plansList.add(plan);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plans> searchPlans(String pattern) {
        Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        List<Plans> filteredPlans = new ArrayList<>();
        for (Plans plan : plansList) {
            if (regex.matcher(plan.getPrice()).find() ||
                    regex.matcher(plan.getPlanName()).find() ||
                    regex.matcher(plan.getFeatures()).find()) {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }

    public List<Plans> getAllPlans() {
        return plansList;
    }

}

