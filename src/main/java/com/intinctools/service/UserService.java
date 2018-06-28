package com.intinctools.service;

import com.intinctools.entities.Role;
import com.intinctools.entities.Specialization;
import com.intinctools.entities.User;
import com.intinctools.entities.WorkDay;
import com.intinctools.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    /**
     *Do something with this methods
     */

    public boolean saveEmployee(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb!=null){
            return false;
        }
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        userRepo.save(user);
        return true;
    }

    public boolean saveDeveloper(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb!=null){
            return false;
        }
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.DEVELOPER));
        userRepo.save(user);
        return true;
    }

    public void addInformation(User user, String skill, int end, int start, int schedule){
        Specialization specialization = new Specialization(skill);
        user.getSpecializations().clear();
        WorkDay workDay = user.getWorkDay();
        workDay.setEnd(end);
        workDay.setStart(start);
        workDay.setSchedule(schedule);
        user.getSpecializations().add(specialization);
        userRepo.save(user);
    }
}
