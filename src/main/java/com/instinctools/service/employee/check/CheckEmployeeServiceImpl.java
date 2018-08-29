package com.instinctools.service.employee.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.exceptions.JobNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CheckEmployeeServiceImpl implements CheckEmployeeService {
    private final JobRepo jobRepo;

    public CheckEmployeeServiceImpl(final JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public boolean checkEmployeeEditing(final User user,
                                        final Long id) throws JobNotFoundException {
        jobRepo.findById(id).orElseThrow(() -> new JobNotFoundException("Cannot find job with id " + id));
        return user.getEmployee().equals(jobRepo.getOne(id).getEmployee());
    }
}
