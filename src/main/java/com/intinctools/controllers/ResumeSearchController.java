package com.intinctools.controllers;

import com.intinctools.entities.devEntities.DesiredJob;
import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.developerRepo.DeveloperRepo;
import com.intinctools.service.employee.EmployeeService;
import com.intinctools.service.mail.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("resume")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class ResumeSearchController {
    private final EmployeeService employeeService;

    private final DeveloperRepo developerRepo;

    private final Logger l = LoggerFactory.getLogger(ResumeSearchController.class);
    private final MailSender mailSender;

    public ResumeSearchController(final EmployeeService employeeService,
                                  final DeveloperRepo developerRepo,
                                  final MailSender mailSender) {
        this.employeeService = employeeService;
        this.developerRepo = developerRepo;
        this.mailSender = mailSender;
    }

    @GetMapping("/search")
    public String findResumePage() {
        return "employee/search/usual";
    }

    @PostMapping("/search")
    public String findResume(@RequestParam("whatDescription") String whatDescription,
                             @RequestParam(value = "whereDescription") String whereDescription,
                             HttpServletRequest request) {
        request.getSession().setAttribute("devs", employeeService.searchForResume(whatDescription, whereDescription));
        return "redirect:/resume/results";
    }

    @GetMapping("/results")
    public String resultsPage(Model model,
                              HttpServletRequest request) {
        Set<Developer> developers = (Set<Developer>) request.getSession().getAttribute("devs");
        if (developers == null) {
            model.addAttribute("developers", new HashSet<>(developerRepo.findAll()));
        } else {
            model.addAttribute("developers", developers);
        }
        return "employee/results/results";
    }

    @GetMapping("/search/advanced")
    public String advancedSearch() {
        return "employee/search/advanced";
    }

    @PostMapping("/search/advanced")
    public String findResumeAdvanced(@AuthenticationPrincipal User user,
                                     @RequestParam("allWords") String allWords,
                                     @RequestParam("phrase") String phrase,
                                     @RequestParam("oneWord") String oneWord,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "company", required = false) String company,
                                     @RequestParam(name = "experience", required = false) Long experience,
                                     @RequestParam(name = "place", required = false) String place,
                                     @RequestParam(name = "degree", required = false) String degree,
                                     @RequestParam(name = "field", required = false) String field,
                                     @RequestParam(name = "location", required = false) String location,
                                     HttpServletRequest request) {
        request.getSession().setAttribute("devs", employeeService.searchForResumeAdvanced(user, allWords, phrase, oneWord, title, company,
                experience, place, degree, field, location));
        return "redirect:/resume/results";
    }

    @GetMapping("/preview/{id}")
    public String previewResume(@PathVariable("id") Long id,
                                Model model) {
        Developer developer = developerRepo.getOne(id);
        model.addAttribute("developer", developer);
        if (developer.isEnable()){
            model.addAttribute("enable", "enable");
        }
        return "employee/preview";
    }

    @PostMapping("/offer/{id}")
    public String sendOfferToDeveloper(@AuthenticationPrincipal User user,
                                       @PathVariable("id") Long id,
                                       RedirectAttributes attributes) {
        mailSender.sendOfferToDeveloper(user, id, attributes);
        return "redirect:/resume/preview/" + id;
    }
}
