package com.instinctools.service.employee.edit;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.stereotype.Service;

@Service
public class JobEditionService implements EditJob{

    private final JobRepo jobRepo;
    private final UserRepo userRepo;
    private final EmployeeRepo employeeRepo;

    public JobEditionService(JobRepo jobRepo, UserRepo userRepo, EmployeeRepo employeeRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void editJobLocation(final User user, final String country, final String location, final Long id) {
        Job job = jobRepo.getOne(id);
        job.setJobLocation(location);
        job.setCountry(country);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDescription(final User user, final String description,
                                   final Long id) {
        Job job = jobRepo.getOne(id);
        job.setFullDescription(description);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDesiredDescription(final User user, final String experience,
                                          final Long id) {
        Job job = jobRepo.getOne(id);
        job.setDesiredExperience(experience);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobTitle(final User user, final String title, final Long id) {
        Job job = jobRepo.getOne(id);
        final Employee employee = user.getEmployee();
        job.setTitle(title);
        job.setEmployee(employee);
        employee.setJobs(jobRepo.findByEmployee(employee));
        employeeRepo.save(employee);
    }

    @Override
    public void editEmployeeCompany(final User user, final String company) {
        Employee employee = user.getEmployee();
        employee.setCompany(company);
        employeeRepo.save(employee);
    }

    @Override
    public void editJobQualification(final User user, final String qualification, final Long id) {
        Job job = jobRepo.getOne(id);
        job.setQualifications(qualification);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }
}
