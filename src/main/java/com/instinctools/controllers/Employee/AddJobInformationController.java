package com.instinctools.controllers.Employee;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.empEntites.dto.JobDTO;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.employee.adding.AddingJob;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class AddJobInformationController {

    private final AddingJob addingJob;

    public AddJobInformationController(AddingJob addingJob) {
        this.addingJob = addingJob;
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
        addingJob.setEmployeeAccountInformation(user, company, name, phone, email);
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
        addingJob.setEmployeeBasicInformation(user, redirectedJob, new JobDTO(jobTitle,jobLocation, country), company);
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
        addingJob.setEmployeeJobSalary(jobType, fromSalary, toSalary, salaryPeriod, qualifications, redirectJob, request);
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
        addingJob.setEmployeeDescription(user, desiredExperience, fullDescription, request);
        return "redirect:/employee/jobs";
    }
}
