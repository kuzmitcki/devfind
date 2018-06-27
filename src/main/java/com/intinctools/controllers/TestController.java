package com.intinctools.controllers;


import com.intinctools.entities.Developer;
import com.intinctools.entities.Employee;
import com.intinctools.repo.DevRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
public class TestController {


    private final DevRepo devRepo;
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(DevRepo devRepo) {
        this.devRepo = devRepo;
    }

    @GetMapping
    @ResponseBody
    public String get(){

        return "hello";
    }

}
