package com.intinctools.repo;

import com.intinctools.entities.devEntities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepo extends JpaRepository<Developer , Long> {
}
