package com.intinctools.service.employee;

import com.intinctools.entities.userEntites.User;

public interface EmployeeService {

    boolean saveEmployee(User user, String email);

    void editEmployee(User user, String country, String state,
                      String street, String zipPostalCode, String telephone);


    void addJob(User user, String title, String fullDescription, String shortDescription, String desiredExperience,
                Long fromSalary, Long toSalary, String company, String jobType, String salaryPeriod);
}
