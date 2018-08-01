package com.instinctools.service.employee.adding;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.empEntites.dto.JobDTO;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class AddingJobService implements AddingJob {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    public AddingJobService(JobRepo jobRepo, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void setEmployeeDescription(final User user, final String desiredExperience,
                                       final String fullDescription, final HttpServletRequest request) {
        Job job = (Job) request.getSession().getAttribute("job");
        job.setFullDescription(fullDescription);
        job.setDesiredExperience(desiredExperience);
        Employee employee = user.getEmployee();
        Set<Job> jobs = new HashSet<>(employee.getJobs());
        employee.getJobs().clear();
        jobs.add(job);
        employee.setJobs(jobs);
        user.setEmployee(employee);
        jobRepo.save(job);
        userRepo.save(user);
    }

    @Override
    public void setEmployeeJobSalary(final String jobType, final String fromSalary, final String toSalary,
                                     final Long salaryPeriod, final String qualifications,
                                     final RedirectAttributes redirectJob, final HttpServletRequest request) {
        Job job = (Job) request.getSession().getAttribute("attribute");
        job.setJobType(jobType);
        job.setFromSalary(Long.valueOf(fromSalary) * salaryPeriod);
        job.setToSalary(Long.valueOf(toSalary) * salaryPeriod);
        job.setSalaryPeriod(salaryPeriod);
        job.setQualifications(qualifications);
        redirectJob.addFlashAttribute("job", job);
    }

    @Override
    public void setEmployeeBasicInformation(final User user, final RedirectAttributes redirectedJob, final JobDTO jobDTO, String company) {
        Job job = new Job();
        ModelMapper mapper = new ModelMapper();
        mapper.map(jobDTO, job);
        Employee employee = user.getEmployee();
        employee.setCompany(company);
        user.setEmployee(employee);
        userRepo.save(user);
        redirectedJob.addFlashAttribute("job", job);
    }

    @Override
    public void setEmployeeAccountInformation(final User user, final String company, final String name,
                                              String phone,  String email) {
        Employee employee = user.getEmployee();
        employee.setName(name);
        if (phone.isEmpty()) {
            phone = employee.getPhone();
        }
        if (email.isEmpty()) {
            email = user.getEmail();
        }
        employee.setCompany(company);
        employee.setPhone(phone);
        user.setEmail(email);
        user.setEmployee(employee);
        userRepo.save(user);
    }



}
