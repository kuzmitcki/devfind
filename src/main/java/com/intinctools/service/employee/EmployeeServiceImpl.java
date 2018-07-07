package com.intinctools.service.employee;

import com.intinctools.controllers.SearchForJobController;
import com.intinctools.entities.empEntites.Address;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.JobRepo;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Service
public class EmployeeServiceImpl extends UserService implements EmployeeService {
    private final UserRepo userRepo;
    private final JobRepo jobRepo;

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    public EmployeeServiceImpl(final UserRepo userRepo, JobRepo jobRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveEmployee(final User user, final String email) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        user.setEmployee(new Employee(email));
        userRepo.save(user);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editEmployee(User user, String country, String state, String street, String zipPostalCode, String telephone) {
        if (!country.equals("") && !state.equals("") && !street.equals("")) {
            Employee employee = user.getEmployee();
            Set<Address> addresses = new HashSet<>(employee.getAddresses());
            employee.getAddresses().clear();
            if (zipPostalCode.equals("")) {
                zipPostalCode = null;
            }
            if (telephone.equals("")) {
                telephone = null;
            }
            addresses.add(new Address(country , state , street , zipPostalCode , telephone));
            employee.setAddresses(addresses);
            user.setEmployee(employee);
            userRepo.save(user);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)

    public void addJob(User user, String title, String fullDescription,
                       String shortDescription, String desiredExperience, Long fromSalary,
                       Long toSalary, String company, String jobType, String salaryPeriod) {
        Employee employee = user.getEmployee();
        Set<Job> jobs = new HashSet<>(employee.getJobs());
        jobs.add(new Job(title, fullDescription,
                shortDescription, desiredExperience,
                fromSalary, toSalary, company,
                jobType, salaryPeriod));
        employee.setJobs(jobs);
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean checkEmployeeEditing(User user, Long id) {
        return user.getEmployee().equals(jobRepo.getById(id).getEmployee());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editJob(User user, String title, String fullDescription,
                        String shortDescription, String desiredExperience, String fromSalary,
                        String toSalary, String company, String jobType, String salaryPeriod, Long id) {
        Job job = jobRepo.getById(id);
        if (title.equals("")){
            title = job.getTitle();
        }
        if (fullDescription.equals("")) {
            fullDescription = job.getFullDescription();
        }
        if (shortDescription.equals("")) {
            shortDescription = job.getShortDescription();
        }
        if (desiredExperience.equals("")) {
            desiredExperience = job.getDesiredExperience();
        }
        if (fromSalary.equals("")){
            fromSalary = String.valueOf(job.getFromSalary());
        }
        if (toSalary.equals("")){
            toSalary = String.valueOf(job.getToSalary());
        }
        if (company.equals("")){
            company = job.getCompany();
        }
        if (jobType.equals("")) {
            jobType = job.getJobType();
        }
        if (salaryPeriod.equals("")){
            salaryPeriod = job.getSalaryPeriod();
        }
        job.setCompany(company);
        job.setDesiredExperience(desiredExperience);
        job.setFromSalary(Long.valueOf(fromSalary));
        job.setTitle(title);
        job.setFullDescription(fullDescription);
        job.setShortDescription(shortDescription);
        job.setToSalary(Long.valueOf(toSalary));
        job.setJobType(jobType);
        job.setSalaryPeriod(salaryPeriod);
        jobRepo.save(job);

    }
}
