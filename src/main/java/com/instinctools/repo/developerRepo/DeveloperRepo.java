package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeveloperRepo extends JpaRepository<Developer , Long> {
    Set<Developer> findByAdditionalInformationIgnoreCaseLike(String additionalLike);

    Set<Developer> findBySummaryIgnoreCaseLike(String summaryLike);

    Set<Developer> findByCityIgnoreCaseOrZipPostalCodeIgnoreCaseOrCountryIgnoreCase(String city, String zipPostalCode, String country);
}
