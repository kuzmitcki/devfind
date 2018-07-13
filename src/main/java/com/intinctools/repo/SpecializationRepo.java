package com.intinctools.repo;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    List<Specialization> findByExperienceContaining(String resume);
    List<Specialization> findBySkillIgnoreCaseContaining(String skill);
    List<Specialization> findByDeveloper(Developer developer);

    Specialization getById(Long id);
}
