package com.intinctools.controllers;

import com.intinctools.entities.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DeveloperController {


    private final DeveloperService developerService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }


    @PostMapping("/developer")
    public String developerRegistration(User user , Model model){
        if (developerService.saveDeveloper(user)){
            return "redirect:/login";
        }
        model.addAttribute("message" , "User already exits");
        return "developerRegistration";
    }

    @GetMapping("/developer")
    public String registrationPage(){
        return "developerRegistration";
    }


    @GetMapping("/information")
    @PreAuthorize("hasAuthority(\"DEVELOPER\")")
    public String informationPage(@AuthenticationPrincipal User user,
                                  Model model){
        model.addAttribute("user" , user);
        return "information";
    }


    @PostMapping("/information")
    @PreAuthorize("hasAuthority('DEVELOPER')")
    public String addInformation(@AuthenticationPrincipal User user,
                                 @RequestParam(value = "start" , required = false) String start,
                                 @RequestParam(value = "end",  required = false) String end,
                                 @RequestParam(value = "schedule" , required = false) String schedule,
                                 @RequestParam(value = "skill" ,required = false) String skill){
        developerService.addInformation(user , skill,  end , start, schedule);
        return "welcomePage";
    }
}
