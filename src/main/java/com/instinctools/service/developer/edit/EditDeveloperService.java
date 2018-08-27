package com.instinctools.service.developer.edit;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.entities.userEntites.User;
import javax.servlet.http.HttpServletRequest;

public interface EditDeveloperService {
    void editResumeBasicInformation(User user, UserDto userDTO, HttpServletRequest request);

    void editDeveloperWorkExperience(User user, Long id, WorkExperienceDto workExperienceDTO);
}
