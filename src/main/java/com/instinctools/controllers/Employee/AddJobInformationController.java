package com.instinctools.controllers.Employee;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.employee.adding.AddingJobService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class AddJobInformationController {
    private final AddingJobService addingJobService;

    public AddJobInformationController(final AddingJobService addingJobService) {
        this.addingJobService = addingJobService;
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
                                             final UserDto userDto,
                                             final Model model) {
        model.addAttribute("userDto", userDto);
        addingJobService.setEmployeeAccountInformation(user, userDto);
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
                                      final JobDto jobDto,
                                      final Model model) {
        model.addAttribute("jobDto", jobDto);
        addingJobService.setEmployeeBasicInformation(user, redirectedJob, jobDto);
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
    public String jobDescriptionDetails(final RedirectAttributes redirectJob,
                                        final HttpServletRequest request,
                                        final JobDto jobDto,
                                        Model model) {
        model.addAttribute("jobDto", jobDto);
        addingJobService.setEmployeeJobSalary(jobDto, redirectJob, request);
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
                                 final JobDto jobDto,
                                 final Model model,
                                 final HttpServletRequest request) {
        model.addAttribute("jobDto", jobDto);
        addingJobService.setEmployeeDescription(user, jobDto, request);
        return "redirect:/employee/jobs";
    }
}
