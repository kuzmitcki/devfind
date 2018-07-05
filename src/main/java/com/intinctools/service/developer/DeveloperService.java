package com.intinctools.service.developer;

import com.intinctools.entities.userEntites.User;

public interface DeveloperService {

    boolean saveDeveloper(User user, String email);

    void editDeveloper(User user, String firstName, String lastName,
                       String company, String email,
                       String date, String gender);

    void editSpecialization(User user, String skill);
}
