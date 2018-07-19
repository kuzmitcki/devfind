package com.intinctools.controllers;

import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;
import com.intinctools.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String employeeForm() {
        return "employee/registration/employeeRegistration";
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

    @GetMapping("job/wizard/information")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String employeeAccountInformationPage(@AuthenticationPrincipal User user,
                                                 Model model){
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/accountInformation";
    }

    @PostMapping("job/wizard/information")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String employeeAccountInformation(@AuthenticationPrincipal User user,
                                             @RequestParam(name = "company") String company,
                                             @RequestParam(name = "name")    String name,
                                             @RequestParam(name = "phone", required = false)   String phone,
                                             @RequestParam(name = "email", required = false)   String email) {
        employeeService.setEmployeeAccountInformation(user, company, name, phone, email);
        return "redirect:/job/wizard/job-description/basic";
    }

    @GetMapping("job/wizard/job-description/basic")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescriptionBasicPage(@AuthenticationPrincipal User user,
                                          Model model,
                                          @ModelAttribute("message") String message){
        Employee employee = user.getEmployee();
        model.addAttribute("message", message);
        model.addAttribute("employee", employee);
        return "employee/job/startJobDescription";
    }

    @PostMapping("job/wizard/job-description/basic")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescriptionBasic(@AuthenticationPrincipal User user,
                                      RedirectAttributes redirectedJob,
                                      @RequestParam("jobTitle")    String jobTitle,
                                      @RequestParam("company")     String company,
                                      @RequestParam("jobLocation") String jobLocation,
                                      @RequestParam("country")     String country){
        employeeService.setEmployeeBasicInformation(user, redirectedJob, jobTitle, company, jobLocation, country);
        return "redirect:/job/wizard/job-description/details";
    }

    @GetMapping("job/wizard/job-description/details")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescriptionDetailsPage(@AuthenticationPrincipal User user,
                                            @ModelAttribute("job") Job job,
                                            RedirectAttributes attribute,
                                            HttpServletRequest redirect,
                                            Model model){
        if (job.getTitle() == null){
            attribute.addFlashAttribute("message", "Please fill this form");
            return "redirect:/job/wizard/job-description/basic";
        }
        redirect.getSession().setAttribute("attribute", job);
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/jobDetails";
    }

    @PostMapping("job/wizard/job-description/details")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescriptionDetails(@RequestParam("jobType")        String jobType,
                                        @RequestParam("fromSalary")     String fromSalary,
                                        @RequestParam("toSalary")       String toSalary,
                                        @RequestParam("salaryPeriod")   String salaryPeriod,
                                        @RequestParam("qualifications") String qualifications,
                                        RedirectAttributes redirectJob,
                                        HttpServletRequest request) {
        employeeService.setEmployeeJobSalary(jobType, fromSalary, toSalary, salaryPeriod, qualifications, redirectJob, request);
        return "redirect:/job/wizard/description";
    }

    @GetMapping("job/wizard/description")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescriptionPage(@AuthenticationPrincipal User user,
                                     @ModelAttribute("job") Job job,
                                     HttpServletRequest request,
                                     RedirectAttributes attribute,
                                     Model model){
        if (job.getTitle() == null){
            attribute.addFlashAttribute("message", "Please fill this form");
            return "redirect:/job/wizard/job-description/basic";
        }
        request.getSession().setAttribute("job", job);
        model.addAttribute("job", job);
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/description";
    }

    @PostMapping("job/wizard/description")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobDescription(@AuthenticationPrincipal User user,
                                 @RequestParam("desiredExperience") String desiredExperience,
                                 @RequestParam("fullDescription")   String fullDescription,
                                 HttpServletRequest request){
        employeeService.setEmployeeDescription(user, desiredExperience, fullDescription, request);
        return "redirect:/employee/jobs";
    }

    @GetMapping("employee/jobs")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String jobsPage(@AuthenticationPrincipal User user,
                           Model model){
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/jobs";
    }

    @PostMapping("edit-employee/company")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editCompany(@AuthenticationPrincipal User user,
                              @RequestParam("company") String company){
        employeeService.editEmployeeCompany(user, company);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/title/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editJobTitle(@AuthenticationPrincipal User user,
                               @RequestParam("title") String title,
                               @PathVariable("id") Long id){
        if (employeeService.checkEmployeeEditing(user, id)){
            return "redirect:/employee/jobs";
        }
        employeeService.editJobTitle(user, title, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/location/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editJobLocation(@AuthenticationPrincipal  User user,
                                  @RequestParam("country")  String country,
                                  @RequestParam("location") String location,
                                  @PathVariable("id") Long id){
        if (employeeService.checkEmployeeEditing(user, id)){
            return "redirect:/employee/jobs";
        }
        employeeService.editJobLocation(user, country, location, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/description/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editJobDescription(@AuthenticationPrincipal  User user,
                                     @RequestParam("description")  String description,
                                     @PathVariable("id") Long id){
        if (employeeService.checkEmployeeEditing(user, id)){
            return "redirect:/employee/jobs";
        }
        employeeService.editJobDescription(user, description, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/experience/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editJobDesiredExperience(@AuthenticationPrincipal  User user,
                                           @RequestParam("experience")  String experience,
                                           @PathVariable("id") Long id){
        if (employeeService.checkEmployeeEditing(user, id)){
            return "redirect:/employee/jobs";
        }
        employeeService.editJobDesiredDescription(user, experience, id);
        return "redirect:/employee/jobs";
    }


    @PostMapping("/edit-employee/job/qualification/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public String editJobQualification(@AuthenticationPrincipal  User user,
                                       @RequestParam("qualification")  String qualification,
                                       @PathVariable("id") Long id){
        if (employeeService.checkEmployeeEditing(user, id)){
            return "redirect:/employee/jobs";
        }
        employeeService.editJobQualification(user, qualification, id);
        return "redirect:/employee/jobs";
    }


}
