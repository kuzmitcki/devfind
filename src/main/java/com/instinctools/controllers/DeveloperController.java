package com.instinctools.controllers;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.developer.DeveloperService;
import com.instinctools.service.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class DeveloperController {
    private final DeveloperService developerService;

    private final MailSender mailSender;

    private final WorkExperienceRepo workExperienceRepo;

    private final EducationRepo educationRepo;

    @Autowired
    public DeveloperController(final DeveloperService developerService, final MailSender mailSender,
                               final WorkExperienceRepo workExperienceRepo, final EducationRepo educationRepo) {
        this.developerService = developerService;
        this.mailSender = mailSender;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
    }

    @GetMapping("resume/wizard/profile")
    public String resumeBasicsPage(final @AuthenticationPrincipal User user,
                                   final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        return "developer/resume/profile";
    }

    @PostMapping("/resume/wizard/profile")
    public String resumeBasicsAdd(final @AuthenticationPrincipal User user,
                                  final @RequestParam(name = "firstName") String firstName,
                                  final @RequestParam(name = "lastName")  String lastName,
                                  final @RequestParam(name = "country") String country,
                                  final @RequestParam(name = "city", required = false) String city,
                                  final @RequestParam(name = "zipPostal", required = false) String zipPostal,
                                  final @RequestParam(name = "telephone", required = false) String telephone) {
        developerService.setBasicQualities(user, firstName, lastName, country, city, zipPostal, telephone);
        return "redirect:/resume/wizard/education";
    }

    @PostMapping("edit-developer/summary")
    public String summary(final @AuthenticationPrincipal User user,
                          final @RequestParam(name = "summary") String summary) {
        developerService.setDeveloperSummary(user, summary);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/additional")
    public String additionalInformation(final @AuthenticationPrincipal  User user,
                                        final @RequestParam(name = "additional") String additional) {
        developerService.setDeveloperAdditional(user, additional);
        return "redirect:/developer/resume";
    }

    @PostMapping("/edit-developer/information")
    public String editContactInformation(final @AuthenticationPrincipal User user,
                                         final @RequestParam(name = "country") String country,
                                         final @RequestParam(name = "city") String city,
                                         final @RequestParam(name = "telephone") String telephone,
                                         final @RequestParam(name = "zipPostalCode") String zipPostalCode,
                                         final @RequestParam(name = "email") String email,
                                         final HttpServletRequest request) {
        developerService.editResumeBasicInformation(user, country, city, telephone, zipPostalCode, email, request);
        return "redirect:/developer/resume";
    }

    @GetMapping("resume/wizard/education")
    public String resumeEducationPage(@AuthenticationPrincipal  User user,
                                      final Model model) {
        model.addAttribute("education", user.getDeveloper().getEducation().isEmpty());
        return "developer/resume/education";
    }

    @PostMapping("resume/wizard/education")
    public String resumeEducation(final @AuthenticationPrincipal User user,
                                  final @RequestParam(name = "degree") String degree,
                                  final @RequestParam(name = "place") String place,
                                  final @RequestParam(name = "fieldOfStudy") String field,
                                  final @RequestParam(name = "city") String city,
                                  final @RequestParam(name = "monthFrom") String monthFrom,
                                  final @RequestParam(name = "monthTo") String monthTo,
                                  final @RequestParam(name = "yearFrom") String yearFrom,
                                  final @RequestParam(name = "yearTo") String yearTo) {
        if (!user.getDeveloper().getWorkExperiences().isEmpty()) {
            developerService.setEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo);
            return "redirect:/developer/resume";
        }
        developerService.setEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo);
        return   "redirect:/resume/wizard/experience";
    }

    @GetMapping("edit-developer/education/{id}")
    public String educationEditPage(final @AuthenticationPrincipal User user,
                                    final @PathVariable(name = "id") Long id,
                                    final Model model) {
        if (!developerService.checkDeveloperEditingEducation(user, id)) {
            return "redirect:/developer/resume";
        }
        model.addAttribute("educ", educationRepo.getOne(id));
        return "developer/edit/education";
    }

    @PostMapping("edit-developer/education/{id}")
    public String educationEdit(final @AuthenticationPrincipal User user,
                                final @RequestParam(name = "degree") String degree,
                                final @RequestParam(name = "place") String place,
                                final @RequestParam(name = "fieldOfStudy") String field,
                                final @RequestParam(name = "city") String city,
                                final @RequestParam(name = "monthFrom") String monthFrom,
                                final @RequestParam(name = "monthTo") String monthTo,
                                final @RequestParam(name = "yearFrom") String yearFrom,
                                final @RequestParam(name = "yearTo")  String yearTo,
                                final @PathVariable("id") Long id) {
        developerService.setDeveloperEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo, id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/education-delete/{id}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String  deleteEducation(final @AuthenticationPrincipal User user,
                                   final @PathVariable(name = "id") Long id) {
        developerService.deleteDeveloperEducation(user, id);
        return "redirect:/developer/resume";
    }

    @GetMapping("resume/wizard/experience")
    public String resumeExperiencePage() {
        return "developer/resume/experience";
    }

    @PostMapping("resume/wizard/experience")
    public String resumeExperience(final @AuthenticationPrincipal User user,
                                   final @RequestParam(name = "jobTitle") String jobTitle,
                                   final @RequestParam(name = "company") String company,
                                   final @RequestParam(name = "city") String city,
                                   final @RequestParam(name = "monthFrom") String monthFrom,
                                   final @RequestParam(name = "monthTo") String monthTo,
                                   final @RequestParam(name = "yearFrom") String yearFrom,
                                   final @RequestParam(name = "yearTo") String yearTo,
                                   final @RequestParam(name = "description") String description,
                                   final @RequestParam(name = "check", required = false) String check) {
        developerService.setWorkExperience(user, jobTitle, company, city, monthFrom, monthTo, yearFrom, yearTo, description, check);
        return "redirect:/developer/resume";
    }

    @PostMapping("/edit-developer/experience/{id}")
    public String workExperienceEdit(final @AuthenticationPrincipal User user,
                                     final @PathVariable("id") Long id,
                                     final @RequestParam(name = "jobTitle") String jobTitle,
                                     final @RequestParam(name = "company") String company,
                                     final @RequestParam(name = "city") String city,
                                     final @RequestParam(name = "monthFrom") String monthFrom,
                                     final @RequestParam(name = "monthTo") String monthTo,
                                     final @RequestParam(name = "yearFrom") String yearFrom,
                                     final @RequestParam(name = "yearTo") String yearTo,
                                     final @RequestParam(name = "description") String description) {
        developerService.editDeveloperWorkExperience(user, id, jobTitle, company, city, monthFrom, monthTo, yearFrom, yearTo, description);
        return "redirect:/developer/resume";
    }

    @GetMapping("/edit-developer/experience/{id}")
    public String workExperienceEditPage(final @AuthenticationPrincipal User user,
                                         final @PathVariable(name = "id") Long id,
                                         final Model model) {
        if (!developerService.checkDeveloperEditingWork(user, id)) {
            return "redirect:/developer/resume";
        }
        model.addAttribute("work", workExperienceRepo.getById(id));
        return "developer/edit/experience";
    }

    @PostMapping("edit-developer/work-delete/{id}")
    public String  deleteWork(final @AuthenticationPrincipal User user,
                              final @PathVariable(name = "id") Long id) {
        developerService.deleteDeveloperWork(user, id);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/resume")
    public String profilePage(final @AuthenticationPrincipal User user,
                              final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        model.addAttribute("telephone", user.getDeveloper().getTelephone());
        return "developer/resume/resume";
    }

    @PostMapping("edit-developer/skills")
    public String skills(final @AuthenticationPrincipal User user,
                         final @RequestParam(name = "skill") String skill,
                         final @RequestParam(name = "experience") String year) {
        developerService.setDeveloperSkill(user, skill, year);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skill-delete/{id}")
    public String deleteSkill(final @AuthenticationPrincipal User user,
                              final @PathVariable("id") Long id) {
        developerService.deleteDeveloperSkill(user, id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/desired")
    public String editDesiredJob(final @AuthenticationPrincipal User user,
                                 final @RequestParam(name = "title") String title,
                                 final @RequestParam(name = "salary") String salary,
                                 final @RequestParam(name = "jobType") String jobType,
                                 final @RequestParam(name = "salaryPeriod") String salaryPeriod) {
        developerService.setDesiredJob(user, title, salary, jobType, salaryPeriod);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/job-offer")
    public String offerSaving(final @AuthenticationPrincipal User user) {
        mailSender.getOfferFromEmployee(user);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/resume/preview")
    public String previewResume(final @AuthenticationPrincipal User user,
                                final Model model) {
        model.addAttribute("developer", user.getDeveloper());
        return "developer/preview/preview";
    }

}
