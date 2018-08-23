package com.instinctools.service.developer.adding;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.EducationDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.controllers.Dto.SkillDto;
import com.instinctools.controllers.Dto.DesiredJobDto;
import com.instinctools.entities.userEntites.User;

public interface AddDeveloperService {
    void setBasicQualities(User user, UserDto userDTO);

    void setEducation(User user, EducationDto educationDTO);

    void setWorkExperience(User user, WorkExperienceDto workExperienceDTO, String check);


    void setDeveloperSummary(User user, String summary);

    void setDeveloperAdditional(User user, String additional);


    void setDeveloperEducation(User user, EducationDto educationDTO, Long id);

    void setDeveloperSkill(User user, SkillDto skillDto);

    void setDesiredJob(User user, DesiredJobDto desiredJobDTO);
}
