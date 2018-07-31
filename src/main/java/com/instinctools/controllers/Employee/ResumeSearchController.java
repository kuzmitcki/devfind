package com.instinctools.controllers.Employee;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.DeveloperRepo;
import com.instinctools.service.employee.search.ResumeSearch;
import com.instinctools.service.mail.MailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("resume")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class ResumeSearchController {
    private final ResumeSearch resumeSearch;

    private final DeveloperRepo developerRepo;

    private final MailSender mailSender;

    public ResumeSearchController(final ResumeSearch resumeSearch,
                                  final DeveloperRepo developerRepo,
                                  final MailSender mailSender) {
        this.resumeSearch = resumeSearch;
        this.developerRepo = developerRepo;
        this.mailSender = mailSender;
    }

    @GetMapping("/search")
    public String findResumePage() {
        return "employee/search/usual";
    }

    @PostMapping("/search")
    public String findResume(final @RequestParam("whatDescription") String whatDescription,
                             final @RequestParam(value = "whereDescription") String whereDescription,
                             final HttpServletRequest request) {
        request.getSession().setAttribute("devs", resumeSearch.searchForResume(whatDescription, whereDescription));
        return "redirect:/resume/results";
    }

    @GetMapping("/results")
    public String resultsPage(final Model model,
                              final HttpServletRequest request) {
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
    public String findResumeAdvanced(final @AuthenticationPrincipal User user,
                                     final @RequestParam("allWords") String allWords,
                                     final @RequestParam("phrase") String phrase,
                                     final @RequestParam("oneWord") String oneWord,
                                     final @RequestParam(name = "title", required = false) String title,
                                     final @RequestParam(name = "company", required = false) String company,
                                     final @RequestParam(name = "experience", required = false) Long experience,
                                     final @RequestParam(name = "place", required = false) String place,
                                     final @RequestParam(name = "degree", required = false) String degree,
                                     final @RequestParam(name = "field", required = false) String field,
                                     final @RequestParam(name = "location", required = false) String location,
                                     final HttpServletRequest request) {
        request.getSession().setAttribute("devs", resumeSearch.searchForResumeAdvanced(user, allWords, phrase, oneWord, title, company,
                                                                                             experience, place, degree, field, location));
        return "redirect:/resume/results";
    }

    @GetMapping("/preview/{id}")
    public String previewResume(final @PathVariable("id") Long id,
                                final Model model) {
        Developer developer = developerRepo.getOne(id);
        model.addAttribute("developer", developer);
        if (developer.isEnable()) {
            model.addAttribute("enable", "enable");
        }
        return "employee/preview";
    }

    @PostMapping("/offer/{id}")
    public String sendOfferToDeveloper(final @AuthenticationPrincipal User user,
                                       final @PathVariable("id") Long id,
                                       final RedirectAttributes attributes) {
        mailSender.sendOfferToDeveloper(user, id, attributes);
        return "redirect:/resume/preview/" + id;
    }
}
