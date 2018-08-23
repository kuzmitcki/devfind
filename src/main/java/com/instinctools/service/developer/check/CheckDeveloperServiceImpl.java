package com.instinctools.service.developer.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import org.springframework.stereotype.Service;

@Service
public class CheckDeveloperServiceImpl implements CheckDeveloperService {
    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;

    public CheckDeveloperServiceImpl(final WorkExperienceRepo workExperienceRepo,
                                     final EducationRepo educationRepo) {
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
    }

    @Override
    public boolean checkDeveloperEditingWork(final User user,
                                             final Long id) {
        return user.getDeveloper().equals(workExperienceRepo.getOne(id).getDeveloper());
    }

    @Override
    public boolean checkDeveloperEditingEducation(final User user,
                                                  final Long id) {
        return user.getDeveloper().equals(educationRepo.getOne(id).getDeveloper());

    }
}
