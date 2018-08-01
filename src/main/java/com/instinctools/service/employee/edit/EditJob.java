package com.instinctools.service.employee.edit;

import com.instinctools.entities.userEntites.User;

public interface EditJob {
    void editJobLocation(User user, String country, String location, Long id);

    void editJobDescription(User user, String description, Long id);

    void editJobDesiredDescription(User user, String experience, Long id);

    void editJobTitle(User user, String title, Long id);

    void editEmployeeCompany(User user, String company);

    void editJobQualification(User user, String qualification, Long id);

}
