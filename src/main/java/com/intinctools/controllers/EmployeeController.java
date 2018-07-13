package com.intinctools.controllers;

import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.JobRepo;
import com.intinctools.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final JobRepo jobRepo;
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService, JobRepo jobRepo) {
        this.employeeService = employeeService;
        this.jobRepo = jobRepo;
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
        attribute.addFlashAttribute("message", "You are successfully added address to your account");
        return "redirect:/edit-employee";
    }



    @GetMapping("/employee-job-main")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String addJobForm(@AuthenticationPrincipal User user,
                             Model model){
        model.addAttribute("user" , user);
        return "employee/jobAdd";
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
                         @RequestParam(name = "fullDescription") String fullDescription,
                         RedirectAttributes attribute) {
        employeeService.addJob(user, title,
                               fullDescription, shortDescription,
                               desiredExperience, fromSalary,
                               toSalary, company,
                               jobType, salaryPeriod);
        attribute.addFlashAttribute("message" , "You are successfully added job");
        return "redirect:/employee-job-main";
    }

        @GetMapping("/employee-jobs")
        @PreAuthorize("hasAuthority('EMPLOYEE')")
        public String employeesJobsPage(@AuthenticationPrincipal User user,
                                        Model model){
            model.addAttribute("jobs", jobRepo.findByEmployee(user.getEmployee()));
            return "employee/employeeJobs";
        }

        @GetMapping("/employee-jobs/edit/{user}")
        @PreAuthorize("hasAuthority('EMPLOYEE')")
        public String employeesJobEditPage(@AuthenticationPrincipal User user,
                                           @PathVariable("user") Long id,
                                           Model model){
            if (employeeService.checkEmployeeEditing(user , id)){
                model.addAttribute("job", jobRepo.getById(id));
                return "employee/jobEdit";
            }
            return "redirect:/employee-jobs";
        }

        @PostMapping("/employee-jobs/edit/{user}")
        @PreAuthorize("hasAuthority('EMPLOYEE')")
        public String employeeJobEdit(@AuthenticationPrincipal User user,
                                      @RequestParam(name = "title", required = false) String title,
                                      @RequestParam(name = "company", required = false) String company,
                                      @RequestParam(name = "shortDescription", required = false) String shortDescription,
                                      @RequestParam(name = "fromSalary", required = false) String fromSalary,
                                      @RequestParam(value = "desiredExperience", required = false) String desiredExperience,
                                      @RequestParam(name = "toSalary", required = false) String toSalary,
                                      @RequestParam(name = "salaryPeriod", required = false) String salaryPeriod,
                                      @RequestParam(name = "jobType", required = false) String jobType,
                                      @RequestParam(name = "fullDescription", required = false) String fullDescription,
                                      @PathVariable("user") Long id,
                                      RedirectAttributes attribute){
            employeeService.editJob(user, title, fullDescription, shortDescription, desiredExperience, fromSalary, toSalary, company, jobType, salaryPeriod, id);
            attribute.addFlashAttribute("message" , "You are successfully updated job");
            return "redirect:/employee-jobs/edit/{id}";
        }



}
