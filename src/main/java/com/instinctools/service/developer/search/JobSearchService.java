package com.instinctools.service.developer.search;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.empEntites.Job;

import java.util.List;
import java.util.Set;

public interface JobSearchService {
    List<Job> searchForJobByTitle(String title);

    List<Job> searchForJobByQualifications(String qualifications);

    List<Job> searchForJobByCompany(String company);

    List<Job> searchForJob(String description);

    List<Job> searchForJobByLocation(String location);

    List<Job> searchForJob(String whatDescription, String whereDescription);

    List<Job> searchForJobByDesiredExperience(String desiredExperience);

    List<Job> searchForJobByDescription(String description);

    List<Job> searchForJobByOneWord(String oneWord);

    List<Job> searchForJobByAllWords(String allWords);

    List<Job> searchFroJobByPhrase(String phrase);

    List<Job> searchForJobByWordsInTitle(String title);

    List<Job> searchForJobByJobType(String jobType);

    List<Job> searchForJobBySalaryAndPeriod(String salary);

    List<Job> searchForJobAdvanced(SearchDto searchDto);
}
