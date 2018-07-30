package com.instinctools.repo.employeeRepo;

import com.instinctools.entities.empEntites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Set<Employee> findByCompanyIgnoreCaseLike(String companyLike);
}
