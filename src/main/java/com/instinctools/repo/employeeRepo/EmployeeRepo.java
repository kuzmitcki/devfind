package com.instinctools.repo.employeeRepo;

import com.instinctools.entities.empEntites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByCompanyIgnoreCaseLike(String companyLike);
}
