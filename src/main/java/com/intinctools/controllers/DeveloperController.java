package com.intinctools.controllers;

import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.developerRepo.EducationRepo;
import com.intinctools.repo.developerRepo.WorkExperienceRepo;
import com.intinctools.service.developer.DeveloperService;
import com.intinctools.service.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String resumeBasicsPage(@AuthenticationPrincipal User user,
                                   Model model){
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        return "developer/resume/profile";
    }


    @PostMapping("/resume/wizard/profile")
    public String resumeBasicsAdd(@AuthenticationPrincipal User user,
                                  @RequestParam(name = "firstName") String firstName,
                                  @RequestParam(name = "lastName")  String lastName,
                                  @RequestParam("country") String country,
                                  @RequestParam(value = "city", required = false) String city,
                                  @RequestParam(value = "zipPostal", required = false) String zipPostal,
                                  @RequestParam(value = "telephone", required = false) String telephone){
        developerService.setBasicQualities(user, firstName, lastName, country, city, zipPostal, telephone);
        return "redirect:/resume/wizard/education";
    }

    @PostMapping("edit-developer/summary")
    public String summary(@AuthenticationPrincipal User user,
                          @RequestParam("summary") String summary){
        developerService.setDeveloperSummary(user, summary);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/additional")
    public String additionalInformation(@AuthenticationPrincipal User user,
                                        @RequestParam("additional") String additional){
        developerService.setDeveloperAdditional(user, additional);
        return "redirect:/developer/resume";
    }

    @PostMapping("/edit-developer/information")
    public String editContactInformation(@AuthenticationPrincipal User user,
                                         @RequestParam(name = "country") String country,
                                         @RequestParam(name = "city") String city,
                                         @RequestParam(name = "telephone") String telephone,
                                         @RequestParam(name = "zipPostalCode") String zipPostalCode,
                                         @RequestParam(name = "email") String email){
        developerService.editResumeBasicInformation(user, country, city, telephone, zipPostalCode, email);
        return "redirect:/developer/resume";
    }

    @GetMapping("resume/wizard/education")
    public String resumeEducationPage(@AuthenticationPrincipal User user,
                                      Model model){
        model.addAttribute("education", user.getDeveloper().getEducation().isEmpty());
        return "developer/resume/education";
    }

    @PostMapping("resume/wizard/education")
    public String resumeEducation(@AuthenticationPrincipal User user,
                                  @RequestParam(name = "degree") String degree,
                                  @RequestParam(name = "place") String place,
                                  @RequestParam(name = "fieldOfStudy") String field,
                                  @RequestParam(name = "city") String city,
                                  @RequestParam(name = "monthFrom") String monthFrom,
                                  @RequestParam(name = "monthTo") String monthTo,
                                  @RequestParam(name = "yearFrom") String yearFrom,
                                  @RequestParam(name = "yearTo") String yearTo){
        if (!user.getDeveloper().getWorkExperiences().isEmpty()){
            developerService.setEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo);
            return "redirect:/developer/resume";
        }
        developerService.setEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo);
        return   "redirect:/resume/wizard/experience";
    }

    @GetMapping("edit-developer/education/{id}")
    public String educationEditPage(@AuthenticationPrincipal User user,
                                    @PathVariable("id") Long id,
                                    Model model){
        if (!developerService.checkDeveloperEditingEducation(user, id)){
            return "redirect:/developer/resume";
        }
        model.addAttribute("educ", educationRepo.getOne(id));
        return "developer/edit/education";
    }

    @PostMapping("edit-developer/education/{id}")
    public String educationEdit(@AuthenticationPrincipal User user,
                                @RequestParam(name = "degree") String degree,
                                @RequestParam(name = "place") String place,
                                @RequestParam(name = "fieldOfStudy") String field,
                                @RequestParam(name = "city") String city,
                                @RequestParam(name = "monthFrom") String monthFrom,
                                @RequestParam(name = "monthTo") String monthTo,
                                @RequestParam(name = "yearFrom") String yearFrom,
                                @RequestParam(name = "yearTo") String yearTo,
                                @PathVariable("id") Long id){
        developerService.setDeveloperEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo, id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/education-delete/{id}")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String  deleteEducation(@AuthenticationPrincipal User user,
                                   @PathVariable("id") Long id){
        developerService.deleteDeveloperEducation(user, id);
        return "redirect:/developer/resume";
    }

    @GetMapping("resume/wizard/experience")
    public String resumeExperiencePage(){
        return "developer/resume/experience";
    }

    @PostMapping("resume/wizard/experience")
    public String resumeExperience(@AuthenticationPrincipal User user,
                                   @RequestParam(name = "jobTitle") String jobTitle,
                                   @RequestParam(name ="company") String company,
                                   @RequestParam(name = "city") String city,
                                   @RequestParam(name = "monthFrom") String monthFrom,
                                   @RequestParam(name = "monthTo") String monthTo,
                                   @RequestParam(name = "yearFrom") String yearFrom,
                                   @RequestParam(name = "yearTo") String yearTo,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "check", required = false) String check){
        developerService.setWorkExperience(user, jobTitle, company, city, monthFrom, monthTo, yearFrom, yearTo, description, check);
        return "redirect:/developer/resume";
    }

    @PostMapping("/edit-developer/experience/{id}")
    public String workExperienceEdit(@AuthenticationPrincipal User user,
                                     @PathVariable("id") Long id,
                                     @RequestParam(name = "jobTitle") String jobTitle,
                                     @RequestParam(name ="company") String company,
                                     @RequestParam(name = "city") String city,
                                     @RequestParam(name = "monthFrom") String monthFrom,
                                     @RequestParam(name = "monthTo") String monthTo,
                                     @RequestParam(name = "yearFrom") String yearFrom,
                                     @RequestParam(name = "yearTo") String yearTo,
                                     @RequestParam(name = "description") String description){
        developerService.editDeveloperWorkExperience(user, id, jobTitle, company, city, monthFrom, monthTo, yearFrom, yearTo, description);
        return "redirect:/developer/resume";
    }

    @GetMapping("/edit-developer/experience/{id}")
    public String workExperienceEditPage(@AuthenticationPrincipal User user,
                                         @PathVariable("id") Long id,
                                         Model model){
        if (!developerService.checkDeveloperEditingWork(user, id)){
            return "redirect:/developer/resume";
        }
        model.addAttribute("work", workExperienceRepo.getById(id));
        return "developer/edit/experience";
    }

    @PostMapping("edit-developer/work-delete/{id}")
    public String  deleteWork(@AuthenticationPrincipal User user,
                              @PathVariable("id") Long id){
        developerService.deleteDeveloperWork(user, id);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/resume")
    public String profilePage(@AuthenticationPrincipal User user,
                              Model model){
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        model.addAttribute("telephone", user.getDeveloper().getTelephone());
        return "developer/resume/resume";
    }

    @PostMapping("edit-developer/skills")
    public String skills(@AuthenticationPrincipal User user,
                         @RequestParam(value = "skill") String skill,
                         @RequestParam(value = "experience") String year){
        developerService.setDeveloperSkill(user, skill, year);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skill-delete/{id}")
    public String deleteSkill(@AuthenticationPrincipal User user,
                              @PathVariable("id") Long id){
        developerService.deleteDeveloperSkill(user, id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/desired")
    public String editDesiredJob(@AuthenticationPrincipal User user,
                                 @RequestParam("title") String title,
                                 @RequestParam("salary") String salary,
                                 @RequestParam("jobType") String jobType,
                                 @RequestParam("salaryPeriod") String salaryPeriod){
       developerService.setDesiredJob(user, title, salary, jobType, salaryPeriod);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/job-offer")
    public String offerSaving(@AuthenticationPrincipal User user){
        mailSender.getOfferFromEmployee(user);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/resume/preview")
    public String previewResume(@AuthenticationPrincipal User user,
                                Model model){
        model.addAttribute("developer", user.getDeveloper());
        return "developer/preview/preview";
    }

}
