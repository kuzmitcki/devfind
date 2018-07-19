package com.intinctools.repo.developerRepo;

import com.intinctools.entities.devEntities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {
    Set<Specialization> findBySkillIgnoreCase(String skill);

}
