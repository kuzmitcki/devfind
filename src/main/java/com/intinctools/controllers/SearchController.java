package com.intinctools.controllers;

import com.intinctools.entities.Role;
import com.intinctools.entities.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class SearchController {

    @Autowired
    private  EmployeeService employeeService;

    private final UserRepo userRepo;

    @Autowired
    public SearchController(UserRepo userRepo, EmployeeService employeeService) {
        this.userRepo = userRepo;
        this.employeeService = employeeService;

    }





}
