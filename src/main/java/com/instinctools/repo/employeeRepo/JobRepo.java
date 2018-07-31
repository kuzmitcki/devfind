package com.instinctools.repo.employeeRepo;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface JobRepo extends JpaRepository<Job, Long> {
    Set<Job> findByTitleIgnoreCaseLike(String titleLike);

    Set<Job> findByQualificationsIgnoreCaseLike(String qualificationsLike);

    Set<Job> findByFullDescriptionIgnoreCaseLike(String descriptionLike);

    Set<Job> findByJobTypeIgnoreCaseLike(String jobTypeLike);

    Set<Job> findByJobLocationIgnoreCaseLike(String jobLocationLike);

    Set<Job> findByCountryIgnoreCaseLike(String countryLike);

    Set<Job> findByDesiredExperienceIgnoreCaseLike(String desiredExperienceLike);

    Set<Job> findByToSalaryBetween(Long toSalary, Long toSalaryWithCoefficient);

    Set<Job> findByFromSalaryBetween(Long fromSalaryWithCoefficient, Long fromSalary);

    Set<Job> findByEmployee(Employee employee);
}
