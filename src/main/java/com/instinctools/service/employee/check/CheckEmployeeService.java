package com.instinctools.service.employee.check;

import com.instinctools.entities.userEntites.User;

public interface CheckEmployeeService {
    boolean checkEmployeeEditing(User user, Long id);
}
