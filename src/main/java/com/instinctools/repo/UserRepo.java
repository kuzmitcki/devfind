package com.instinctools.repo;

import com.instinctools.entities.userEntites.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String activationCode);
}
