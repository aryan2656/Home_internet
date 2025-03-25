package org.example.home_internet_hero.controller;

import org.example.home_internet_hero.service.Validation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/validate")
public class ValidationController {

    // Endpoint to start the validation process (file scanning)
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public String startValidation() {
        // Set the directory path where the text files are located
        String folderPath = "src/main/resources/url_text"; // Make sure the path is correct

        // Call the service method to process the files
        Validation.processFileDirectory(folderPath);

        // Redirect to the results page after scanning is complete
        return "redirect:/validate/results";
    }

    // Endpoint to get extracted data and display it in HTML
    @GetMapping("/results")
    public String showResults(Model model) {
        // Get the extracted phone numbers, emails, and URLs
        Set<String> phoneNumbers = Validation.getTotalUniquePhoneNumbers();
        Set<String> emails = Validation.getTotalUniqueEmails();
        Set<String> urls = Validation.getTotalUniqueUrls();

        // Add the extracted data to the model
        model.addAttribute("phoneNumbers", phoneNumbers);
        model.addAttribute("emails", emails);
        model.addAttribute("urls", urls);

        // Return the name of the HTML page to display the data
        return "validation"; // This refers to validation.html
    }
}
