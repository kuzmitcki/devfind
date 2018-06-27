package com.intinctools.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {


    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @GetMapping
    public String startPage(){
        return "welcomePage";
    }
}
