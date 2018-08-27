package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByJobTitleIgnoreCaseLike(String jobTitleLike);

    List<WorkExperience> findByCompanyIgnoreCase(String company);

    List<WorkExperience> findByDescriptionIgnoreCaseLike(String descriptionLike);

    List<WorkExperience> findByDeveloper(Developer developer);
}
