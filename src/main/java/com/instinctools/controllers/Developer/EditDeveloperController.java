package com.instinctools.controllers.Developer;

import com.instinctools.controllers.Dto.SpecializationDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.exceptions.EducationNotFoundException;
import com.instinctools.service.exceptions.SpecializationNotFoundException;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;
import com.instinctools.service.developer.check.CheckDeveloperService;
import com.instinctools.service.developer.edit.EditDeveloperService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class EditDeveloperController {

    private final EditDeveloperService editDeveloperService;
    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;
    private final CheckDeveloperService checkDeveloperService;

    public EditDeveloperController(final EditDeveloperService editDeveloperService,
                                   final WorkExperienceRepo workExperienceRepo,
                                   final EducationRepo educationRepo,
                                   final CheckDeveloperService checkDeveloperService) {
        this.editDeveloperService = editDeveloperService;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
        this.checkDeveloperService = checkDeveloperService;
    }

    @PostMapping("/edit-developer/information")
    public String editContactInformation(final @AuthenticationPrincipal User user,
                                         final UserDto userDTO,
                                         final HttpServletRequest request,
                                         final Model model) {
        model.addAttribute("developerDto", userDTO);
        editDeveloperService.editResumeBasicInformation(user, userDTO, request);
        return "redirect:/developer/resume";
    }

    @GetMapping("edit-developer/education/{id}")
    public String educationEditPage(final @AuthenticationPrincipal User user,
                                    final @PathVariable(name = "id") Long id,
                                    final Model model) throws EducationNotFoundException {
        if (checkDeveloperService.checkDeveloperEditingEducation(user, id)) {
            return "redirect:/developer/resume";
        }
        model.addAttribute("educ", educationRepo.getOne(id));
        return "developer/edit/education";
    }

    @PostMapping("/edit-developer/experience/{id}")
    public String workExperienceEdit(final @AuthenticationPrincipal User user,
                                     final @PathVariable("id") Long id,
                                     final WorkExperienceDto workExperienceDto,
                                     final Model model) throws WorkExperienceNotFoundException {
        model.addAttribute("workExperienceDto", workExperienceDto);
        editDeveloperService.editDeveloperWorkExperience(user, id, workExperienceDto);
        return "redirect:/developer/resume";
    }

    @GetMapping("/edit-developer/experience/{id}")
    public String workExperienceEditPage(final @AuthenticationPrincipal User user,
                                         final @PathVariable(name = "id") Long id,
                                         final Model model) throws WorkExperienceNotFoundException {
        if (checkDeveloperService.checkDeveloperEditingWork(user, id)) {
            return "redirect:/developer/resume";
        }
        model.addAttribute("work", workExperienceRepo.getOne(id));
        return "developer/edit/experience";
    }

    @PostMapping("/edit-developer/skill/{id}")
    public String skillEdit(final @PathVariable("id") Long id,
                            final SpecializationDto specializationDto,
                            final Model model) throws SpecializationNotFoundException {
        model.addAttribute("specializationDto", specializationDto);
        editDeveloperService.editDeveloperSkill(id, specializationDto);
        return "redirect:/developer/resume";
    }
}
