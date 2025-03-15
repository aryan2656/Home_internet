package org.example.home_internet_hero.rogers.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class rogersWebScraping {

    public static void main(String[] args) throws InterruptedException {

        // Initialize WebDriver (ChromeDriver in this case)
        WebDriver driver = new ChromeDriver();

        // Open the Rogers website
        driver.get("https://www.rogers.com/");

        // Maximize the browser window
        driver.manage().window().maximize();

        // Locate and click on "Internet" in the navigation bar
        WebElement navigationItem = driver.findElement((By.id("geMainMenuDropdown_1")));
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

        // Explicit wait for the plans section to be clickable
        WebDriverWait waitForPlanContainer = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement plansContainer = waitForPlanContainer.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='row justify-content-center']")));

        // Move to the plans section for visibility
        WebElement moveToPlan = driver.findElement(By.cssSelector("div[class='row justify-content-center']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(moveToPlan).perform();

        // Fetch all available internet plans
        List<WebElement> plans = driver.findElements(By.cssSelector("div[class='row justify-content-center'] div[class^='col-12 col-sm-6']"));

        System.out.println("Plans " + plans.size());

        // List to store CSV data
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"Plan Name", "Price", "Powered By", "Support", "Speed", "Device"});

        // Iterate over each plan and extract data
        for (WebElement item : plans) {
            WebElement plan = item.findElement((By.cssSelector("p.dsa-tile-plan__heading"))); // Plan name
            WebElement price = item.findElement((By.cssSelector("div.ds-price__amountDollars"))); // Plan price

            // Extracting plan features like provider, speed, device count, etc.
            List<WebElement> features = item.findElements((By.cssSelector("div.dsa-tile-plan__features ul li")));

            // Add extracted data to the CSV list
            csvData.add(new String[]{
                    plan.getText(),
                    price.getText(),
                    features.get(0).getText(), // Provider
                    features.get(1).getText(), // Support
                    features.get(2).getText(), // Speed
                    features.get(3).getText()  // Device
            });

            // Write data to a CSV file
            String filePath = "rogers.csv";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String[] row : csvData) {
                    writer.write(String.join(",", row));
                    writer.newLine();
                }
                System.out.println("CSV file written successfully to: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}