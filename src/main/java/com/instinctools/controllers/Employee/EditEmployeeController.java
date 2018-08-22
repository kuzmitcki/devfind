package com.instinctools.controllers.Employee;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.employee.check.CheckEmployee;
import com.instinctools.service.employee.edit.EditJob;
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
    private final EditJob editJob;
    private final CheckEmployee checkEmployee;

    public EditEmployeeController(EditJob editJob, CheckEmployee checkEmployee) {
        this.editJob = editJob;
        this.checkEmployee = checkEmployee;
    }

    @GetMapping("employee/jobs")
    public String jobsPage(final @AuthenticationPrincipal User user,
                           final Model model) {
        model.addAttribute("employee", user.getEmployee());
        return "employee/job/jobs";
    }

    @PostMapping("edit-employee/company")
    public String editCompany(final @AuthenticationPrincipal User user,
                              final UserDto userDto,
                              Model model) {
        model.addAttribute("userDto", userDto);
        editJob.editEmployeeCompany(user, userDto);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/title/{id}")
    public String editJobTitle(final @AuthenticationPrincipal User user,
                               final @PathVariable(name = "id") Long id,
                               final JobDto jobDto,
                               Model model) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJob.editJobTitle(user, jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/location/{id}")
    public String editJobLocation(final @AuthenticationPrincipal User user,
                                  final @PathVariable(name = "id") Long id,
                                  final JobDto jobDto,
                                  Model model) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJob.editJobLocation(user, jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/description/{id}")
    public String editJobDescription(final @AuthenticationPrincipal User user,
                                     final @PathVariable(name = "id") Long id,
                                     final JobDto jobDto,
                                     Model model) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJob.editJobDescription(user, jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/experience/{id}")
    public String editJobDesiredExperience(final @AuthenticationPrincipal User user,
                                           final @PathVariable(name = "id") Long id,
                                           final JobDto jobDto,
                                           Model model) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJob.editJobDesiredDescription(user, jobDto, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/qualification/{id}")
    public String editJobQualification(final @AuthenticationPrincipal User user,
                                       final @PathVariable(name = "id") Long id,
                                       final JobDto jobDto,
                                       Model model) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        model.addAttribute("jobDto", jobDto);
        editJob.editJobQualification(user, jobDto, id);
        return "redirect:/employee/jobs";
    }
}
