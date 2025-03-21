package org.example.home_internet_hero.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Crawler {

    // Define the 4 different base URLs
    private static final String[] BASE_URLS = {
            "https://www.rogers.com/",
            "https://www.telus.com/",
            "https://www.bell.ca/",
            "https://www.fido.ca/",
            "https://www.att.com/"
    };

    private static final String SAVE_DIRECTORY = "src/main/resources/url_file";
    private static final int MAX_PAGES_PER_SITE = 5;  // Limit to 5 pages per website
    private static final int MAX_TOTAL_PAGES = 35;    // Stop after 35 pages in total

    private Set<String> visitedPages = new HashSet<>();
    private Queue<String> pagesToVisit = new LinkedList<>();
    private Map<String, Integer> sitePageCount = new HashMap<>();
    private int totalPagesCrawled = 0; // Global page count

    public static void main(String[] args) {
        Crawler crawler = new Crawler();

        // Initialize the crawler with the 4 base URLs
        for (String baseUrl : BASE_URLS) {
            crawler.pagesToVisit.add(baseUrl); // Add each base URL to the queue
            crawler.sitePageCount.put(baseUrl, 0); // Initialize site count
        }

        crawler.crawl();
    }

    public void crawl() {
        while (!pagesToVisit.isEmpty() && totalPagesCrawled < MAX_TOTAL_PAGES) {
            String url = pagesToVisit.poll();
            if (url != null && !visitedPages.contains(url)) {
                visitPage(url);
            }
        }
        System.out.println("\nCrawling finished! Total pages scraped: " + totalPagesCrawled);
    }

    private void visitPage(String url) {
        if (totalPagesCrawled >= MAX_TOTAL_PAGES) return; // Stop if we reached 100

        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println("Visiting: " + url);
            visitedPages.add(url);

            // Save the HTML content of the page to a file
            saveHtmlToFile(doc, url);

            // Update counters
            totalPagesCrawled++;
            String baseSite = getBaseUrl(url);

            // Extract and add links to other pages on the site
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkHref = link.absUrl("href");
                if (shouldVisit(linkHref)) {
                    pagesToVisit.add(linkHref);
                    sitePageCount.put(baseSite, sitePageCount.getOrDefault(baseSite, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing: " + url + " - " + e.getMessage());
        }
    }

    // Check if a URL should be crawled
    private boolean shouldVisit(String url) {
        if (url.startsWith("javascript:") || !url.startsWith("http")) {
            return false;
        }

        String baseSite = getBaseUrl(url);

        // Blocklist of unwanted domains
        String[] blockedDomains = {
                "facebook.com", "twitter.com", "instagram.com", "linkedin.com", "youtube.com",
                "apps.apple.com", "play.google.com","rogersbank.com","a.localytics.com"
        };

        // Check if the URL contains any blocked domains
        for (String domain : blockedDomains) {
            if (url.contains(domain)) {
                return false;
            }
        }

        // Stop crawling if global limit (100) or site limit (25) is reached
        if (totalPagesCrawled >= MAX_TOTAL_PAGES || sitePageCount.getOrDefault(baseSite, 0) >= MAX_PAGES_PER_SITE) {
            return false;
        }

        return url.startsWith(baseSite) && !visitedPages.contains(url);
    }

    private void saveHtmlToFile(Document doc, String url) {
        try {
            File directory = new File(SAVE_DIRECTORY);
            if (!directory.exists()) directory.mkdir();

            String safeFileName = Paths.get(url.replace("https://", "").replaceAll("[^a-zA-Z0-9.-]", "_")).getFileName().toString();
            File file = new File(SAVE_DIRECTORY + File.separator + safeFileName + ".html");

            FileWriter writer = new FileWriter(file);
            writer.write(doc.html());
            writer.close();

            System.out.println("Saved page to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving file for URL: " + url + " - " + e.getMessage());
        }
    }

    private String getBaseUrl(String url) {
        int endIndex = url.indexOf("/", 8); // Skip "https://"
        return endIndex == -1 ? url : url.substring(0, endIndex);
    }
}
