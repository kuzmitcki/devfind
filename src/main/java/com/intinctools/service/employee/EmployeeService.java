package com.intinctools.service.employee;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.userEntites.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface EmployeeService {
    void editJobLocation(User user, String country, String location, Long id);

    void editJobDescription(User user, String description, Long id);

    void editJobDesiredDescription(User user, String experience, Long id);

    void editJobTitle(User user, String title, Long id);

    void editEmployeeCompany(User user, String company);

    void editJobQualification(User user, String qualification, Long id);

    void setEmployeeDescription(User user, String desiredExperience,
                                String fullDescription, HttpServletRequest request);

    void setEmployeeJobSalary(String jobType, String fromSalary, String toSalary,
                              Long salaryPeriod, String qualifications,
                              RedirectAttributes redirectJob, HttpServletRequest request);

    void setEmployeeBasicInformation(User user, RedirectAttributes redirectedJob,
                                     String jobTitle, String company, String jobLocation, String country);

    void setEmployeeAccountInformation(User user, String company, String name, String phone, String email);


    boolean checkEmployeeEditing(User user, Long id);

    Set<Developer> searchForResumeByTitleAndDesiredTitle(String title);

    Set<Developer> searchForResumeByWorkTitle(String title);

    Set<Developer> searchForResumeByWorkDescription(String description);

    Set<Developer> searchForResumeByCompany(String company);

    Set<Developer> searchForResumeBySkills(String skills);

    Set<Developer> searchForResumeByCountry(String country);

    Set<Developer> searchForResumeByCity(String city);

    Set<Developer> searchForResumeByZipPostalCode(String zipPostalCode);

    Set<Developer> searchForResumeByEducationPlace(String place);

    Set<Developer> searchForResumeByEducationDegree(String degree);

    Set<Developer> searchForResumeByFieldOfStudy(String fieldOfStudy);

    Set<Developer> searchForDeveloperByExperience(Long experience);

    Set<Developer> searchForResumeByDescription(String description);

    Set<Developer> searchForDeveloperByLocation(String location);

    Set<Developer> searchForResume(String whatDescription, String whereDescription);

    Set<Developer> searchForResumeByAdditionalInformation(String additional);

    Set<Developer> searchForResumeBySummary(String  summary);

    Set<Developer> searchForResumeByWords(String allWords);

    Set<Developer> searchForResumeByPhrase(String phrase);

    Set<Developer> searchForResumeByOneWord(String oneWord);

    Set<Developer> searchForResumeAdvanced(User user, String allWords, String phrase, String oneWord,
                                           String title, String company, Long experience, String place,
                                           String degree, String field, String location);

}
