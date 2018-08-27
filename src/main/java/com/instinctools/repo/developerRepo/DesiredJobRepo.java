package com.instinctools.repo.developerRepo;

import com.instinctools.entities.devEntities.DesiredJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesiredJobRepo extends JpaRepository<DesiredJob, Long> {
    List<DesiredJob> findByDesiredJobTitleIgnoreCaseLike(String desiredJobTitleLike);
}
