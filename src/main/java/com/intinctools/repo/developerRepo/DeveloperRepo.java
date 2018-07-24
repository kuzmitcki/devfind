package com.intinctools.repo.developerRepo;

import com.intinctools.entities.devEntities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeveloperRepo extends JpaRepository<Developer , Long> {
    Set<Developer> findByCountryIgnoreCase(String country);

    Set<Developer> findByCityIgnoreCase(String city);

    Set<Developer> findByZipPostalCode(String zipPostalCode);

    Set<Developer> findByAdditionalInformationIgnoreCaseLike(String additionalLike);

    Set<Developer> findBySummaryIgnoreCaseLike(String summaryLike);
}
