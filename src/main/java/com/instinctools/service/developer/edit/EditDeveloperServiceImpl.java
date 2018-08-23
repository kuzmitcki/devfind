package com.instinctools.service.developer.edit;


import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.mail.MailSerivce;
import com.instinctools.service.mail.MailServiceSender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class EditDeveloperServiceImpl implements EditDeveloperService {
    private final UserRepo userRepo;
    private final MailSerivce mailServiceSender;
    private final WorkExperienceRepo workExperienceRepo;

    public EditDeveloperServiceImpl(final UserRepo userRepo,
                                    final MailServiceSender mailSender,
                                    final WorkExperienceRepo workExperienceRepo) {
        this.userRepo = userRepo;
        this.mailServiceSender = mailSender;
        this.workExperienceRepo = workExperienceRepo;
    }

    @Override
    public void editResumeBasicInformation(final User user,
                                           final String email,
                                           final UserDto userDTO,
                                           final HttpServletRequest request) {
        Developer developer = user.getDeveloper();
        developer.setCountry(userDTO.getCountry());
        developer.setCity(userDTO.getCity());
        developer.setTelephone(userDTO.getTelephone());
        developer.setZipPostalCode(userDTO.getZipPostalCode());

        if (!user.getEmail().equals(email)) {
            request.getSession().setAttribute("email", email);
            user.setActivationCode(UUID.randomUUID().toString());
            String message = String.format(
                    "Hello, %s! \n"
                            + "Please, visit next link to change your email: http://localhost:8080/change/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailServiceSender.send(user.getEmail(), "Activation code", message);
        }
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void editDeveloperWorkExperience(final User user,
                                            final Long id,
                                            final WorkExperienceDto workExperienceDTO) {
        WorkExperience workExperience = workExperienceRepo.getOne(id);

        ModelMapper mapper = new ModelMapper();
        mapper.map(workExperienceDTO, workExperience);
        workExperience.setDeveloper(user.getDeveloper());

        workExperienceRepo.save(workExperience);
    }
}
