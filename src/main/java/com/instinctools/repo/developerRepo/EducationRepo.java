package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EducationRepo extends JpaRepository<Education, Long> {
    Set<Education> findByPlaceIgnoreCaseLike(String cityLike);

    Set<Education> findByDegreeIgnoreCase(String degreeLike);

    Set<Education> findByFieldOfStudyIgnoreCaseLike(String fieldOfStudyLike);

    Set<Education> findByDeveloper(Developer developer);
}
