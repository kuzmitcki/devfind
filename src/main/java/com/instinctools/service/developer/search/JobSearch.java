package com.instinctools.service.developer.search;

import com.instinctools.entities.empEntites.Job;

import java.util.Set;

public interface JobSearch {
    Set<Job> searchForJobByTitle(String title);

    Set<Job> searchForJobByQualifications(String qualifications);

    Set<Job> searchForJobByCompany(String company);

    Set<Job> searchForJob(String description);

    Set<Job> searchForJobByLocation(String location);

    Set<Job> searchForJob(String whatDescription, String whereDescription);

    Set<Job> searchForJobByDesiredExperience(String desiredExperience);

    Set<Job> searchForJobByDescription(String description);

    Set<Job> searchForJobByOneWord (String oneWord);

    Set<Job> searchForJobByAllWords (String allWords);

    Set<Job> searchFroJobByPhrase (String phrase);

    Set<Job> searchForJobByWordsInTitle(String title);

    Set<Job> searchForJobByJobType(String jobType);

    Set<Job> searchForJobBySalaryAndPeriod(String salary);

    Set<Job> searchForJobAdvanced(String allWords, String phrase, String oneWord,
                                  String title, String jobType, String salary);
}
