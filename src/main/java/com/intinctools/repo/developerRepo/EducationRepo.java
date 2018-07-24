package com.intinctools.repo.developerRepo;

import com.intinctools.entities.devEntities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EducationRepo extends JpaRepository<Education, Long> {
    Set<Education> findByPlaceIgnoreCaseLike(String cityLike);

    Set<Education> findByDegreeIgnoreCase(String degreeLike);

    Set<Education> findByFieldOfStudyIgnoreCaseLike(String fieldOfStudyLike);
}
