package com.intinctools.controllers;


import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.employeeRepo.JobRepo;
import com.intinctools.service.developer.DeveloperService;
import com.intinctools.service.mail.Mail;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping("job")
@PreAuthorize("hasAuthority('DEVELOPER')")
public class SearchForJobController {
    private final DeveloperService developerService;

    private final JobRepo jobRepo;

    private final Mail mailSender;

    public SearchForJobController(final DeveloperService developerService,
                                  final JobRepo jobRepo,
                                  final Mail mailSender) {
        this.developerService = developerService;
        this.jobRepo = jobRepo;
        this.mailSender = mailSender;
    }

    @GetMapping("search")
    public String searchJobPage(){
        return "developer/search/usual";
    }

    @PostMapping("search")
    public String searchJob(@RequestParam(name = "whatDescription") String whatDescription,
                            @RequestParam(name = "whereDescription", required = false) String whereDescription,
                            HttpServletRequest request){
        request.getSession().setAttribute("jobsRequest",  developerService.searchForJob(whatDescription, whereDescription));
        return "redirect:/job/results";
    }

    @GetMapping("results")
    public String searchResults(HttpServletRequest request,
                                Model model){
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
    public String advancedJobSearchPage(){
        return "developer/search/advanced";
    }

    @PostMapping("search/advanced")
    public String advancedSearch(@RequestParam("allWords") String allWords,
                                 @RequestParam("phrase") String phrase,
                                 @RequestParam("oneWord") String oneWord,
                                 @RequestParam(name = "title",required = false) String title,
                                 @RequestParam("jobType") String jobType,
                                 @RequestParam(name = "salary", required = false) String salary,
                                 HttpServletRequest request) {
        request.getSession().setAttribute("jobsRequest", developerService.searchForJobAdvanced(allWords, phrase, oneWord, title, jobType, salary));
        return "redirect:/job/results";
    }

    @GetMapping("/preview/{id}")
    public String jobPreview(@PathVariable("id") Long id,
                             Model model){
        Job job = jobRepo.getOne(id);
        model.addAttribute("employee", job.getEmployee());
        model.addAttribute("job", job);
        return "developer/preview/jobPreview";
    }

    @PostMapping("/send-resume/{id}")
    public String sendResumeToEmployee(@AuthenticationPrincipal User user,
                                       @PathVariable("id") Long id,
                                       RedirectAttributes attributes){
        mailSender.sendResumeToEmployee(user, id, attributes);
        return "redirect:/job/preview/" + id;
    }
}

