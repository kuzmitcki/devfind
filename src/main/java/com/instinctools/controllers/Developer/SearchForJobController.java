package com.instinctools.controllers.Developer;

import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.developer.search.JobSearch;
import com.instinctools.service.mail.Mail;
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
    private final JobSearch jobSearchForJob;
    private final JobRepo jobRepo;
    private final Mail mailSender;

    public SearchForJobController(JobSearch jobSearchForJob, final JobRepo jobRepo,
                                  final Mail mailSender) {
        this.jobSearchForJob = jobSearchForJob;
        this.jobRepo = jobRepo;
        this.mailSender = mailSender;
    }

    @GetMapping("search")
    public String searchJobPage() {
        return "developer/search/usual";
    }

    @PostMapping("search")
    public String searchJob(final @RequestParam(name = "whatDescription") String whatDescription,
                            final @RequestParam(name = "whereDescription", required = false) String whereDescription,
                            final HttpServletRequest request) {
        request.getSession().setAttribute("jobsRequest",  jobSearchForJob.searchForJob(whatDescription, whereDescription));
        return "redirect:/job/results";
    }

    @GetMapping("results")
    public String searchResults(final HttpServletRequest request,
                                final Model model) {
        Set<Job> jobs = (Set<Job>) request.getSession().getAttribute("jobsRequest");
        if (jobs == null) {
            model.addAttribute("jobs", jobRepo.findAll());
        } else {
            model.addAttribute("jobs", jobs);
        }
        return "developer/results/results";
    }

    @GetMapping("search/advanced")
    public String advancedJobSearchPage() {
        return "developer/search/advanced";
    }

    @PostMapping("search/advanced")
    public String advancedSearch(final @RequestParam("allWords") String allWords,
                                 final @RequestParam("phrase") String phrase,
                                 final @RequestParam("oneWord") String oneWord,
                                 final @RequestParam(name = "title", required = false) String title,
                                 final @RequestParam("jobType") String jobType,
                                 final @RequestParam(name = "salary", required = false) String salary,
                                 final HttpServletRequest request) {
        request.getSession().setAttribute("jobsRequest", jobSearchForJob.searchForJobAdvanced(allWords, phrase, oneWord, title, jobType, salary));
        return "redirect:/job/results";
    }

    @GetMapping("/preview/{id}")
    public String jobPreview(final @PathVariable("id") Long id,
                             final Model model) {
        Job job = jobRepo.getOne(id);
        model.addAttribute("employee", job.getEmployee());
        model.addAttribute("job", job);
        return "developer/preview/jobPreview";
    }

    @PostMapping("/send-resume/{id}")
    public String sendResumeToEmployee(final @AuthenticationPrincipal User user,
                                       final @PathVariable("id") Long id,
                                       final RedirectAttributes attributes) {
        mailSender.sendResumeToEmployee(user, id, attributes);
        return "redirect:/job/preview/" + id;
    }
}

