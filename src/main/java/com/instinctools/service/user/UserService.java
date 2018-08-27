package com.instinctools.service.user;


import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.userEntites.Role;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.service.mail.MailServiceSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailServiceSender mailSender;

    @Autowired
    public UserService(final UserRepo userRepo,
                       final MailServiceSender mailSender,
                       final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean activateUser(final String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public boolean saveUser(final User user,
                            final String devOrEmp) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setEnable(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if ("1".equals(devOrEmp)) {
            Developer developer = new Developer();
            developer.setUser(user);
            developer.setEnable(true);
            user.setDeveloper(developer);
            user.setRoles(Collections.singletonList(Role.DEVELOPER));
        }
        if ("2".equals(devOrEmp)) {
            Employee employee = new Employee();
            employee.setUser(user);
            user.setEmployee(employee);
            user.setRoles(Collections.singletonList(Role.EMPLOYEE));
        }
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);
        mailSender.sendActivationCode(user);
        return true;
    }
}
