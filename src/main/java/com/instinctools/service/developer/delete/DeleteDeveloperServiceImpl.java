package com.instinctools.service.developer.delete;

import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.SpecializationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import org.springframework.stereotype.Service;


@Service
public class DeleteDeveloperServiceImpl implements DeleteDeveloperService {
    private final EducationRepo educationRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final SpecializationRepo specializationRepo;

    public DeleteDeveloperServiceImpl(final EducationRepo educationRepo,
                                      final WorkExperienceRepo workExperienceRepo,
                                      final SpecializationRepo specializationRepo) {
        this.educationRepo = educationRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.specializationRepo = specializationRepo;
    }

    @Override
    public void deleteDeveloperEducation(final Long id) {
        educationRepo.deleteById(id);
    }

    @Override
    public void deleteDeveloperWork(final Long id) {
       workExperienceRepo.deleteById(id);
    }

    @Override
    public void deleteDeveloperSkill(final Long id) {
        specializationRepo.deleteById(id);
    }
}

