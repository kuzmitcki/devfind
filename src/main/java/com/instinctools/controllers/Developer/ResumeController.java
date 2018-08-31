package com.instinctools.controllers.Developer;

import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.SpecializationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.mail.MailServiceSender;
import net.bytebuddy.TypeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('DEVELOPER')")
public class ResumeController {
    private final MailServiceSender mailSender;
    private final SpecializationRepo specializationRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;
    private final Logger l = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    public ResumeController(final MailServiceSender mailSender,
                            final SpecializationRepo specializationRepo,
                            final WorkExperienceRepo workExperienceRepo,
                            final EducationRepo educationRepo) {
        this.mailSender = mailSender;
        this.specializationRepo = specializationRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
    }

    @GetMapping("developer/resume")
    public String profilePage(final @AuthenticationPrincipal User user,
                              final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("developer", user.getDeveloper());
        model.addAttribute("telephone", user.getDeveloper().getTelephone());
        model.addAttribute("specializations",
                                         specializationRepo.findByDeveloper(user.getDeveloper()));
        model.addAttribute("educations",
                                         educationRepo.findByDeveloper(user.getDeveloper()));
        model.addAttribute("workExperiences",
                                         workExperienceRepo.findByDeveloper(user.getDeveloper()));
        List<WorkExperience> collect = user.getDeveloper().getWorkExperiences().stream().sorted(Comparator.comparing(WorkExperience::getYearFrom)).
                                        collect(Collectors.toList());
        l.info(collect.toString());
        return "developer/resume/resume";
    }

    @GetMapping("developer/job-offer")
    public String offerSaving(final @AuthenticationPrincipal User user) {
        mailSender.getOfferFromEmployee(user);
        return "redirect:/developer/resume";
    }

    @GetMapping("developer/resume/preview")
    public String previewResume(final @AuthenticationPrincipal User user,
                                final Model model) {
        model.addAttribute("developer", user.getDeveloper());
        model.addAttribute("specializations", specializationRepo.findByDeveloper(user.getDeveloper()));
        model.addAttribute("education", educationRepo.findByDeveloper(user.getDeveloper()));
        model.addAttribute("workExperiences", workExperienceRepo.findByDeveloper(user.getDeveloper()));
        return "developer/preview/preview";
    }
}
