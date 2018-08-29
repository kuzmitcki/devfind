package com.instinctools.controllers.Developer;

import com.instinctools.service.exceptions.EducationNotFoundException;
import com.instinctools.service.exceptions.SpecializationNotFoundException;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;
import com.instinctools.service.developer.delete.DeleteDeveloperService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class DeleteDeveloperInformationController {

    private final DeleteDeveloperService developerService;

    public DeleteDeveloperInformationController(final DeleteDeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("edit-developer/education-delete/{id}")
    public String  deleteEducation(final @PathVariable(name = "id") Long id) throws EducationNotFoundException {
        developerService.deleteDeveloperEducation(id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/work-delete/{id}")
    public String  deleteWork(final @PathVariable(name = "id") Long id) throws WorkExperienceNotFoundException {
        developerService.deleteDeveloperWork(id);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skill-delete/{id}")
    public String deleteSkill(final @PathVariable("id") Long id) throws SpecializationNotFoundException {
        developerService.deleteDeveloperSkill(id);
        return "redirect:/developer/resume";
    }
}
