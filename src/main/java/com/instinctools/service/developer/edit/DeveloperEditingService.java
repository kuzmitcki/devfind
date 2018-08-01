package com.instinctools.service.developer.edit;


import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.entities.devEntities.dto.DeveloperDTO;
import com.instinctools.entities.devEntities.dto.WorkExperienceDTO;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.mail.Mail;
import com.instinctools.service.mail.MailSender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class DeveloperEditingService implements EditDeveloper {

    private final UserRepo userRepo;
    private final Mail mailSender;
    private final WorkExperienceRepo workExperienceRepo;

    public DeveloperEditingService(UserRepo userRepo, MailSender mailSender, WorkExperienceRepo workExperienceRepo) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.workExperienceRepo = workExperienceRepo;
    }

    @Override
    public void editResumeBasicInformation(final User user, String email, final DeveloperDTO developerDTO, final HttpServletRequest request) {
        Developer developer = user.getDeveloper();
        ModelMapper mapper = new ModelMapper();
        mapper.map(developerDTO, developer);
        if (!user.getEmail().equals(email)) {
            request.getSession().setAttribute("email", email);
            user.setActivationCode(UUID.randomUUID().toString());
            String message = String.format(
                    "Hello, %s! \n"
                            + "Please, visit next link to change your email: http://localhost:8080/change/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void editDeveloperWorkExperience(final User user, final Long id, WorkExperienceDTO workExperienceDTO) {
        WorkExperience workExperience = workExperienceRepo.getOne(id);

        ModelMapper mapper = new ModelMapper();
        mapper.map(workExperienceDTO, workExperience);

        Developer developer = workExperience.getDeveloper();
        user.setDeveloper(developer);
        userRepo.save(user);
    }
}
