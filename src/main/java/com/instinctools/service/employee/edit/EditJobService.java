package com.instinctools.service.employee.edit;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.userEntites.User;

public interface EditJobService {
    void editJobLocation(JobDto jobDto, Long id);

    void editJobDescription(JobDto jobDto, Long id);

    void editJobDesiredDescription(JobDto jobDto, Long id);

    void editJobTitle(JobDto jobDto, Long id);

    void editEmployeeCompany(User user, UserDto userDto);

    void editJobQualification(JobDto jobDto, Long id);
}
