package com.intinctools.service.employee;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.DeveloperAddress;
import com.intinctools.entities.empEntites.EmployeeAddress;
import com.intinctools.entities.userEntites.User;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    boolean saveEmployee(User user, String email);

    void editEmployee(User user, String country, String state,
                      String street, String zipPostalCode, String telephone);


    void addJob(User user, String title, String fullDescription, String shortDescription, String desiredExperience,
                Long fromSalary, Long toSalary, String company, String jobType, String salaryPeriod);

    boolean checkEmployeeEditing(User user, Long id);

    void editJob(User user, String title, String fullDescription, String shortDescription, String desiredExperience,
                 String fromSalary, String  toSalary, String company, String jobType, String salaryPeriod, Long id);

    List<DeveloperAddress> findDevelopersByState(String state);
    List<DeveloperAddress> findDevelopersByCountry(String country);
    List<DeveloperAddress> findDevelopersByZipPostalCode(String code);
    Set<DeveloperAddress> findDevelopersByAddress(String location);
    Set<Developer> findDevelopersBySkills(String skills);
    Set<Developer> findBySkillsWithAddress(String skills, String location);

}
