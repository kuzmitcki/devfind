package com.intinctools.service.developer;

import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;

import java.util.Set;

public interface DeveloperService {
    void setBasicQualities(User user, String firstName,String lastName,
                           String country, String city,
                           String zipPostalCode, String telephone);
    void setEducation(User user, String degree, String place, String fieldOfStudy, String city,
                      String monthFrom, String monthTo, String yearFrom, String yearTo);

    void setWorkExperience(User user, String jobTitle, String company, String city,
                           String monthFrom, String monthTo, String yearFrom,
                           String yearTo, String description, String check);

    boolean checkDeveloperEditingWork(User user, Long id);

    boolean checkDeveloperEditingEducation(User user, Long id);

    void setDeveloperSummary(User user, String summary);

    void setDeveloperAdditional(User user, String additional);

    void editResumeBasicInformation(User user, String country, String city, String telephone, String zipPostalCode, String email);

    void setDeveloperEducation(User user, String degree, String place, String field,
                               String city, String monthFrom, String monthTo,
                               String yearFrom, String yearTo, Long id);

    void deleteDeveloperEducation(User user, Long id);

    void editDeveloperWorkExperience(User user, Long id, String jobTitle, String company,
                                     String city, String monthFrom, String monthTo,
                                     String yearFrom, String yearTo, String description);

    void deleteDeveloperWork(User user, Long id);

    void setDeveloperSkill(User user, String skill, String year);

    void deleteDeveloperSkill(User user, Long id);

    void setDesiredJob(User user, String title, String salary, String jobType, String salaryPeriod);

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
