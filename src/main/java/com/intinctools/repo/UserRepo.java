package com.intinctools.repo;

import com.intinctools.entities.Role;
import com.intinctools.entities.Specialization;
import com.intinctools.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Iterable<User> findByRoles(Set<Role> roles);
    List<User> findBySpecializations(String skill);
}
