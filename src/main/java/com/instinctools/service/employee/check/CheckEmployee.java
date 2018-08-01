package com.instinctools.service.employee.check;

import com.instinctools.entities.userEntites.User;

public interface CheckEmployee {
    boolean checkEmployeeEditing(User user, Long id);
}
