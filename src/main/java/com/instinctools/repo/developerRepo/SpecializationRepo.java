package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    Set<Specialization> findBySkillIgnoreCase(String skill);

    Set<Specialization> findByDeveloper(Developer developer);
}
