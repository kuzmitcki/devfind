package com.intinctools.service.developer;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.Specialization;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Service
public class DeveloperServiceImpl extends UserService implements DeveloperService {


    private final UserRepo userRepo;


    public DeveloperServiceImpl(UserRepo userRepo) {
        super(userRepo);
        this.userRepo = userRepo;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveDeveloper(User user , String email) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.DEVELOPER));
        if (email.equals("")) {
            user.setDeveloper(new Developer());
        } else {
            user.setDeveloper(new Developer(email));
        }
        userRepo.save(user);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editDeveloper(User user, String firstName, String lastName,
                              String company, String email, String date, String gender) {
        Developer developer = user.getDeveloper();
        if (firstName.equals("")) {
            firstName = developer.getFirstName();
        }
        if (lastName.equals("")) {
            lastName = developer.getLastName();
        }
        if (company.equals("")) {
            company = developer.getCompany();
        }
        if (email.equals("")) {
            email = developer.getEmail();
        }
        if(date.equals("")) {
            date = String.valueOf(developer.getBirthday());
        }
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setCompany(company);
        developer.setEmail(email);
        developer.setGender(gender);
        developer.setEnable(true);
        developer.setBirthday(Date.valueOf(date));
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editSpecialization(User user, String skill) {
        Developer developer = user.getDeveloper();
        Set<Specialization> specializations = new HashSet<>(developer.getSpecializations());
        developer.getSpecializations().clear();
        if (!skill.equals("")) {
            specializations.add(new Specialization(skill));
        }
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }


}
