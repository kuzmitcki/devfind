package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepo extends JpaRepository<Developer , Long> {
    List<Developer> findByAdditionalInformationIgnoreCaseLike(String additionalLike);

    List<Developer> findBySummaryIgnoreCaseLike(String summaryLike);

    List<Developer> findByCityIgnoreCaseOrZipPostalCodeIgnoreCaseOrCountryIgnoreCase(String city, String zipPostalCode, String country);
}
