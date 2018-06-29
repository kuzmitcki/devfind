package com.intinctools.controllers;

import com.intinctools.entities.User;
import com.intinctools.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String registrationPage(){
        return "employeeRegistration";
    }

    @PostMapping("/employee")
    public String employeeRegistration(User user, Model model){
        if (employeeService.saveEmployee(user)){
            return "redirect:/login";
        }
        model.addAttribute("message" , "User already exits");
        return "employeeRegistration";
    }


}
