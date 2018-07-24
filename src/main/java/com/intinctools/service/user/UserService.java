package com.intinctools.service.user;


import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService{
    private final UserRepo userRepo;

    private final MailSender mailSender;

    @Autowired
    public UserService(UserRepo userRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public boolean saveUser(User user, String devOrEmp) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setEnable(true);
        if (devOrEmp.equals("1")) {
            Developer developer = new Developer();
            developer.setUser(user);
            developer.setEnable(true);
            user.setDeveloper(developer);
            user.setRoles(Collections.singleton(Role.DEVELOPER));
        }
        if (devOrEmp.equals("2")) {
            Employee employee = new Employee();
            employee.setUser(user);
            user.setEmployee(employee);
            user.setRoles(Collections.singleton(Role.EMPLOYEE));
        }
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        mailSender.sendActivationCode(user);
        return true;
    }


}
