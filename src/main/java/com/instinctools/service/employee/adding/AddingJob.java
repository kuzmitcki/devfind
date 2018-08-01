package com.instinctools.service.employee.adding;

import com.instinctools.entities.empEntites.dto.JobDTO;
import com.instinctools.entities.userEntites.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

public interface AddingJob {

    void setEmployeeDescription(User user, String desiredExperience, String fullDescription, HttpServletRequest request);

    void setEmployeeJobSalary(String jobType, String fromSalary, String toSalary,
                              Long salaryPeriod, String qualifications,
                              RedirectAttributes redirectJob, HttpServletRequest request);

    void setEmployeeBasicInformation(User user, RedirectAttributes redirectedJob, JobDTO jobDTO, String company);

    void setEmployeeAccountInformation(User user, String company, String name, String phone, String email);

}
