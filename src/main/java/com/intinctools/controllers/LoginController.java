package com.intinctools.controllers;


import com.intinctools.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    private final UserRepo userRepo;

    @Autowired
    public LoginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping
    public String startPage(Model model){
        model.addAttribute("users" , userRepo.findAll());
        return "welcomePage";
    }



}
