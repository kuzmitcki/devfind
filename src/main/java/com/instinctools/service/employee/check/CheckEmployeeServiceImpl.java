package com.instinctools.service.employee.check;

import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.stereotype.Service;

@Service
public class CheckEmployeeServiceImpl implements CheckEmployeeService {
    private final JobRepo jobRepo;

    public CheckEmployeeServiceImpl(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public boolean checkEmployeeEditing(final User user,
                                        final Long id) {
        return user.getEmployee().equals(jobRepo.getOne(id).getEmployee());
    }

}
