//package org.example.home_internet_hero.controller;
//
//import org.example.home_internet_hero.Plans.model.Plans;
//import org.springframework.web.bind.annotation.*;
//
//import com.opencsv.CSVReader;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/plans")
//@CrossOrigin(origins = "*")
//public class PlanController {
//
//    private List<Plans> plansList = new ArrayList<>();
//
//    public PlanController() {
//        loadPlansFromCSV();
//    }
//
//    private void loadPlansFromCSV() {
//        try {
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("plans.csv");
//            if (inputStream == null) {
//                throw new RuntimeException("CSV file not found!");
//            }
//
//            Reader reader = new InputStreamReader(inputStream);
//            CSVReader csvReader = new CSVReader(reader);
//            String[] nextRecord;
//            csvReader.readNext(); // Skip header
//
//            while ((nextRecord = csvReader.readNext()) != null) {
//                if (nextRecord.length < 6) {
//                    continue;
//                }
//
//                Plans plan = new Plans(
//                        nextRecord[0],  // provider
//                        nextRecord[1],  // planName
//                        nextRecord[2],  // price
//                        nextRecord[3],  // downloadSpeed
//                        nextRecord[4],  // uploadSpeed
//                        nextRecord[5],  // features
//                        (nextRecord.length > 6) ? nextRecord[6] : "" // imageUrl
//                );
//
//                plansList.add(plan);
//            }
//            csvReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @GetMapping
//    public List<Plans> getAllPlans() {
//        return plansList;
//    }
//}
