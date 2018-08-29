package com.instinctools.service.developer.delete;

import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.SpecializationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.exceptions.EducationNotFoundException;
import com.instinctools.service.exceptions.WorkExperienceNotFoundException;
import com.instinctools.service.exceptions.SpecializationNotFoundException;
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
    public void deleteDeveloperEducation(final Long id) throws EducationNotFoundException {
        educationRepo.delete(educationRepo.findById(id).
                                                orElseThrow(()->
                                                        new EducationNotFoundException("Cannot delete education with id " + id + ". Education doesn't exist")));
    }

    @Override
    public void deleteDeveloperWork(final Long id) throws WorkExperienceNotFoundException {
       workExperienceRepo.delete(workExperienceRepo.findById(id).
                                            orElseThrow(()->
                                                    new WorkExperienceNotFoundException("Cannot delete work with id " + id + ". Work doesn't exist")));
    }

    @Override
    public void deleteDeveloperSkill(final Long id) throws SpecializationNotFoundException {
        specializationRepo.delete(specializationRepo.findById(id).
                                        orElseThrow(()->
                                                new SpecializationNotFoundException("Cannot delete specialization with id " + id + ". Specialization doesn't exists")));
    }
}

