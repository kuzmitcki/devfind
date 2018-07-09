package com.intinctools.service.developer;

import com.intinctools.entities.empEntites.Address;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DeveloperService {

    boolean saveDeveloper(User user, String email);

    void editDeveloper(User user, String firstName, String lastName,
                       String company, String email,
                       String date, String gender);
    void editSpecialization(User user, String skill);

    List<Job> findEmployeesByTitle(String title);
    List<Job> findEmployeesByDesiredExperience(String experience);
    Set<Job> findEmployeesByDescription(String jobDescription);
    List<Job> findEmployeesByCompany(String company);
    List<Job> findEmployeesBySalaryPeriod(String salaryPeriod);
    Collection<Job> findEmployeeBySalary(String fromSalary, String toSalary);
    Set<Job> findEmployeesByJobDescription(String jobDescription);
    Set<Job> findEmployeesBySalaryAndSalaryPeriod(String fromSalary, String toSalary, String salaryPeriod);

    List<Address> findEmployeesByState(String state);
    List<Address> findEmployeesByCountry(String country);
    List<Address> findEmployeesByZipPostalCode(String code);
    Set<Address> findEmployeesByAddress(String jobLocation);



    Set<Job> advancedSearch(String title, String salaryPeriod, String company,
                            String keywords, String fullDescription, String location,
                            String fromSalary, String toSalary);

    Set<Job> findWithAddress(String jobDescription, String jobLocation);

}
