package com.instinctools.controllers.Employee;

import com.instinctools.entities.userEntites.User;
import com.instinctools.service.employee.check.CheckEmployee;
import com.instinctools.service.employee.edit.EditJob;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                              final @RequestParam(name = "company") String company) {
        editJob.editEmployeeCompany(user, company);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/title/{id}")
    public String editJobTitle(final @AuthenticationPrincipal User user,
                               final @RequestParam(name = "title") String title,
                               final @PathVariable(name = "id") Long id) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        editJob.editJobTitle(user, title, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("edit-employee/job/location/{id}")
    public String editJobLocation(final @AuthenticationPrincipal User user,
                                  final @RequestParam(name = "country") String country,
                                  final @RequestParam(name = "location") String location,
                                  final @PathVariable(name = "id") Long id) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        editJob.editJobLocation(user, country, location, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/description/{id}")
    public String editJobDescription(final @AuthenticationPrincipal User user,
                                     final @RequestParam(name = "description") String description,
                                     final @PathVariable(name = "id") Long id) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        editJob.editJobDescription(user, description, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/experience/{id}")
    public String editJobDesiredExperience(final @AuthenticationPrincipal User user,
                                           final @RequestParam(name = "experience") String experience,
                                           final @PathVariable(name = "id") Long id) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        editJob.editJobDesiredDescription(user, experience, id);
        return "redirect:/employee/jobs";
    }

    @PostMapping("/edit-employee/job/qualification/{id}")
    public String editJobQualification(final @AuthenticationPrincipal User user,
                                       final @RequestParam(name = "qualification") String qualification,
                                       final @PathVariable(name = "id") Long id) {
        if (checkEmployee.checkEmployeeEditing(user, id)) {
            return "redirect:/employee/jobs";
        }
        editJob.editJobQualification(user, qualification, id);
        return "redirect:/employee/jobs";
    }
}
