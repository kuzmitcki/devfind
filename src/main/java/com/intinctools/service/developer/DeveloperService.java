package com.intinctools.service.developer;

import com.intinctools.entities.empEntites.EmployeeAddress;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DeveloperService {

    boolean saveDeveloper(User user, String email);
    void setBasicQualities(User user, String firstName,String lastName,
                           String country, String city,
                           String zipPostalCode, String telephone);
    void setEducation(User user, String degree, String place, String fieldOfStudy, String city,
                      String monthFrom, String monthTo, String yearFrom, String yearTo);

    void setWorkExperience(User user, String jobTitle, String company, String city,
                           String monthFrom, String monthTo, String yearFrom,
                           String yearTo, String description, String check);




    void editDeveloper(User user, String firstName, String lastName,
                       String company, String email);

    void editSpecialization(User user, String skill);

    boolean checkDeveloperEditing(User user, Long id);
    List<Job> findEmployeesByTitle(String title);
    List<Job> findEmployeesByDesiredExperience(String experience);
    Set<Job> findEmployeesByDescription(String jobDescription);
    List<Job> findEmployeesByCompany(String company);
    List<Job> findEmployeesBySalaryPeriod(String salaryPeriod);
    Collection<Job> findEmployeeBySalary(String fromSalary, String toSalary);
    Set<Job> findEmployeesByJobDescription(String jobDescription);

    Set<Job> findEmployeesBySalaryAndSalaryPeriod(String fromSalary, String toSalary, String salaryPeriod);
    List<EmployeeAddress> findEmployeesByState(String state);
    List<EmployeeAddress> findEmployeesByCountry(String country);
    List<EmployeeAddress> findEmployeesByZipPostalCode(String code);

    Set<EmployeeAddress> findEmployeesByAddress(String jobLocation);



    Set<Job> advancedSearch(String title, String salaryPeriod, String company,
                            String keywords, String fullDescription, String location,
                            String fromSalary, String toSalary);

    Set<Job> findWithAddress(String jobDescription, String jobLocation);

}
