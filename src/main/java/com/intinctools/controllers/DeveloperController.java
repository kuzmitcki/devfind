package com.intinctools.controllers;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.Specialization;
import com.intinctools.entities.devEntities.WorkExperience;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.*;
import com.intinctools.service.developer.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;


@Controller
public class DeveloperController {

    private final DeveloperService developerService;
    private final SpecializationRepo specializationRepo;
    private final UserRepo userRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;

    private final static Logger LOGGER = LoggerFactory.getLogger(DeveloperController.class);

    @Autowired
    public DeveloperController(final DeveloperService developerService, SpecializationRepo specializationRepo, UserRepo userRepo, WorkExperienceRepo workExperienceRepo, EducationRepo educationRepo) {
        this.developerService = developerService;

        this.specializationRepo = specializationRepo;
        this.userRepo = userRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
    }

    @GetMapping("/developer")
    public String developerForm() {
        return "developer/developerRegistration";
    }

    @PostMapping("/developer")
    public String saveDeveloper(User user,
                                @RequestParam("email") String email,
                                RedirectAttributes attribute) {
        if (developerService.saveDeveloper(user, email)) {
            return "redirect:/";
        }
        attribute.addFlashAttribute("message", "User already exists");
        return "redirect:/developer";
    }



    @GetMapping("/edit-developer")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String editForm(@AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("user", user);
        return "developer/editDeveloperProfile";
    }

    @PostMapping("/edit-developer")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String editDeveloper(@AuthenticationPrincipal User user,
                           @RequestParam(value = "firstName",  required = false) String firstName,
                           @RequestParam(value = "lastName",   required = false) String lastName,
                           @RequestParam(value = "company",    required = false) String company,
                           @RequestParam(value = "email",      required = false) String email,
                           @RequestParam(value = "birthday",   required = false) String date,
                           @RequestParam(value = "gender",     required = false) String gender,
                           @RequestParam(value = "experience", required = false) String experience,
                           @RequestParam(value = "education",  required = false) String education,
                           RedirectAttributes attribute) {
        //developerService.editDeveloper(user, firstName, lastName, company, email, date, gender, experience, education);
        attribute.addFlashAttribute("message", "You are successfully updated your account");
        return "redirect:/edit";
    }


    @GetMapping("/edit-developer/specializations")
    public String resumePage(@AuthenticationPrincipal User user,
                             Model model){
        model.addAttribute("specializations", specializationRepo.findByDeveloper(user.getDeveloper()));
        return "developer/resumeAndSpecialization";
    }

    @GetMapping("/resume/edit/{id}")
    public String editDeveloperSpecializationPage(@AuthenticationPrincipal User user,
                                                  @PathVariable(name = "id") Long id,
                                                  Model model){
        LOGGER.info(String.valueOf(id));
        if (developerService.checkDeveloperEditing(user, id)){
            model.addAttribute("specializations", specializationRepo.getById(id));
            return "developer/editSpecialization";
        }
        return "redirect:/edit-developer/specializations";
    }

    @GetMapping("/edit-developer/add")
    public String resumeAddingPage(@AuthenticationPrincipal User user,
                                   Model model){
        model.addAttribute("user", user);
        return "developer/resume";
    }

    @PostMapping("/edit-developer/add")
    public String resumeAdding(@AuthenticationPrincipal User user,
                               @RequestParam(value = "skills", required = false)  String skills,
                               @RequestParam(value = "resume", required = false) String resume,
                               RedirectAttributes attribute){
        developerService.editSpecialization(user, skills);
        attribute.addFlashAttribute("message", "You are successfully added new resume");
        return "redirect:/edit-developer/add";
    }




    @GetMapping("/edit-developer/address")
    public String editAddressForm(@AuthenticationPrincipal User user,
                                  Model model) {
        model.addAttribute("user" , user);
        return "developer/editAddress";
    }


    @PostMapping("/edit-developer/address")
    public String editAddress(@AuthenticationPrincipal User user,
                              @RequestParam(name = "country", required = false)String country,
                              @RequestParam(name = "street", required = false) String street,
                              @RequestParam(name = "state", required = false) String state,
                              @RequestParam(name = "zipPostalCode", required = false) String zipPostalCode,
                              @RequestParam(name = "telephone", required = false) String telephone){
       // developerService.editDeveloperAddress(user, country, state, street, zipPostalCode, telephone);
        return "redirect:/edit-developer/address";
    }

    // _____________________________________________//

    @GetMapping("resume/wizard/profile")
    public String resumeBasicsPage(@AuthenticationPrincipal User user,
                                   Model model){
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
        developerService.setEducation(user, degree, place, field, city, monthFrom, monthTo, yearFrom, yearTo);
        return   "redirect:/resume/wizard/experience";
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


    @GetMapping("developer/resume")
    public String profilePage(@AuthenticationPrincipal User user,
                             Model model){
        model.addAttribute("developer", user.getDeveloper());
        model.addAttribute("telephone", user.getDeveloper().getTelephone());
        return "developer/resume/resume";
    }


    @PostMapping("edit-developer/additional")
    public String additionalInformation(@AuthenticationPrincipal User user,
                                        @RequestParam("additional") String additional){
        Developer developer = user.getDeveloper();
        developer.setAdditionalInformation(additional);
        userRepo.save(user);
        return "redirect:/developer/resume";
    }



    @PostMapping("edit-developer/summary")
    public String summary(@AuthenticationPrincipal User user,
                                        @RequestParam("summary") String summary){
        Developer developer = user.getDeveloper();
        developer.setSummary(summary);
        userRepo.save(user);
        return "redirect:/developer/resume";
    }


    @PostMapping("edit-developer/skills")
    public String skills(@AuthenticationPrincipal User user,
                         @RequestParam(value = "skill") String skill,
                         @RequestParam(value = "experience") String year){
        Set<Specialization> specializations = new HashSet<>(user.getDeveloper().getSpecializations());
        user.getDeveloper().getSpecializations().clear();
        specializations.add(new Specialization(skill, year));
        Developer developer = user.getDeveloper();
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
        return "redirect:/developer/resume";
    }


    @GetMapping("/edit-developer/experience/{id}")
    public String workExperienceEditPage(@AuthenticationPrincipal User user,
                                         @PathVariable("id") Long id,
                                         Model model){
        if (!developerService.checkDeveloperEditing(user, id)){
            return "redirect:/developer/resume";
        }
        LOGGER.info("some");
        model.addAttribute("work", workExperienceRepo.getById(id));
        return "developer/edit/experience";
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
        WorkExperience workExperience = workExperienceRepo.getById(id);
        workExperience.setJobTitle(jobTitle);
        workExperience.setCompany(company);
        workExperience.setCity(city);
        workExperience.setMonthFrom(monthFrom);
        workExperience.setMonthTo(monthTo);
        workExperience.setYearFrom(yearFrom);
        workExperience.setYearTo(yearTo);
        workExperience.setDescription(description);
        Developer developer = workExperience.getDeveloper();
        user.setDeveloper(developer);
        userRepo.save(user);
        return "redirect:/developer/resume";
    }


    @GetMapping("edit-developer/education/{id}")
    public String educationEditPage(@AuthenticationPrincipal User user,
                                    @PathVariable("id") Long id,
                                    Model model){
        if (!developerService.checkDeveloperEditing(user, id)){
            return "redirect:/developer/resume";
        }
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skill-delete/{id}")
    public String deleteSkill(@AuthenticationPrincipal User user,
                               @PathVariable("id") Long id){
        specializationRepo.delete(specializationRepo.getById(id));
        return "developer/edit/experience";
    }







}
