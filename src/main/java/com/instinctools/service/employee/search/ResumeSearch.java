package com.instinctools.service.employee.search;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.userEntites.User;

import java.util.Set;

public interface ResumeSearch {
    Set<Developer> searchForResumeByTitleAndDesiredTitle(String title);

    Set<Developer> searchForResumeByWorkTitle(String title);

    Set<Developer> searchForResumeByWorkDescription(String description);

    Set<Developer> searchForResumeByCompany(String company);

    Set<Developer> searchForResumeBySkills(String skills);

    Set<Developer> searchForResumeByEducationPlace(String place);

    Set<Developer> searchForResumeByEducationDegree(String degree);

    Set<Developer> searchForResumeByFieldOfStudy(String fieldOfStudy);

    Set<Developer> searchForDeveloperByExperience(Long experience);

    Set<Developer> searchForResumeByDescription(String description);

    Set<Developer> searchForDeveloperByLocation(String location);

    Set<Developer> searchForResume(SearchDto searchDto);

    Set<Developer> searchForResumeByAdditionalInformation(String additional);

    Set<Developer> searchForResumeBySummary(String  summary);

    Set<Developer> searchForResumeByWords(String allWords);

    Set<Developer> searchForResumeByPhrase(String phrase);

    Set<Developer> searchForResumeByOneWord(String oneWord);

    Set<Developer> searchForResumeAdvanced(User user, SearchDto searchDto);

}
