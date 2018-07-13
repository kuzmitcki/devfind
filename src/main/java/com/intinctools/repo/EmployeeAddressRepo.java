package com.intinctools.repo;

import com.intinctools.entities.empEntites.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAddressRepo extends JpaRepository<EmployeeAddress, Long> {
    List<EmployeeAddress> findByStateIgnoreCaseLike(String state);
    List<EmployeeAddress> findByCountryIgnoreCaseLike(String country);
    List<EmployeeAddress> findByZipPostalCode(String zipPostalCode);
}
