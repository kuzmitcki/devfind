package com.instinctools.service.developer.adding;

import com.instinctools.entities.devEntities.dto.DesiredJobDTO;
import com.instinctools.entities.devEntities.dto.DeveloperDTO;
import com.instinctools.entities.devEntities.dto.EducationDTO;
import com.instinctools.entities.devEntities.dto.WorkExperienceDTO;
import com.instinctools.entities.userEntites.User;

public interface AddDeveloper {
    void setBasicQualities(User user, DeveloperDTO developerDTO);

    void setEducation(User user, EducationDTO educationDTO);

    void setWorkExperience(User user, WorkExperienceDTO workExperienceDTO, String check);


    void setDeveloperSummary(User user, String summary);

    void setDeveloperAdditional(User user, String additional);


    void setDeveloperEducation(User user, EducationDTO educationDTO, Long id);

    void setDeveloperSkill(User user, String skill, String year);

    void setDesiredJob(User user, DesiredJobDTO desiredJobDTO);

}
