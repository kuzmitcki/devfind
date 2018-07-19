package com.intinctools.repo.employeeRepo;

import com.intinctools.entities.empEntites.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface JobRepo extends JpaRepository<Job, Long> {
    Job getById(Long id);
    Set<Job> findByTitleIgnoreCaseLike(String titleLike);
    Set<Job> findByQualificationsIgnoreCaseLike(String qualificationsLike);
    Set<Job> findByJobLocationIgnoreCaseLike(String jobLocationLike);
    Set<Job> findByCountryIgnoreCaseLike(String countryLike);
}
