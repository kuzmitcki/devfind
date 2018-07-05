package com.intinctools.controllers;

import com.intinctools.entities.userEntites.User;
import com.intinctools.service.developer.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(final DeveloperService developerService) {
        this.developerService = developerService;

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
            return "redirect:/developer";
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
                           @RequestParam(value = "firstName", required = false) String firstName,
                           @RequestParam(value = "lastName",  required = false)  String lastName,
                           @RequestParam(value = "company",  required = false)  String company,
                           @RequestParam(value = "email",     required = false) String email,
                           @RequestParam(value = "skill",     required = false) String skill,
                           @RequestParam(value = "birthday",  required = false) String date,
                           @RequestParam(value = "gender",    required = false) String gender,
                           RedirectAttributes attribute) {
        developerService.editDeveloper(user, firstName, lastName, company, email, date, gender);
        developerService.editSpecialization(user, skill);
        attribute.addFlashAttribute("message", "You are successfully updated your account");
        return "redirect:/edit";
    }






}
