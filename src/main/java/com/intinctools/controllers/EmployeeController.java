package com.intinctools.controllers;

import com.intinctools.entities.User;
import com.intinctools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final UserService userService;

    @Autowired
    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/employee")
    public String registrationPage(){
        return "employeeRegistration";
    }

    @PostMapping("/employee")
    public String employeeRegistration(User user, Model model){
        if (userService.saveEmployee(user)){
            return "redirect:/login";
        }
        model.addAttribute("message" , "User already exits");
        return "employeeRegistration";
    }


}
