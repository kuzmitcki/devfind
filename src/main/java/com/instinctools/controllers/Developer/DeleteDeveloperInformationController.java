package com.instinctools.controllers.Developer;

import com.instinctools.entities.userEntites.User;
import com.instinctools.service.developer.delete.DeleteDeveloper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class DeleteDeveloperInformationController {

    private final DeleteDeveloper developerService;

    public DeleteDeveloperInformationController(DeleteDeveloper developerService) {
        this.developerService = developerService;
    }

    @PostMapping("edit-developer/education-delete/{id}")
    public String  deleteEducation(final @AuthenticationPrincipal User user,
                                   final @PathVariable(name = "id") Long id) {
        developerService.deleteDeveloperEducation(user, id);
        return "redirect:/developer/resume";
    }


    @PostMapping("edit-developer/work-delete/{id}")
    public String  deleteWork(final @AuthenticationPrincipal User user,
                              final @PathVariable(name = "id") Long id) {
        developerService.deleteDeveloperWork(user, id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skill-delete/{id}")
    public String deleteSkill(final @AuthenticationPrincipal User user,
                              final @PathVariable("id") Long id) {
        developerService.deleteDeveloperSkill(user, id);
        return "redirect:/developer/resume";
    }


}
