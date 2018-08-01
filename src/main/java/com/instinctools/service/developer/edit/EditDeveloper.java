package com.instinctools.service.developer.edit;

import com.instinctools.entities.devEntities.dto.DeveloperDTO;
import com.instinctools.entities.devEntities.dto.WorkExperienceDTO;
import com.instinctools.entities.userEntites.User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public interface EditDeveloper {
    void editResumeBasicInformation(User user, String email, DeveloperDTO developerDTO, HttpServletRequest request);

    void editDeveloperWorkExperience(User user, Long id, WorkExperienceDTO workExperienceDTO);
}
