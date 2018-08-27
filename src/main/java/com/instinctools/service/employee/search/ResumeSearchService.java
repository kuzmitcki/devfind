package com.instinctools.service.employee.search;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.userEntites.User;

import java.util.List;
import java.util.Set;

public interface ResumeSearchService {
    List<Developer> searchForResumeByTitleAndDesiredTitle(String title);

    List<Developer> searchForResumeByWorkTitle(String title);

    List<Developer> searchForResumeByWorkDescription(String description);

    List<Developer> searchForResumeByCompany(String company);

    List<Developer> searchForResumeBySkills(String skills);

    List<Developer> searchForResumeByEducationPlace(String place);

    List<Developer> searchForResumeByEducationDegree(String degree);

    List<Developer> searchForResumeByFieldOfStudy(String fieldOfStudy);

    List<Developer> searchForDeveloperByExperience(Long experience);

    List<Developer> searchForResumeByDescription(String description);

    List<Developer> searchForDeveloperByLocation(String location);

    List<Developer> searchForResume(SearchDto searchDto);

    List<Developer> searchForResumeByAdditionalInformation(String additional);

    List<Developer> searchForResumeBySummary(String  summary);

    List<Developer> searchForResumeByWords(String allWords);

    List<Developer> searchForResumeByPhrase(String phrase);

    List<Developer> searchForResumeByOneWord(String oneWord);

    List<Developer> searchForResumeAdvanced(SearchDto searchDto);
}
