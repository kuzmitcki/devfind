package com.instinctools.service.developer.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;
import com.instinctools.service.exceptions.EducationNotFoundException;
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
                                             final Long id) throws WorkExperienceNotFoundException {
        return user.getDeveloper().equals(workExperienceRepo.findById(id).
                                                orElseThrow(()-> new WorkExperienceNotFoundException("Cannot find work with id " + id)).
                                          getDeveloper());
    }

    @Override
    public boolean checkDeveloperEditingEducation(final User user,
                                                  final Long id) throws EducationNotFoundException {
        return user.getDeveloper().equals(educationRepo.findById(id).
                                                    orElseThrow(()-> new EducationNotFoundException("Cannot find education with id " + id)).
                                          getDeveloper());

    }
}
