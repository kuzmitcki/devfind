package com.instinctools.repo.employeeRepo;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByTitleIgnoreCaseLike(String titleLike);

    List<Job> findByQualificationsIgnoreCaseLike(String qualificationsLike);

    List<Job> findByFullDescriptionIgnoreCaseLike(String descriptionLike);

    List<Job> findByJobTypeIgnoreCaseLike(String jobTypeLike);

    List<Job> findByJobLocationIgnoreCaseLike(String jobLocationLike);

    List<Job> findByCountryIgnoreCaseLike(String countryLike);

    List<Job> findByDesiredExperienceIgnoreCaseLike(String desiredExperienceLike);

    List<Job> findByToSalaryBetween(Long toSalary, Long toSalaryWithCoefficient);

    List<Job> findByFromSalaryBetween(Long fromSalaryWithCoefficient, Long fromSalary);

    List<Job> findByEmployee(Employee employee);
}
