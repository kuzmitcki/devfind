package com.instinctools.service.developer.edit;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.entities.userEntites.User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public interface EditDeveloperService {
    void editResumeBasicInformation(User user, String email, UserDto userDTO, HttpServletRequest request);

    void editDeveloperWorkExperience(User user, Long id, WorkExperienceDto workExperienceDTO);
}
