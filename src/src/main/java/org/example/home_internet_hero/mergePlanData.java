package org.example.home_internet_hero;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mergePlanData {

    List<String[]> csvData = new ArrayList<>();
    public void mergeData(String[] files) throws IOException {

        csvData.add(new String[]{"Plan_Name", "Price", "Powered By", "Support", "Speed", "Device"});

        for (String file : files) {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";

            // skips the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                csvData.add(line.split(","));
            }
        }

        // Printing merged data in a readable format
        for (String[] row : csvData) {
            // Using Arrays.toString to print array content properly
            System.out.println(Arrays.toString(row));
        }

        // Writing merged data to a new CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter("merged.csv"));

        // Write the header row (you can add your own header if necessary)
        writer.write("Plan_Name, Price, Powered By, Support, Speed, Device");
        writer.newLine();

        // Write the data rows
        for (String[] row : csvData) {
            writer.write(String.join(", ", row));  // Join the array with commas
            writer.newLine();
        }

        writer.close(); // Close the writer
//        System.out.println("Data has been successfully written to " + );
    }

    public static void main(String args[]) throws IOException {

        String[] dataFiles = {"/Users/aryanitaliya/Documents/ACC/home_internet_hero/src/rogersData.csv", "/Users/aryanitaliya/Documents/ACC/home_internet_hero/src/Koodo.csv"};

        mergePlanData obj = new mergePlanData();
        obj.mergeData(dataFiles);
    }
}
