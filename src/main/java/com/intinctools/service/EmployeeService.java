package com.intinctools.service;

import com.intinctools.entities.Role;
import com.intinctools.entities.Specialization;
import com.intinctools.entities.User;
import com.intinctools.repo.UserRepo;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Service
public class EmployeeService implements UserDetailsService {

    private final UserRepo userRepo;


    @Autowired
    public EmployeeService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


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



    public List<User> searchForDevelopers(String skills){
        Iterable<User> byRoles = userRepo.findByRoles(Collections.singleton(Role.DEVELOPER));
        List<User> userBySkills = new LinkedList<>();
        for (User byRole : byRoles) {
            if (byRole.getSpecializations().toString().contains(skills)){
                userBySkills.add(byRole);
            }
        }
        return userBySkills;
    }


}
