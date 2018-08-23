package com.instinctools.service.developer.check;

import com.instinctools.entities.userEntites.User;

public interface CheckDeveloperService {
    boolean checkDeveloperEditingWork(User user, Long id);

    boolean checkDeveloperEditingEducation(User user, Long id);
}
