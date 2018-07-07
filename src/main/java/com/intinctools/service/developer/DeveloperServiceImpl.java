package com.intinctools.service.developer;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.Specialization;
import com.intinctools.entities.empEntites.Address;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.AddressRepo;
import com.intinctools.repo.JobRepo;
import com.intinctools.repo.UserRepo;
import com.intinctools.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;


@Service
public class DeveloperServiceImpl extends UserService implements DeveloperService {


    private final UserRepo userRepo;
    private final JobRepo jobRepo;
    private final AddressRepo addressRepo;


    public DeveloperServiceImpl(UserRepo userRepo, JobRepo jobRepo, AddressRepo addressRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveDeveloper(User user, String email) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.DEVELOPER));
        if (email.equals("")) {
            user.setDeveloper(new Developer());
        } else {
            user.setDeveloper(new Developer(email));
        }
        userRepo.save(user);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editDeveloper(User user, String firstName, String lastName,
                              String company, String email, String date, String gender) {
        Developer developer = user.getDeveloper();
        if (firstName.equals("")) {
            firstName = developer.getFirstName();
        }
        if (lastName.equals("")) {
            lastName = developer.getLastName();
        }
        if (company.equals("")) {
            company = developer.getCompany();
        }
        if (email.equals("")) {
            email = developer.getEmail();
        }
        if (date.equals("")) {
            date = String.valueOf(developer.getBirthday());
        }
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setCompany(company);
        developer.setEmail(email);
        developer.setGender(gender);
        developer.setEnable(true);
        developer.setBirthday(Date.valueOf(date));
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editSpecialization(User user, String skill) {
        Developer developer = user.getDeveloper();
        Set<Specialization> specializations = new HashSet<>(developer.getSpecializations());
        developer.getSpecializations().clear();
        if (!skill.equals("")) {
            specializations.add(new Specialization(skill));
        }
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByTitle(String title) {
        if (title.equals("")) {
            return Collections.emptyList();
        }
        return jobRepo.findByTitleIgnoreCaseContaining(title);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByDesiredExperience(String experience) {
        if (experience.equals("")) {
            return Collections.emptyList();
        }
        return jobRepo.findByDesiredExperienceIgnoreCaseLike("%" + experience + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByDescription(String jobDescription) {
        if (jobDescription.equals("")) {
            return Collections.emptyList();
        }
        jobDescription = jobDescription.trim().replaceAll(" +", " ");
        String[] words = jobDescription.split("\\s");
        List<Job> jobs = new LinkedList<>();
        for (String word : words) {
            jobs.addAll(jobRepo.findByFullDescriptionIgnoreCaseContaining(word));
        }
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByCompany(String company) {
        if (company.equals("")) {
            return Collections.emptyList();
        }
        return jobRepo.findByCompanyIgnoreCaseContaining(company);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesBySalaryPeriod(String salaryPeriod) {
        if (salaryPeriod.equals("")) {
            return Collections.emptyList();
        }
        return jobRepo.findBySalaryPeriod(salaryPeriod);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findEmployeeBySalary(Long fromSalary, Long toSalary) {
        if (String.valueOf(fromSalary).equals("") && !String.valueOf(toSalary).equals("")){
            return jobRepo.findByToSalaryLessThanEqual(toSalary);
        }
        if (String.valueOf(toSalary).equals("") && !String.valueOf(fromSalary).equals("")){
            return jobRepo.findByFromSalaryGreaterThanEqual(fromSalary);
        }
        if (String.valueOf(fromSalary).equals("") && String.valueOf(toSalary).equals("")){
            return (Set<Job>) jobRepo.findAll();
        }
        if (!String.valueOf(fromSalary).equals("") && !String.valueOf(toSalary).equals("")){
            return jobRepo.findByFromSalaryAndToSalaryBetween(fromSalary, toSalary);
        }
        return Collections.emptySet();
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findEmployeesByJobDescription(String jobDescription) {
        if (findEmployeesByTitle(jobDescription).isEmpty()) {
            if (findEmployeesByDesiredExperience(jobDescription).isEmpty()) {
                if (findEmployeesByDescription(jobDescription).isEmpty()) {
                    return Collections.emptySet();
                } else {
                    return new HashSet<>(findEmployeesByDescription(jobDescription));
                }
            } else {
                Set<Job> jobSet = new HashSet<>(findEmployeesByDescription(jobDescription));
                jobSet.addAll(findEmployeesByDesiredExperience(jobDescription));
                return jobSet;
            }
        } else {
            return new HashSet<>(findEmployeesByTitle(jobDescription));
        }
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Address> findEmployeesByState(String state) {
        return addressRepo.findByStateIgnoreCaseLike("%" + state + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Address> findEmployeesByCountry(String country) {
        return addressRepo.findByCountryIgnoreCaseLike("%" + country + "%");
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Address> findEmployeesByZipPostalCode(String code) {
        if (code.equals("")) {
            return Collections.emptyList();
        }
        return addressRepo.findByZipPostalCode(code);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Address> findEmployeesByAddress(String jobLocation) {
        if (findEmployeesByCountry(jobLocation).isEmpty()) {
            if (findEmployeesByState(jobLocation).isEmpty()) {
                if (findEmployeesByZipPostalCode(jobLocation).isEmpty()) {
                    return Collections.emptySet();
                } else {
                    return new HashSet<>(findEmployeesByZipPostalCode(jobLocation));
                }
            } else {
                List<Address> employeesByState = findEmployeesByState(jobLocation);
                employeesByState.addAll(findEmployeesByZipPostalCode(jobLocation));
                return new HashSet<>(employeesByState);
            }
        } else {
            return new HashSet<>(findEmployeesByCountry(jobLocation));
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findWithAddress(String jobDescription, String jobLocation) {
        if (jobDescription.equals("") && jobDescription.equals("")) {
            return Collections.emptySet();
        }
        if (!jobDescription.equals("") && jobLocation.equals("")) {
            return findEmployeesByJobDescription(jobDescription);
        }
        if (jobDescription.equals("") && !jobLocation.equals("")) {
            Set<Address> employeesAddress = findEmployeesByAddress(jobLocation);
            Set<Employee> employees = new HashSet<>();
            Set<Job> jobs = new HashSet<>();
            for (Address address : employeesAddress) {
                employees.add(address.getEmployee());
            }
            for (Employee employee : employees) {
                jobs.addAll(employee.getJobs());
            }
            return jobs;
        }
        if (!jobDescription.equals("") && !jobLocation.equals("")) {
            Set<Address> employeesAddress = findEmployeesByAddress(jobLocation);
            Set<Job> employeesByJobDescription = findEmployeesByJobDescription(jobDescription);
            Set<Job> jobs = new HashSet<>();
            for (Job job : employeesByJobDescription) {
                for (Address address : employeesAddress) {
                    if (job.getEmployee() == (address.getEmployee())) {
                        jobs.add(job);
                    }
                }
            }
            return jobs;

        }
        return Collections.emptySet();
    }
}








