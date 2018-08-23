package com.instinctools.service.employee.adding;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.entities.userEntites.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

public interface AddingJobService {

    void setEmployeeDescription(User user, JobDto jobDto, HttpServletRequest request);

    void setEmployeeJobSalary(JobDto jobDto, RedirectAttributes redirectJob, HttpServletRequest request);

    void setEmployeeBasicInformation(User user, RedirectAttributes redirectedJob, JobDto jobDTO);

    void setEmployeeAccountInformation(User user, UserDto userDto);
}
