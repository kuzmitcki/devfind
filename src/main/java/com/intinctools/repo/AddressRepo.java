package com.intinctools.repo;

import com.intinctools.entities.empEntites.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address , Long> {
    List<Address> findByStateIgnoreCaseLike(String state);
    List<Address> findByState(String state);
    List<Address> findByCountryIgnoreCaseLike(String country);
    List<Address> findByZipPostalCode(String zipPostalCode);
}
