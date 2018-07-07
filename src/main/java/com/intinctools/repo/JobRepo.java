package com.intinctools.repo;

import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByTitleIgnoreCaseContaining(String title);
    List<Job> findByDesiredExperienceIgnoreCaseLike(String desiredExperience);
    List<Job> findByFullDescriptionIgnoreCaseContaining(String description);
    Job getById(Long id);
    List<Job> findByEmployee(Employee employee);
    List<Job> findByCompanyIgnoreCaseContaining(String company);
    List<Job> findBySalaryPeriod(String salaryPeriod);


    Set<Job> findByFromSalaryGreaterThanEqual(Long fromSalary);
    Set<Job> findByToSalaryLessThanEqual(Long toSalary);
    Set<Job> findByFromSalaryAndToSalaryBetween(Long fromSalary, Long toSalary);
}
