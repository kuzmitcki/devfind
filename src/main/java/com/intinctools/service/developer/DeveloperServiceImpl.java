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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        return jobRepo.findByTitleIgnoreCaseLike("%" + title + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByDesiredExperience(String experience) {
        if (experience.equals("")) {
            return Collections.emptyList();
        }
        experience = experience.trim().replaceAll(" +", " ");
        String[] words = experience.split("\\s");
        List<Job> jobs = new LinkedList<>();
        for (String word : words) {
            jobs.addAll(jobRepo.findByDesiredExperienceIgnoreCaseLike("%"+ word +"%"));
        }
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findEmployeesByDescription(String jobDescription) {
        if (jobDescription.equals("")) {
            return Collections.emptySet();
        }
        jobDescription = jobDescription.trim().replaceAll(" +", " ");
        String[] words = jobDescription.split("\\s");
        Set<Job> jobs = new HashSet<>();
        for (String word : words) {
            jobs.addAll(jobRepo.findByFullDescriptionIgnoreCaseLike("%" + word + "%"));
        }
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByCompany(String company) {
        if (company.equals("")) {
            return Collections.emptyList();
        }
        return jobRepo.findByCompanyIgnoreCaseLike("%" + company + "%");
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
    public Collection<Job> findEmployeeBySalary(String fromSalary, String toSalary) {
        if (fromSalary.equals("") && !toSalary.equals("")){
            return jobRepo.findByToSalaryLessThanEqual(Long.valueOf(toSalary));
        }
        if (!fromSalary.equals("") && toSalary.equals("")){
            return jobRepo.findByFromSalaryGreaterThanEqual(Long.valueOf(fromSalary));
        }
        if (fromSalary.equals("") && toSalary.equals("")){
            return jobRepo.findAll();
        }
        if (!fromSalary.equals("") && !toSalary.equals("")){
            Set<Job> fromSalarySet = jobRepo.findByFromSalaryGreaterThanEqual(Long.valueOf(fromSalary));
            Set<Job> toSalarySet = jobRepo.findByToSalaryLessThanEqual(Long.valueOf(toSalary));
            Set<Job> jobs = new HashSet<>();
            for (Job fromSalaryJob : toSalarySet) {
                for (Job toSalaryJob : fromSalarySet) {
                    if (fromSalaryJob.equals(toSalaryJob)){
                        jobs.add(fromSalaryJob);
                    }
                }
            }
            return jobs;
        }
        return Collections.emptySet();
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findEmployeesByJobDescription(String jobDescription) {
       if (findEmployeesByTitle(jobDescription).isEmpty()){
           if (findEmployeesByDesiredExperience(jobDescription).isEmpty()){
               if (findEmployeesByDescription(jobDescription).isEmpty()){
                   return Collections.emptySet();
               }else {
                   return new HashSet<>(findEmployeesByDescription(jobDescription));
               }
           }else {
               HashSet<Job> jobs = new HashSet<>(findEmployeesByDesiredExperience(jobDescription));
               jobs.addAll(findEmployeesByDescription(jobDescription));
               return jobs;
           }
       }else {
           return new HashSet<>(findEmployeesByTitle(jobDescription));
       }
    }

    @Override
    public Set<Job> findEmployeesBySalaryAndSalaryPeriod(String fromSalary, String toSalary, String salaryPeriod) {
        Set<Job> employeesBySalaryPeriod = new HashSet<>(findEmployeesBySalaryPeriod(salaryPeriod));
        Collection<Job> employeeBySalary = findEmployeeBySalary(fromSalary, toSalary);
        Set<Job> jobWithSalaryAndPeriod  = new HashSet<>();
        for (Job job : employeeBySalary) {
            for (Job salaryPeriodJob: employeesBySalaryPeriod) {
                if (job.equals(salaryPeriodJob)){
                    jobWithSalaryAndPeriod.add(job);
                }
            }
        }
        return jobWithSalaryAndPeriod;
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
    public Set<Job> advancedSearch(String title, String salaryPeriod, String company, String keywords,
                                   String fullDescription, String location,
                                   String fromSalary, String toSalary) {
        Set<Job> employeesByDescription = findEmployeesByDescription(fullDescription);
        Set<Job> employeesBySalaryAndSalaryPeriod = findEmployeesBySalaryAndSalaryPeriod(fromSalary, toSalary, salaryPeriod);
        Collection<Job> employeeBySalary = findEmployeeBySalary(fromSalary, toSalary);
        List<Job> employeesByCompany = findEmployeesByCompany(company);
        Set<Job> withAddressKeywords = findWithAddress(keywords, location);
        Set<Job> withAddressTitle = findWithAddress(title, location);
        Set<Job> jobs = new HashSet<>();
        if (!withAddressKeywords.isEmpty()){
            for (Job withAddressKeyword : withAddressKeywords) {
                if (employeesByDescription.isEmpty()){
                    jobs.add(withAddressKeyword);
                }else {
                    for (Job byDescription : employeesByDescription) {
                        if (withAddressKeyword.equals(byDescription)){
                            jobs.add(withAddressKeyword);
                        }
                    }
                }
                if (!employeesBySalaryAndSalaryPeriod.isEmpty()){
                    for (Job salary : employeeBySalary) {
                        if (withAddressKeyword.equals(salary)){
                            jobs.add(withAddressKeyword);
                        }
                    }
                }
                for (Job companyName : employeesByCompany) {
                    if (withAddressKeyword.equals(companyName)){
                        jobs.add(withAddressKeyword);
                    }
                }
                for (Job withTitle : withAddressTitle) {
                    if (withAddressKeyword.equals(withTitle)){
                        jobs.add(withTitle);
                    }
                }
            }
        }
        return jobs;
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








