package com.intinctools.repo;

import com.intinctools.entities.devEntities.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {
    WorkExperience getById(Long id);
}
