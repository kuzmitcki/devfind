package com.instinctools.controllers.Employee;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.employee.check.CheckEmployeeService;
import com.instinctools.service.employee.edit.EditJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class EditEmployeeController {
    private final EditJobService editJobService;
    private final CheckEmployeeService checkEmployeeService;
    private final JobRepo jobRepo;
    private final Logger l = LoggerFactory.getLogger(EditEmployeeController.class);

    public EditEmployeeController(final EditJobService editJobService,
                                  final CheckEmployeeService checkEmployeeService,
                                  final JobRepo jobRepo) {
        this.editJobService = editJobService;
        this.checkEmployeeService = checkEmployeeService;
        this.jobRepo = jobRepo;
    }

    @GetMapping("employee/jobs")
    public String jobsPage(final @AuthenticationPrincipal User user,
                           final Model model) {
        model.addAttribute("employee", user.getEmployee());
        model.addAttribute("jobs", jobRepo.findByEmployee(user.getEmployee()));
        return "employee/job/jobs";
    }

    @PostMapping("edit-employee/company")
    public String editCompany(final @AuthenticationPrincipal User user,
                              final UserDto userDto,
                              final Model model) {
        model.addAttribute("userDto", userDto);
        editJobService.editEmployeeCompany(user, userDto);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/title/{id}")
    public String editJobTitle(final @AuthenticationPrincipal User user,
                               final @PathVariable(name = "id") Long id,
                               final JobDto jobDto,
                               final Model model) {
        if (checkEmployeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJobService.editJobTitle(jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/location/{id}")
    public String editJobLocation(final @AuthenticationPrincipal User user,
                                  final @PathVariable(name = "id") Long id,
                                  final JobDto jobDto,
                                  final Model model) {
        if (checkEmployeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJobService.editJobLocation(jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/description/{id}")
    public String editJobDescription(final @AuthenticationPrincipal User user,
                                     final @PathVariable(name = "id") Long id,
                                     final JobDto jobDto,
                                     final Model model) {
        if (checkEmployeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJobService.editJobDescription(jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/experience/{id}")
    public String editJobDesiredExperience(final @AuthenticationPrincipal User user,
                                           final @PathVariable(name = "id") Long id,
                                           final JobDto jobDto,
                                           final Model model) {
        if (checkEmployeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJobService.editJobDesiredDescription(jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/qualification/{id}")
    public String editJobQualification(final @AuthenticationPrincipal User user,
                                       final @PathVariable(name = "id") Long id,
                                       final JobDto jobDto,
                                       final Model model) {
        if (checkEmployeeService.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJobService.editJobQualification(jobDto, id);
        return "redirect:/employee/jobs";
    }
}
