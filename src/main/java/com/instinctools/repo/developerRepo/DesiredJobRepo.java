package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.DesiredJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DesiredJobRepo extends JpaRepository<DesiredJob, Long> {
    Set<DesiredJob> findByDesiredJobTitleIgnoreCaseLike(String desiredJobTitleLike);
}
