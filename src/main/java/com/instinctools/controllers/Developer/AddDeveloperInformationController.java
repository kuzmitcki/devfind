package com.instinctools.controllers.Developer;

import com.instinctools.entities.devEntities.dto.DesiredJobDTO;
import com.instinctools.entities.devEntities.dto.DeveloperDTO;
import com.instinctools.entities.devEntities.dto.EducationDTO;
import com.instinctools.entities.devEntities.dto.WorkExperienceDTO;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.developer.adding.AddDeveloper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class AddDeveloperInformationController {

    private final AddDeveloper addDeveloper;

    public AddDeveloperInformationController(AddDeveloper addDeveloper) {
        this.addDeveloper = addDeveloper;
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
        addDeveloper.setBasicQualities(user, new DeveloperDTO(firstName, lastName, country, city, zipPostal, telephone));
        return "redirect:/resume/wizard/education";
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
            addDeveloper.setEducation(user, new EducationDTO(degree, place, field, city,
                                                             monthFrom, monthTo, yearFrom,
                                                             yearTo, user.getDeveloper()));
            return "redirect:/developer/resume";
        }
        addDeveloper.setEducation(user, new EducationDTO(degree, place, field, city,
                                                         monthFrom, monthTo, yearFrom,
                                                         yearTo, user.getDeveloper()));
        return   "redirect:/resume/wizard/experience";
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
        addDeveloper.setWorkExperience(user, new WorkExperienceDTO(jobTitle, company, city, monthFrom,
                                                                    monthTo, yearFrom, yearTo, description, user.getDeveloper()),
                                       check);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skills")
    public String skills(final @AuthenticationPrincipal User user,
                         final @RequestParam(name = "skill") String skill,
                         final @RequestParam(name = "experience") String year) {
        addDeveloper.setDeveloperSkill(user, skill, year);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/desired")
    public String editDesiredJob(final @AuthenticationPrincipal User user,
                                 final @RequestParam(name = "title") String title,
                                 final @RequestParam(name = "salary") Long salary,
                                 final @RequestParam(name = "jobType") String jobType,
                                 final @RequestParam(name = "salaryPeriod") String salaryPeriod) {
        addDeveloper.setDesiredJob(user, new DesiredJobDTO(title, jobType, salary, salaryPeriod, user.getDeveloper()));
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/summary")
    public String summary(final @AuthenticationPrincipal User user,
                          final @RequestParam(name = "summary") String summary) {
        addDeveloper.setDeveloperSummary(user, summary);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/additional")
    public String additionalInformation(final @AuthenticationPrincipal  User user,
                                        final @RequestParam(name = "additional") String additional) {
        addDeveloper.setDeveloperAdditional(user, additional);
        return "redirect:/developer/resume";
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
        addDeveloper.setDeveloperEducation(user, new EducationDTO(degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo), id);
        return "redirect:/developer/resume";
    }
}

