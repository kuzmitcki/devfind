package com.intinctools.repo;

import com.intinctools.entities.devEntities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepo extends JpaRepository<Education, Long> {
    Education getById(Long id);
}
