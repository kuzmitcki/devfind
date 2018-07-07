package com.intinctools.controllers;


import com.intinctools.entities.empEntites.Job;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.developer.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller

public class SearchForJobController {

    private Logger logger = LoggerFactory.getLogger(SearchForJobController.class);



    private final DeveloperService developerService;
    private final UserRepo userRepo;

    public SearchForJobController(DeveloperService developerService, UserRepo userRepo) {
        this.developerService = developerService;
        this.userRepo = userRepo;
    }


    @GetMapping("/job-search")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchPage(){
        return "developer/searchJobPage";
    }




    @PostMapping("/job-search")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchJobs(@RequestParam(name = "whatDescription", required = false) String jobDescription,
                             @RequestParam(name = "whereDescription", required = false)String jobLocation,
                             Model model) {
        model.addAttribute("jobs" , developerService.findWithAddress(jobDescription, jobLocation));
        return "developer/searchResult";
    }

    @GetMapping("/job-search/advanced")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String advancedSearchPage(){
        return "developer/advancedSearch";
    }

    @PostMapping("/job-search/advanced")
    public String advancedSearch(@RequestParam(name = "title", required = false) String title,
                                 @RequestParam(name = "salaryPeriod", required = false) String salaryPeriod,
                                 @RequestParam(name = "company", required = false) String company,
                                 @RequestParam(name = "keywords", required = false) String keywords,
                                 @RequestParam(name = "fullDescription", required = false) String fullDescription,
                                 @RequestParam(name = "location", required = false) String location,
                                 Model model){
        List<Job> employeesByTitle = developerService.findEmployeesByTitle(title);
        List<Job> employeesBySalaryPeriod = developerService.findEmployeesBySalaryPeriod(salaryPeriod);
        Set<Job> jobs = new HashSet<>(employeesBySalaryPeriod );
        logger.info(String.valueOf(jobs));
        return "developer/searchResult";
    }

}
