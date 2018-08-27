package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    List<Specialization> findBySkillIgnoreCase(String skill);

    List<Specialization> findByDeveloper(Developer developer);
}
