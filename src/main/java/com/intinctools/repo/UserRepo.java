package com.intinctools.repo;

import com.intinctools.entities.userEntites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
