package com.intinctools.service.developer;

import com.intinctools.entities.empEntites.Address;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;

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
    List<Job> findEmployeesByDescription(String jobDescription);
    List<Job> findEmployeesByCompany(String company);
    List<Job> findEmployeesBySalaryPeriod(String salaryPeriod);
    Set<Job> findEmployeeBySalary(Long fromSalary, Long toSalary);
    Set<Job> findEmployeesByJobDescription(String jobDescription);

    List<Address> findEmployeesByState(String state);
    List<Address> findEmployeesByCountry(String country);
    List<Address> findEmployeesByZipPostalCode(String code);
    Set<Address> findEmployeesByAddress(String jobLocation);




    Set<Job> findWithAddress(String jobDescription, String jobLocation);

}
