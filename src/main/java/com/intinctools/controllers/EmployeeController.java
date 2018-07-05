package com.intinctools.controllers;

import com.intinctools.entities.userEntites.User;
import com.intinctools.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String employeeForm() {
        return "employee/employeeRegistration";
    }

    @PostMapping("/employee")
    public String saveEmployee(User user,
                                @RequestParam("email") String email,
                                RedirectAttributes attribute) {
        if (!employeeService.saveEmployee(user, email)) {
            return "redirect:/employee";
        }
        attribute.addFlashAttribute("message", "User already exists");
        return "redirect:/employee";
    }


    @GetMapping("/edit-employee")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editForm(@AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("user", user);
        return "employee/editEmployeeProfile";
    }


    @PostMapping("/edit-employee")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editEmployee(@AuthenticationPrincipal User user,
                               @RequestParam(name = "country", required = false)String country,
                               @RequestParam(name = "street", required = false) String street,
                               @RequestParam(name = "state", required = false) String state,
                               @RequestParam(name = "zipPostalCode", required = false) String zipPostalCode,
                               @RequestParam(name = "telephone", required = false) String telephone,
                               RedirectAttributes attribute) {
        employeeService.editEmployee(user, country, state, street, zipPostalCode, telephone);
        attribute.addFlashAttribute("message", "You are successfully updated your account");
        return "redirect:/editEmp";
    }



    @GetMapping("/employee-job-main")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String addJobForm() {
        return "employee/jobEdit";
    }


    @PostMapping("/employee-job-main")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String addJob(@AuthenticationPrincipal User user,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "company") String company,
                         @RequestParam(name = "shortDescription") String shortDescription,
                         @RequestParam(name = "fromSalary") Long fromSalary,
                         @RequestParam(value = "desiredExperience") String desiredExperience,
                         @RequestParam(name = "toSalary") Long toSalary,
                         @RequestParam(name = "salaryPeriod") String salaryPeriod,
                         @RequestParam(name = "jobType") String jobType,
                         @RequestParam(name = "fullDescription") String fullDescription) {
        employeeService.addJob(user, title,
                               fullDescription, shortDescription,
                               desiredExperience, fromSalary,
                               toSalary, company,
                               jobType, salaryPeriod);
        return "employee/jobEdit";
    }







}
