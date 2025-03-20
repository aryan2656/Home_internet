package org.example.home_internet_hero.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Crawler {

    private static final String[] BASE_URLS = {
            "https://www.rogers.com",
            "https://www.telus.com",
            "https://www.bell.ca",
            "https://www.fido.ca"
    };

    private static final String SAVE_DIRECTORY = "url_file";
    private static final int MAX_PAGES_PER_SITE = 5;
    private static final int MAX_TOTAL_PAGES = 20;

    private Set<String> visitedPages = new HashSet<>();
    private Queue<String> pagesToVisit = new LinkedList<>();
    private Map<String, Integer> sitePageCount = new HashMap<>();
    private int totalPagesCrawled = 0;

    public static void main(String[] args) {

        // creating object of Crawler class
        Crawler crawler = new Crawler();

        // first storing all base url to pageToVisit queue and sitePageCount and setting it 0
        for (String baseUrl : BASE_URLS) {
            crawler.sitePageCount.put(crawler.getBaseUrl(baseUrl), 0); // Ensure consistent site tracking
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
        if (totalPagesCrawled >= MAX_TOTAL_PAGES) return;

        String baseSite = getBaseUrl(url);
        System.out.println("Base url " + baseSite + ": " + url);

        // Ensure sitePageCount has a default value to prevent NullPointerException
        int pageCount = sitePageCount.getOrDefault(baseSite, 0);
        System.out.println("Page count: " + pageCount);
        if (pageCount >= MAX_PAGES_PER_SITE) return;

        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println("Visiting: " + url);
            visitedPages.add(url);
            saveHtmlToFile(doc, url);

            totalPagesCrawled++;
            sitePageCount.put(baseSite, pageCount + 1);  // Increment count safely
//            System.out.println(Base + sitePageCount.get(baseSite));

//            if (sitePageCount.get(baseSite) < MAX_PAGES_PER_SITE) {
//                Elements links = doc.select("a[href]");
//                for (Element link : links) {
//                    String linkHref = link.absUrl("href");
//                    if (shouldVisit(linkHref)) {
//                        pagesToVisit.add(linkHref);
//                    }
//                }
//            }

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkHref = link.absUrl("href");
                if (shouldVisit(linkHref)) {
                    pagesToVisit.add(linkHref);
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing: " + url + " - " + e.getMessage());
        }
    }

    private boolean shouldVisit(String url) {
        String baseSite = getBaseUrl(url);
        return !visitedPages.contains(url) && url.startsWith(baseSite) &&
                sitePageCount.getOrDefault(baseSite, 0) < MAX_PAGES_PER_SITE &&
                totalPagesCrawled < MAX_TOTAL_PAGES;
    }

    private void saveHtmlToFile(Document doc, String url) {
        try {
            File directory = new File(SAVE_DIRECTORY);
            if (!directory.exists()) directory.mkdir();

            String safeFileName = url.replace("https://", "").replaceAll("[^a-zA-Z0-9.-]", "_");
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
        int endIndex = url.indexOf("/", 8);
        return endIndex == -1 ? url : url.substring(0, endIndex);
    }

}
