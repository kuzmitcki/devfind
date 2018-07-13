package com.intinctools.controllers;


import com.intinctools.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/resume")
public class SearchForDeveloperController {

    private final EmployeeService employeeService;

    private final Logger logger = LoggerFactory.getLogger(SearchForDeveloperController.class);

    public SearchForDeveloperController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String resumePage() {
        return "employee/searchResume";
    }

    @GetMapping("/advanced")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String advancedResumePage(){
        logger.info(String.valueOf(employeeService.findDevelopersBySkills("java swift")));
        return "employee/advancedSearch";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String findResume(@RequestParam(name = "whatDescription", required = false) String specializations,
                             @RequestParam(name = "whereDescription", required = false) String jobLocation,
                             Model model){
        model.addAttribute("developers", employeeService.findBySkillsWithAddress(specializations, jobLocation));
        return "employee/advancedSearch";
    }

    @PostMapping("/advanced")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String findResumeAdvanced(){
        return "employee/advancedSearch";
    }

}
