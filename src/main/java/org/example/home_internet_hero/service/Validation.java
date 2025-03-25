package org.example.home_internet_hero.service;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Validation {

    // RE to match phoneNumbers, URLs, and emails
    private static final String Phone0REGEX = "\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}";
    private static final String Email0REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    private static final String Url0REGEX = "https?://[a-zA-Z0-9.-]+(?:/[a-zA-Z0-9%._+-]*)*";

    // These are the sets for storing unique PhoneNumbers, emails, and URLs
    private static Set<String> totalUniquePhoneNumbers = new HashSet<>();
    private static Set<String> totalUniqueUrls = new HashSet<>();
    private static Set<String> totalUniqueEmails = new HashSet<>();

    public static void processFileDirectory(String folderPathToFile) {
        File folderFile = new File(folderPathToFile);
        File[] listOfAllFiles = folderFile.listFiles((dir0, nameFile) -> nameFile.endsWith(".txt"));

        if (listOfAllFiles == null || listOfAllFiles.length == 0) {
            System.out.println("There are no text files.");
            return;
        }

        // Analyze each file's content
        for (File file01 : listOfAllFiles) {
            analyzeFileContentT(file01);
        }

        // After processing all files, display the extracted results
        displayExtractedResults();
    }

    private static void analyzeFileContentT(File file0) {
        try (BufferedReader bReader = new BufferedReader(new FileReader(file0))) {
            String currentFileLine;

            // Reading the file line-by-line and extract relevant information
            while ((currentFileLine = bReader.readLine()) != null) {
                extractTotalPhoneNumbers(currentFileLine);
                extractTotalEmailAddresses(currentFileLine);
                extractTotalUrls(currentFileLine);
            }
        } catch (IOException exception) {
            System.err.println("Error reading file: " + file0.getName());
            exception.printStackTrace();
        }
    }

    private static void extractTotalPhoneNumbers(String aLine) {
        Pattern phone_T_Pattern = Pattern.compile(Phone0REGEX);
        Matcher phone_T_Matcher = phone_T_Pattern.matcher(aLine);

        while (phone_T_Matcher.find()) {
            totalUniquePhoneNumbers.add(phone_T_Matcher.group());
        }
    }

    private static void extractTotalEmailAddresses(String aLine) {
        Pattern email_T_Pattern = Pattern.compile(Email0REGEX);
        Matcher email_T_Matcher = email_T_Pattern.matcher(aLine);

        while (email_T_Matcher.find()) {
            totalUniqueEmails.add(email_T_Matcher.group());
        }
    }

    private static void extractTotalUrls(String aLine) {
        Pattern url_T_Pattern = Pattern.compile(Url0REGEX);
        Matcher url_T_Matcher = url_T_Pattern.matcher(aLine);

        while (url_T_Matcher.find()) {
            totalUniqueUrls.add(url_T_Matcher.group());
        }
    }

    private static void displayExtractedResults() {
        Iterator<String> phones = totalUniquePhoneNumbers.iterator();
        Iterator<String> emails = totalUniqueEmails.iterator();
        Iterator<String> urls = totalUniqueUrls.iterator();

        // Printing each extracted item
        while (phones.hasNext() || emails.hasNext() || urls.hasNext()) {
            String phone = phones.hasNext() ? phones.next() : "";
            String email = emails.hasNext() ? emails.next() : "";
            String url = urls.hasNext() ? urls.next() : "";

            System.out.printf("| %-30s | %-50s | %-50s |%n", phone, email, url);
        }
    }

    // Getters for the extracted data
    public static Set<String> getTotalUniquePhoneNumbers() {
        return totalUniquePhoneNumbers;
    }

    public static Set<String> getTotalUniqueEmails() {
        return totalUniqueEmails;
    }

    public static Set<String> getTotalUniqueUrls() {
        return totalUniqueUrls;
    }
}
