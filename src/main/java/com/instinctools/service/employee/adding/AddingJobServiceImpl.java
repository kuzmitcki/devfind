package com.instinctools.service.employee.adding;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.controllers.Dto.JobDto;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;


@Service
public class AddingJobServiceImpl implements AddingJobService {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;

    public AddingJobServiceImpl(final JobRepo jobRepo,
                                final UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void setEmployeeDescription(final User user,
                                       final JobDto jobDto,
                                       final HttpServletRequest request) {
        Job job = (Job) request.getSession().getAttribute("job");
        job.setFullDescription(jobDto.getFullDescription());
        job.setDesiredExperience(jobDto.getDesiredExperience());
        job.setEmployee(user.getEmployee());
        user.getEmployee().getJobs().add(job);
        jobRepo.save(job);
    }

    @Override
    public void setEmployeeJobSalary(final JobDto jobDto,
                                     final RedirectAttributes redirectJob,
                                     final HttpServletRequest request) {
        Job job = (Job) request.getSession().getAttribute("attribute");
        job.setJobType(jobDto.getJobType());
        job.setFromSalary(jobDto.getFromSalary() * jobDto.getSalaryPeriod());
        job.setToSalary(jobDto.getToSalary() * jobDto.getSalaryPeriod());
        job.setSalaryPeriod(jobDto.getSalaryPeriod());
        job.setQualifications(jobDto.getQualifications());
        redirectJob.addFlashAttribute("job", job);
    }

    @Override
    public void setEmployeeBasicInformation(final User user,
                                            final RedirectAttributes redirectedJob,
                                            final JobDto jobDto) {
        Job job = new Job();

        job.setTitle(jobDto.getTitle());
        job.setJobLocation(jobDto.getJobLocation());
        job.setCountry(jobDto.getCountry());

        Employee employee = user.getEmployee();
        employee.setCompany(jobDto.getCompany());
        user.setEmployee(employee);

        userRepo.save(user);

        redirectedJob.addFlashAttribute("job", job);
    }

    @Override
    public void setEmployeeAccountInformation(final User user,
                                              final UserDto userDto) {
        Employee employee = user.getEmployee();
        employee.setName(userDto.getName());
        String telephone = userDto.getTelephone();
        String email = userDto.getEmail();
        if (userDto.getTelephone().isEmpty()) {
            telephone = employee.getPhone();
        }
        if (email.isEmpty()) {
            email = user.getEmail();
        }
        employee.setCompany(userDto.getCompany());
        employee.setPhone(telephone);
        user.setEmail(email);
        user.setEmployee(employee);
        userRepo.save(user);
    }
}
