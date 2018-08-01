package com.instinctools.service.developer.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import org.springframework.stereotype.Service;

@Service
public class CheckDeveloperService implements CheckDeveloper {

    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;

    public CheckDeveloperService(WorkExperienceRepo workExperienceRepo, EducationRepo educationRepo) {
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
    }

    @Override
    public boolean checkDeveloperEditingWork(User user, Long id) {
        return user.getDeveloper().equals(workExperienceRepo.getOne(id).getDeveloper());
    }

    @Override
    public boolean checkDeveloperEditingEducation(User user, Long id) {
        return user.getDeveloper().equals(educationRepo.getOne(id).getDeveloper());

    }
}
