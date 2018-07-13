package com.intinctools.service.employee;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.devEntities.DeveloperAddress;
import com.intinctools.entities.devEntities.Specialization;
import com.intinctools.entities.empEntites.EmployeeAddress;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.*;
import com.intinctools.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class EmployeeServiceImpl extends UserService implements EmployeeService {
    private final UserRepo userRepo;
    private final JobRepo jobRepo;
    private final DeveloperAddressRepo developerAddressRepo;
    private final SpecializationRepo specializationRepo;
    private final DeveloperRepo developerRepo;

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    public EmployeeServiceImpl(final UserRepo userRepo, JobRepo jobRepo, DeveloperAddressRepo developerAddressRepo, SpecializationRepo specializationRepo, DeveloperRepo developerRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.developerAddressRepo = developerAddressRepo;
        this.specializationRepo = specializationRepo;
        this.developerRepo = developerRepo;
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
            Set<EmployeeAddress> addresses = new HashSet<>(employee.getAddresses());
            employee.getAddresses().clear();
            if (zipPostalCode.equals("")) {
                zipPostalCode = null;
            }
            if (telephone.equals("")) {
                telephone = null;
            }
            addresses.add(new EmployeeAddress(country , state , street , zipPostalCode , telephone));
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

    @Override
    public List<DeveloperAddress> findDevelopersByState(String city) {
        if (city.equals("")){
            return developerAddressRepo.findAll();
        }
        return developerAddressRepo.findByCityIgnoreCaseLike("%" + city + "%");
    }

    @Override
    public List<DeveloperAddress> findDevelopersByCountry(String country) {
        if (country.equals("")){
            return developerAddressRepo.findAll();
        }
        return developerAddressRepo.findByCountryIgnoreCaseLike("%" + country + "%");
    }

    @Override
    public List<DeveloperAddress> findDevelopersByZipPostalCode(String code) {
        if (code.equals("")){
            return developerAddressRepo.findAll();
        }
        return developerAddressRepo.findByZipPostalCode(code);
    }

    @Override
    public Set<DeveloperAddress> findDevelopersByAddress(String jobLocation) {
        if (findDevelopersByCountry(jobLocation).isEmpty()) {
            if (findDevelopersByState(jobLocation).isEmpty()) {
                if (findDevelopersByZipPostalCode(jobLocation).isEmpty()) {
                    return new HashSet<>(developerAddressRepo.findAll());
                } else {
                    return new HashSet<>(findDevelopersByZipPostalCode(jobLocation));
                }
            } else {
                List<DeveloperAddress> employeesByState = findDevelopersByState(jobLocation);
                employeesByState.addAll(findDevelopersByZipPostalCode(jobLocation));
                return new HashSet<>(employeesByState);
            }
        } else {
            return new HashSet<>(findDevelopersByCountry(jobLocation));
        }
    }

    @Override
    public Set<Developer> findDevelopersBySkills(String skills) {
        if (skills.equals("")) {
            List<Specialization> all = specializationRepo.findAll();
            Set<Developer> developers = new HashSet<>();
            for (Specialization specialization : all) {
                Developer developer = developerRepo.findBySpecializations(specialization);
                developers.add(developer);
                return developers;
            }
        }
        skills = skills.trim().replaceAll(" +", " ");
        String[] words = skills.split("\\s");
        Set<Developer> developers = new HashSet<>();
        for (String word : words) {
            List<Specialization> specializations = specializationRepo.findBySkillIgnoreCaseContaining("%" + word + "%");
            for (Specialization specialization : specializations) {
                developers.add(developerRepo.findBySpecializations(specialization));
            }
        }
        return  developers;
    }

    @Override
    public Set<Developer> findBySkillsWithAddress(String skills, String location) {
        if (skills.equals("") && location.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        if (skills.equals("") && !location.equals("")){
            Set<DeveloperAddress> developersByAddress = findDevelopersByAddress(location);
            Set<Developer> developers = new HashSet<>();
            for (DeveloperAddress byAddress : developersByAddress) {
                developers.add( byAddress.getDeveloper());
            }
            return developers;
        }
        if (!skills.equals("") && location.equals("")){
            return findDevelopersBySkills(skills);
        }
            Set<Developer> developersBySkills = findDevelopersBySkills(skills);
            Set<DeveloperAddress> developersByAddress = findDevelopersByAddress(location);
            Set<Developer> developers = new HashSet<>();
            Set<Developer> developersWithAddress = new HashSet<>();
            for (DeveloperAddress byAddress : developersByAddress){
                developers.add(byAddress.getDeveloper());
            }
            for (Developer developerSkills : developersBySkills) {
                for (Developer developer : developers) {
                    if (developerSkills.equals(developer)){
                        developersWithAddress.add(developerSkills);
                    }
                }
            }
            return developersWithAddress;
    }
}
