package com.instinctools.service.developer.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.service.exceptions.EducationNotFoundException;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;

public interface CheckDeveloperService {
    boolean checkDeveloperEditingWork(User user, Long id) throws WorkExperienceNotFoundException;

    boolean checkDeveloperEditingEducation(User user, Long id) throws EducationNotFoundException;
}
