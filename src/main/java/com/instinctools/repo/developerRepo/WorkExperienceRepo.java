package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {
    WorkExperience getById(Long id);

    Set<WorkExperience> findByJobTitleIgnoreCaseLike(String jobTitleLike);

    Set<WorkExperience> findByCompanyIgnoreCase(String company);

    Set<WorkExperience> findByDescriptionIgnoreCaseLike(String descriptionLike);
}
