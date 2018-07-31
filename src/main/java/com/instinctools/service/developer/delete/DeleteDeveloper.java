package com.instinctools.service.developer.delete;

import com.instinctools.entities.userEntites.User;

public interface DeleteDeveloper {


    void deleteDeveloperEducation(User user, Long id);

    void deleteDeveloperWork(User user, Long id);

    void deleteDeveloperSkill(User user, Long id);


}
