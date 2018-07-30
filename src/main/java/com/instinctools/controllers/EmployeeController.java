package com.instinctools.controllers;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    @GetMapping("job/wizard/information")
    public String employeeAccountInformationPage(final @AuthenticationPrincipal User user,
                                                 final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/accountInformation";
    }

    @PostMapping("job/wizard/information")
    public String employeeAccountInformation(final @AuthenticationPrincipal User user,
                                             final @RequestParam(name = "company") String company,
                                             final @RequestParam(name = "name") String name,
                                             final @RequestParam(name = "phone", required = false) String phone,
                                             final @RequestParam(name = "email", required = false) String email) {
        employeeService.setEmployeeAccountInformation(user, company, name, phone, email);
        return "redirect:/job/wizard/job-description/basic";
    }

    @GetMapping("job/wizard/job-description/basic")
    public String jobDescriptionBasicPage(final @AuthenticationPrincipal User user,
                                          final Model model,
                                          final @ModelAttribute(name = "message") String message) {
        Employee employee = user.getEmployee();
        model.addAttribute("message", message);
        model.addAttribute("employee", employee);
        return "employee/job/startJobDescription";
    }

    @PostMapping("job/wizard/job-description/basic")
    public String jobDescriptionBasic(final @AuthenticationPrincipal User user,
                                      final RedirectAttributes redirectedJob,
                                      final @RequestParam(name = "jobTitle") String jobTitle,
                                      final @RequestParam(name = "company") String company,
                                      final @RequestParam(name = "jobLocation") String jobLocation,
                                      final @RequestParam(name = "country") String country) {
        employeeService.setEmployeeBasicInformation(user, redirectedJob, jobTitle, company, jobLocation, country);
        return "redirect:/job/wizard/job-description/details";
    }

    @GetMapping("job/wizard/job-description/details")
    public String jobDescriptionDetailsPage(final @AuthenticationPrincipal User user,
                                            final @ModelAttribute(name = "job") Job job,
                                            final RedirectAttributes attribute,
                                            final HttpServletRequest redirect,
                                            final Model model) {
        if (job.getTitle() == null) {
            attribute.addFlashAttribute("message", "Please fill this form");
            return "redirect:/job/wizard/job-description/basic";
        }
        redirect.getSession().setAttribute("attribute", job);
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/jobDetails";
    }

    @PostMapping("job/wizard/job-description/details")
    public String jobDescriptionDetails(final @RequestParam(name = "jobType") String jobType,
                                        final @RequestParam(name = "fromSalary") String fromSalary,
                                        final @RequestParam(name = "toSalary") String toSalary,
                                        final @RequestParam(name = "salaryPeriod") Long salaryPeriod,
                                        final @RequestParam(name = "qualifications") String qualifications,
                                        final RedirectAttributes redirectJob,
                                        final HttpServletRequest request) {
        employeeService.setEmployeeJobSalary(jobType, fromSalary, toSalary, salaryPeriod, qualifications, redirectJob, request);
        return "redirect:/job/wizard/description";
    }

    @GetMapping("job/wizard/description")
    public String jobDescriptionPage(final @AuthenticationPrincipal User user,
                                     final @ModelAttribute(name = "job") Job job,
                                     final HttpServletRequest request,
                                     final RedirectAttributes attribute,
                                     final Model model) {
        if (job.getTitle() == null) {
            attribute.addFlashAttribute("message", "Please fill this form");
            return "redirect:/job/wizard/job-description/basic";
        }
        request.getSession().setAttribute("job", job);
        model.addAttribute("job", job);
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/description";
    }

    @PostMapping("job/wizard/description")
    public String jobDescription(final @AuthenticationPrincipal User user,
                                 final @RequestParam(name = "desiredExperience") String desiredExperience,
                                 final @RequestParam(name = "fullDescription") String fullDescription,
                                 final HttpServletRequest request) {
        employeeService.setEmployeeDescription(user, desiredExperience, fullDescription, request);
        return "redirect:/employee/jobs";
    }

    @GetMapping("employee/jobs")
    public String jobsPage(final @AuthenticationPrincipal User user,
                           final Model model) {
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/jobs";
    }

    @PostMapping("edit-employee/company")
    public String editCompany(final @AuthenticationPrincipal User user,
                              final @RequestParam(name = "company") String company) {
        employeeService.editEmployeeCompany(user, company);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/title/{id}")
    public String editJobTitle(final @AuthenticationPrincipal User user,
                               final @RequestParam(name = "title") String title,
                               final @PathVariable(name = "id") Long id) {
        if (employeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        employeeService.editJobTitle(user, title, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/location/{id}")
    public String editJobLocation(final @AuthenticationPrincipal User user,
                                  final @RequestParam(name = "country") String country,
                                  final @RequestParam(name = "location") String location,
                                  final @PathVariable(name = "id") Long id) {
        if (employeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        employeeService.editJobLocation(user, country, location, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/description/{id}")
    public String editJobDescription(final @AuthenticationPrincipal User user,
                                     final @RequestParam(name = "description") String description,
                                     final @PathVariable(name = "id") Long id) {
        if (employeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        employeeService.editJobDescription(user, description, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/experience/{id}")
    public String editJobDesiredExperience(final @AuthenticationPrincipal User user,
                                           final @RequestParam(name = "experience") String experience,
                                           final @PathVariable(name = "id") Long id) {
        if (employeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        employeeService.editJobDesiredDescription(user, experience, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/qualification/{id}")
    public String editJobQualification(final @AuthenticationPrincipal User user,
                                       final @RequestParam(name = "qualification") String qualification,
                                       final @PathVariable(name = "id") Long id) {
        if (employeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        employeeService.editJobQualification(user, qualification, id);
        return "redirect:/employee/jobs";
    }
}
