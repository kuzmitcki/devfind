package com.instinctools.service.employee.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.service.exceptions.JobNotFoundException;

public interface CheckEmployeeService {
    boolean checkEmployeeEditing(User user, Long id) throws JobNotFoundException;
}
