package com.intinctools.controllers;


import com.intinctools.entities.Role;
import com.intinctools.entities.User;
import com.intinctools.entities.WorkDay;
import com.intinctools.repo.SpecializationRepo;
import com.intinctools.repo.UserRepo;
import com.intinctools.repo.WorkDayRepo;
import jdk.nashorn.internal.runtime.Specialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Time;
import java.util.Collections;


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
