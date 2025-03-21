package org.example.home_internet_hero.rogers.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class rogersWebScraping {

    public static void main(String[] args) throws InterruptedException {

        // Ensure the directory exists
        String directoryPath = "resources";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Creates the directory if it doesn't exist
        }
        // Initialize WebDriver (ChromeDriver in this case)
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Rogers website
            driver.get("https://www.rogers.com/");

            // Maximize the browser window
            driver.manage().window().maximize();

            // Wait for the loading overlay to disappear
            WebDriverWait waitForLoading = new WebDriverWait(driver, Duration.ofSeconds(10));
            waitForLoading.until(ExpectedConditions.invisibilityOfElementLocated(By.id("divAppLoading")));

            // Locate and click on "Internet" in the navigation bar
            WebElement navigationItem = waitForLoading.until(ExpectedConditions.elementToBeClickable(By.id("geMainMenuDropdown_1")));
            navigationItem.click();

            // Fetch all menu items under "Internet"
            List<WebElement> list = driver.findElements(By.cssSelector(".dropdown-items .geMainMenuDropdownBody_1 ul.geMainMenuL2 li"));

            // Iterate through the menu items to find "Shop all internet" and click it
            for (WebElement item : list) {
                String text = item.getText();
                System.out.println(text);
                if (text.contains("Shop all internet")) {
                    item.click();
                    break;
                }
            }

            // Wait for the plans section to be clickable
            WebElement plansContainer = waitForLoading.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='row justify-content-center']")));

            // Move to the plans section for visibility
            WebElement moveToPlan = driver.findElement(By.cssSelector("div[class='row justify-content-center']"));
            Actions actions = new Actions(driver);
            actions.moveToElement(moveToPlan).perform();

            // Fetch all available internet plans
            List<WebElement> plans = driver.findElements(By.cssSelector("div[class='row justify-content-center'] div[class^='col-12 col-sm-6']"));

            System.out.println("Plans: " + plans.size());

            // List to store CSV data
            List<String[]> csvData = new ArrayList<>();
            csvData.add(new String[]{"Plan Name", "Price", "Powered By", "Support", "Speed", "Device"});

            // Iterate over each plan and extract data
            for (WebElement item : plans) {
                try {
                    WebElement plan = item.findElement(By.cssSelector("p.dsa-tile-plan__heading")); // Plan name
                    WebElement price = item.findElement(By.cssSelector("div.ds-price__amountDollars")); // Plan price

                    // Extracting plan features like provider, speed, device count, etc.
                    List<WebElement> features = item.findElements(By.cssSelector("div.dsa-tile-plan__features ul li"));

                    // Add extracted data to the CSV list
                    csvData.add(new String[]{
                            plan.getText(),
                            price.getText(),
                            features.size() > 0 ? features.get(0).getText() : "N/A", // Provider
                            features.size() > 1 ? features.get(1).getText() : "N/A", // Support
                            features.size() > 2 ? features.get(2).getText() : "N/A", // Speed
                            features.size() > 3 ? features.get(3).getText() : "N/A"  // Device
                    });
                } catch (Exception e) {
                    System.out.println("Error extracting plan details: " + e.getMessage());
                }
            }

            // Write data to a CSV file
            String filePath = directoryPath + "/rogersPlans.csv";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String[] row : csvData) {
                    writer.write(String.join(",", row));
                    writer.newLine();
                }
                System.out.println("CSV file written successfully to: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the WebDriver to clean up resources
            driver.quit();
        }
    }
}
