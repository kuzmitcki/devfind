package com.intinctools.repo;

import com.intinctools.entities.devEntities.DeveloperAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperAddressRepo extends JpaRepository<DeveloperAddress, Long> {
    List<DeveloperAddress> findByCountryIgnoreCaseLike(String country);
    List<DeveloperAddress> findByCityIgnoreCaseLike(String city);
    List<DeveloperAddress> findByZipPostalCode(String zipPostalCode);
}
