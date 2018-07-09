package com.intinctools.repo;

import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface JobRepo extends JpaRepository<Job, Long> {


    Job getById(Long id);
    List<Job> findByEmployee(Employee employee);
    List<Job> findBySalaryPeriod(String salaryPeriod);
    Set<Job> findByFromSalaryGreaterThanEqual(Long fromSalary);
    Set<Job> findByToSalaryLessThanEqual(Long toSalary);

    Set<Job> findByDesiredExperienceIgnoreCaseLike(String desiredExperience);

    List<Job> findByTitleIgnoreCaseLike(String title);

    List<Job> findByCompanyIgnoreCaseLike(String company);

    Set<Job> findByFullDescriptionIgnoreCaseLike(String description);
}
