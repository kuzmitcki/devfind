package com.instinctools.service.developer.delete;

import com.instinctools.service.exceptions.EducationNotFoundException;
import com.instinctools.service.exceptions.SpecializationNotFoundException;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;

public interface DeleteDeveloperService {
    void deleteDeveloperEducation(Long id) throws EducationNotFoundException;

    void deleteDeveloperWork(Long id) throws WorkExperienceNotFoundException;

    void deleteDeveloperSkill(Long id) throws SpecializationNotFoundException;
}
