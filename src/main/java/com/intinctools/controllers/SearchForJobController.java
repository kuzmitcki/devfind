package com.intinctools.controllers;


import com.intinctools.entities.empEntites.Job;
import com.intinctools.repo.employeeRepo.JobRepo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping("job")
public class SearchForJobController {

    private Logger logger = LoggerFactory.getLogger(SearchForJobController.class);


    private final DeveloperService developerService;
    private final JobRepo jobRepo;

    public SearchForJobController(DeveloperService developerService, JobRepo jobRepo) {
        this.developerService = developerService;
        this.jobRepo = jobRepo;
    }

    @GetMapping("search")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchJobPage(){
        return "developer/search/usual";
    }

    @PostMapping("search")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchJob(@RequestParam("whatDescription") String whatDescription,
                            @RequestParam(value = "whereDescription", required = false) String whereDescription,
                            HttpServletRequest request){
        request.getSession().setAttribute("jobsRequest",  developerService.searchForJob(whatDescription, whereDescription));
        return "redirect:/job/results";
    }


    @GetMapping("results")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String searchResults(Model model,
                                HttpServletRequest request){
        Set<Job> jobs = (Set<Job>) request.getSession().getAttribute("jobsRequest");
        if (jobs == null){
            model.addAttribute("jobs", jobRepo.findAll());
        }
        else{
            model.addAttribute("jobs", jobs);
        }
        return "developer/results/results";
    }


    @GetMapping("search/advanced")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String advancedJobSearchPage(){
        return "developer/search/advanced";
    }


    @PostMapping("search/advanced")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String advancedSearch(@RequestParam("allWords") String allWords,
                                 @RequestParam("phrase") String phrase,
                                 @RequestParam("oneWord") String oneWord,
                                 @RequestParam(name = "title",required = false) String title,
                                 @RequestParam("jobType") String jobType,
                                 @RequestParam(name = "salary", required = false) String salary,
                                 @RequestParam("salaryPeriod") String salaryPeriod,
                                 HttpServletRequest request){
        request.getSession().setAttribute("jobsRequest", developerService.searchForJobAdvanced(allWords, phrase, oneWord, title, jobType, salary, salaryPeriod));
        return "redirect:/job/results";
    }
}

