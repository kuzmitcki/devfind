package com.instinctools.controllers.Developer;

import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.EducationDto;
import com.instinctools.controllers.Dto.SkillDto;
import com.instinctools.controllers.Dto.DesiredJobDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.service.developer.adding.AddDeveloper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class AddDeveloperInformationController {

    private final AddDeveloper addDeveloper;

    public AddDeveloperInformationController(AddDeveloper addDeveloper) {
        this.addDeveloper = addDeveloper;
    }

    @GetMapping("resume/wizard/profile")
    public String resumeBasicsPage(final @AuthenticationPrincipal User user,
                                   final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        return "developer/resume/profile";
    }

    @PostMapping("/resume/wizard/profile")
    public String resumeBasicsAdd(final @AuthenticationPrincipal User user,
                                  final Model model,
                                  final UserDto userDto) {
        model.addAttribute("developerDto", userDto);
        addDeveloper.setBasicQualities(user, userDto);
        return "redirect:/resume/wizard/education";
    }

    @GetMapping("resume/wizard/education")
    public String resumeEducationPage(@AuthenticationPrincipal  User user,
                                      final Model model) {
        model.addAttribute("education", user.getDeveloper().getEducation().isEmpty());
        return "developer/resume/education";
    }

    @PostMapping("resume/wizard/education")
    public String resumeEducation(final @AuthenticationPrincipal User user,
                                  final EducationDto educationDto,
                                  final Model model) {
        model.addAttribute("educationDto", educationDto);
        if (!user.getDeveloper().getWorkExperiences().isEmpty()) {
            addDeveloper.setEducation(user, educationDto);
            return "redirect:/developer/resume";
        }
        addDeveloper.setEducation(user, educationDto);
        return   "redirect:/resume/wizard/experience";
    }

    @GetMapping("resume/wizard/experience")
    public String resumeExperiencePage() {
        return "developer/resume/experience";
    }

    @PostMapping("resume/wizard/experience")
    public String resumeExperience(final @AuthenticationPrincipal User user,
                                   final @RequestParam(name = "check", required = false) String check,
                                   final WorkExperienceDto workExperienceDto,
                                   final Model model) {
        model.addAttribute("workExperienceDTO", workExperienceDto);
        addDeveloper.setWorkExperience(user, workExperienceDto, check);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/skills")
    public String skills(final @AuthenticationPrincipal User user,
                         final Model model,
                         final SkillDto skillDto) {
        model.addAttribute("skillDto", skillDto);
        addDeveloper.setDeveloperSkill(user, skillDto);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/desired")
    public String editDesiredJob(final @AuthenticationPrincipal User user,
                                 final DesiredJobDto desiredJobDto,
                                 Model model) {
        model.addAttribute("desiredJobDto", desiredJobDto);
        desiredJobDto.setDeveloper(user.getDeveloper());
        addDeveloper.setDesiredJob(user, desiredJobDto);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/summary")
    public String summary(final @AuthenticationPrincipal User user,
                          final @RequestParam(name = "summary") String summary) {
        addDeveloper.setDeveloperSummary(user, summary);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/additional")
    public String additionalInformation(final @AuthenticationPrincipal  User user,
                                        final @RequestParam(name = "additional") String additional) {
        addDeveloper.setDeveloperAdditional(user, additional);
        return "redirect:/developer/resume";
    }

    @PostMapping("edit-developer/education/{id}")
    public String educationEdit(final @AuthenticationPrincipal User user,
                                final @PathVariable("id") Long id,
                                final EducationDto educationDto,
                                final Model model) {
        model.addAttribute("educationDto", educationDto);
        addDeveloper.setDeveloperEducation(user, educationDto, id);
        return "redirect:/developer/resume";
    }
}

