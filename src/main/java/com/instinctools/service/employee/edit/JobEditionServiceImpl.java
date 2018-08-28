package com.instinctools.service.employee.edit;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.stereotype.Service;

@Service
public class JobEditionServiceImpl implements EditJobService {
    private final JobRepo jobRepo;
    private final EmployeeRepo employeeRepo;

    public JobEditionServiceImpl(final JobRepo jobRepo,
                                 final EmployeeRepo employeeRepo) {
        this.jobRepo = jobRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void editJobLocation(final JobDto jobDto,
                                final Long id) {
        Job job = jobRepo.getOne(id);
        job.setJobLocation(jobDto.getJobLocation());
        job.setCountry(jobDto.getCountry());
        jobRepo.save(job);
    }

    @Override
    public void editJobDescription(final JobDto jobDto,
                                   final Long id) {
        Job job = jobRepo.getOne(id);
        job.setFullDescription(jobDto.getFullDescription());
        jobRepo.save(job);
    }

    @Override
    public void editJobDesiredDescription(final JobDto jobDto,
                                          final Long id) {
        Job job = jobRepo.getOne(id);
        job.setDesiredExperience(jobDto.getDesiredExperience());
        jobRepo.save(job);
    }

    @Override
    public void editJobTitle(final JobDto jobDto,
                             final Long id) {
        Job job = jobRepo.getOne(id);
        job.setTitle(jobDto.getTitle());
        jobRepo.save(job);
    }

    @Override
    public void editEmployeeCompany(final User user,
                                    final UserDto userDto) {
        Employee employee = user.getEmployee();
        employee.setCompany(userDto.getCompany());
        employeeRepo.save(employee);
    }

    @Override
    public void editJobQualification(final JobDto jobDto,
                                     final Long id) {
        Job job = jobRepo.getOne(id);
        job.setQualifications(jobDto.getQualifications());
        jobRepo.save(job);
    }
}
