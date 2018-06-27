package com.intinctools.repo;

import com.intinctools.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface DevRepo extends JpaRepository<Developer , Long> {

}
