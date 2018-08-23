package com.instinctools.service.employee.edit;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.userEntites.User;

public interface EditJobService {
    void editJobLocation(User user, JobDto jobDto, Long id);

    void editJobDescription(User user, JobDto jobDto, Long id);

    void editJobDesiredDescription(User user, JobDto jobDto, Long id);

    void editJobTitle(User user, JobDto jobDto, Long id);

    void editEmployeeCompany(User user, UserDto userDto);

    void editJobQualification(User user, JobDto jobDto, Long id);
}
