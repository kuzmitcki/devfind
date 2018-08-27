package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepo extends JpaRepository<Education, Long> {
    List<Education> findByPlaceIgnoreCaseLike(String cityLike);

    List<Education> findByDegreeIgnoreCase(String degreeLike);

    List<Education> findByFieldOfStudyIgnoreCaseLike(String fieldOfStudyLike);

    List<Education> findByDeveloper(Developer developer);
}
