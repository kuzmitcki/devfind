package com.intinctools.controllers;


import com.intinctools.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


@Controller
public class LoginController {

    private final UserRepo employeeRepo;

    @Autowired
    public LoginController(UserRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping
    public String startPage(Model model) {
        model.addAttribute("users", employeeRepo.findAll());
        return "welcomePage";
    }

}
