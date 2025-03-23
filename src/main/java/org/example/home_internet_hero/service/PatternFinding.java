package org.example.home_internet_hero.service;

import com.opencsv.CSVReader;
import org.example.home_internet_hero.Plans.model.Plans;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PatternFinding {

    private List<Plans> plansList = new ArrayList<>();

    // Load CSV data when the controller is initialized
    public PatternFinding() {
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

    public List<Plans> searchPlans(String pattern) {
        Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        List<Plans> filteredPlans = new ArrayList<>();

        for (Plans plan : plansList) {
            if (regex.matcher(plan.getPrice()).matches() ||
                    regex.matcher(plan.getPlanName()).matches() ||
                    (plan.getFeatures() != null && regex.matcher(plan.getFeatures()).matches()) || plan.getProvider() != null && regex.matcher(plan.getProvider()).matches())
            {
                filteredPlans.add(plan);
            }
        }
        return filteredPlans;
    }
}
