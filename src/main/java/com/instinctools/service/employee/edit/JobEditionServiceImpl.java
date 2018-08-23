package com.instinctools.service.employee.edit;

import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.stereotype.Service;

@Service
public class JobEditionServiceImpl implements EditJobService {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;

    public JobEditionServiceImpl(final JobRepo jobRepo,
                                 final UserRepo userRepo,
                                 final EmployeeRepo employeeRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void editJobLocation(final User user,
                                final JobDto jobDto,
                                final Long id) {
        Job job = jobRepo.getOne(id);
        job.setJobLocation(jobDto.getJobLocation());
        job.setCountry(jobDto.getCountry());
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDescription(final User user,
                                   final JobDto jobDto,
                                   final Long id) {
        Job job = jobRepo.getOne(id);
        job.setFullDescription(jobDto.getFullDescription());
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDesiredDescription(final User user,
                                          final JobDto jobDto,
                                          final Long id) {
        Job job = jobRepo.getOne(id);
        job.setDesiredExperience(jobDto.getDesiredExperience());
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobTitle(final User user,
                             final JobDto jobDto,
                             final Long id) {
        Job job = jobRepo.getOne(id);
        final Employee employee = user.getEmployee();
        job.setTitle(jobDto.getTitle());
        job.setEmployee(employee);
        employee.setJobs(jobRepo.findByEmployee(employee));
        employeeRepo.save(employee);
    }

    @Override
    public void editEmployeeCompany(final User user,
                                    final UserDto userDto) {
        Employee employee = user.getEmployee();
        employee.setCompany(userDto.getCompany());
        employeeRepo.save(employee);
    }

    @Override
    public void editJobQualification(final User user,
                                     final JobDto jobDto,
                                     final Long id) {
        Job job = jobRepo.getOne(id);
        job.setQualifications(jobDto.getQualifications());
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }
}
