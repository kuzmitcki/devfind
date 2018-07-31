package com.instinctools.service.developer.delete;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Education;
import com.instinctools.entities.devEntities.Specialization;
import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class DeleteDeveloperInformationService implements DeleteDeveloper {
    private final UserRepo userRepo;

    public DeleteDeveloperInformationService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void deleteDeveloperEducation(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<Education> educations = new HashSet<>(developer.getEducation());
        developer.getEducation().clear();
        educations.removeIf(e -> e.getId().equals(id));
        developer.setEducation(educations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void deleteDeveloperWork(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<WorkExperience> workExperiences = new HashSet<>(developer.getWorkExperiences());
        developer.getWorkExperiences().clear();
        workExperiences.removeIf(e -> e.getId().equals(id));
        developer.setWorkExperiences(workExperiences);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void deleteDeveloperSkill(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<Specialization> specializations = new HashSet<>(developer.getSpecializations());
        developer.getSpecializations().clear();
        specializations.removeIf(s -> s.getId().equals(id));
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }


}

