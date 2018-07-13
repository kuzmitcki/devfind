package com.intinctools.controllers;


import com.intinctools.service.developer.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("job-search")
public class SearchForJobController {

    private Logger logger = LoggerFactory.getLogger(SearchForJobController.class);


    private final DeveloperService developerService;

    public SearchForJobController(DeveloperService developerService) {
        this.developerService = developerService;

    }


    @GetMapping()
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchPage() {
        return "developer/searchJobPage";
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchJobs(@RequestParam(name = "whatDescription", required = false) String jobDescription,
                             @RequestParam(name = "whereDescription", required = false) String jobLocation,
                             Model model) {
        model.addAttribute("job", developerService.findWithAddress(jobDescription, jobLocation));
        return "developer/searchResult";
    }

    @GetMapping("/advanced")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String advancedSearchPage() {
        return "developer/advancedSearch";
    }

    @PostMapping("/advanced")
    public String advancedSearch(@RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "salaryPeriod", required = false) String salaryPeriod,
                                 @RequestParam(name = "company", required = false) String company,
                                 @RequestParam(name = "keywords") String keywords,
                                 @RequestParam(name = "fullDescription", required = false) String fullDescription,
                                 @RequestParam(name = "location", required = false) String location,
                                 @RequestParam(name = "fromSalary", required = false) String fromSalary,
                                 @RequestParam(name = "toSalary", required = false) String toSalary,
                                 final Model model) {
        model.addAttribute("jobs" , developerService.advancedSearch(title, salaryPeriod, company, keywords, fullDescription, location, fromSalary, toSalary));
        return "developer/searchResult";


    }
}

