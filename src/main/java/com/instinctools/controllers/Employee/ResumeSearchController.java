package com.instinctools.controllers.Employee;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.DeveloperRepo;
import com.instinctools.service.employee.search.ResumeSearchService;
import com.instinctools.service.mail.MailServiceSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("resume")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class ResumeSearchController {
    private final ResumeSearchService resumeSearchService;

    private final DeveloperRepo developerRepo;

    private final MailServiceSender mailSender;

    public ResumeSearchController(final ResumeSearchService resumeSearchService,
                                  final DeveloperRepo developerRepo,
                                  final MailServiceSender mailSender) {
        this.resumeSearchService = resumeSearchService;
        this.developerRepo = developerRepo;
        this.mailSender = mailSender;
    }

    @GetMapping("/search")
    public String findResumePage() {
        return "employee/search/usual";
    }

    @PostMapping("/search")
    public String findResume(final SearchDto searchDto,
                             final Model model,
                             final HttpServletRequest request) {
        model.addAttribute("searchDto", searchDto);
        request.getSession().setAttribute("devs", resumeSearchService.searchForResume(searchDto));
        return "redirect:/resume/results";
    }

    @GetMapping("/results")
    @SuppressWarnings("unchecked")
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
                                     final @ModelAttribute SearchDto searchDto,
                                     final HttpServletRequest request,
                                     final Model model) {
        model.addAttribute("searchDto", searchDto);
        request.getSession().setAttribute("devs", resumeSearchService.searchForResumeAdvanced(user, searchDto));
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
