package com.intinctools.controllers;

import com.intinctools.entities.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class DeveloperController {


    private final UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public DeveloperController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/developer")
    public String developerRegistration(User user , Model model){
        if (userService.saveDeveloper(user)){
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
    public String informationPage(){
        return "information";
    }


    /*
        fix problem with date
     */
    @PostMapping("/information")
    public String addInformation(@AuthenticationPrincipal User user,
                                 @RequestParam(value = "start" , required = false) int start,
                                 @RequestParam(value = "end",  required = false) int end,
                                 @RequestParam(value = "schedule" , required = false) String schedule,
                                 @RequestParam(value = "skill" ,required = false) String skill){
       if (schedule.equals("")){
           schedule = String.valueOf(user.getWorkDay().getSchedule());
       }
        userService.addInformation(user , skill,  end , start,  Integer.parseInt(schedule));
        return "welcomePage";
    }
}
