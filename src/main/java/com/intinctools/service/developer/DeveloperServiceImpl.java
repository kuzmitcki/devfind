package com.intinctools.service.developer;

import com.intinctools.entities.devEntities.*;
import com.intinctools.entities.empEntites.EmployeeAddress;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.*;
import com.intinctools.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class DeveloperServiceImpl extends UserService implements DeveloperService {


    private final UserRepo userRepo;
    private final JobRepo jobRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final EmployeeAddressRepo addressRepo;
    private final SpecializationRepo specializationRepo;


    public DeveloperServiceImpl(UserRepo userRepo, JobRepo jobRepo, WorkExperienceRepo workExperienceRepo, EmployeeAddressRepo addressRepo, SpecializationRepo specializationRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.addressRepo = addressRepo;
        this.specializationRepo = specializationRepo;
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
    public void setBasicQualities(User user, String firstName, String lastName,
                                  String country, String city,
                                  String zipPostalCode, String telephone) {
        Developer developer = user.getDeveloper();
        if (!country.equals("")){
            if (zipPostalCode.equals("")){
                zipPostalCode = developer.getZipPostalCode();
            }
            if (telephone.equals("")){
                telephone = developer.getTelephone();
            }
            developer.setFirstName(firstName);
            developer.setLastName(lastName);
            developer.setCountry(country);
            developer.setCity(city);
            developer.setZipPostalCode(zipPostalCode);
            developer.setTelephone(telephone);
            user.setDeveloper(developer);
            userRepo.save(user);
        }
    }

    @Override
    public void setEducation(User user, String degree, String place, String fieldOfStudy,
                             String city, String monthFrom, String monthTo,
                             String yearFrom, String yearTo) {
      if (!degree.equals("") && !fieldOfStudy.equals("") && !place.equals("")){
          Developer developer = user.getDeveloper();
          Set<Education> educations = new HashSet<>(developer.getEducation());
          developer.getEducation().clear();
          educations.add(new Education(degree, place, fieldOfStudy, city, monthFrom, monthTo, yearFrom, yearTo, developer));
          developer.setEducation(educations);
          user.setDeveloper(developer);
          userRepo.save(user);
      }
    }

    @Override
    public void setWorkExperience(User user, String jobTitle, String company,
                                  String city, String monthFrom, String monthTo,
                                  String yearFrom, String yearTo, String description, String check) {
        Developer developer = user.getDeveloper();
        if (check != null){
            developer.setJobExperience(false);
            user.setDeveloper(developer);
            userRepo.save(user);
        }else {
                Set<WorkExperience> workExperiences = new HashSet<>(developer.getWorkExperiences());
                developer.getWorkExperiences().clear();
                developer.setJobExperience(true);
                workExperiences.add(new WorkExperience(jobTitle, company, city, monthFrom, monthTo, yearFrom, yearTo, description, developer));
                developer.setWorkExperiences(workExperiences);
                user.setDeveloper(developer);
                userRepo.save(user);

        }

    }

    //______________________________________________________________________//

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editDeveloper(User user, String firstName, String lastName,
                              String company, String email) {
        Developer developer = user.getDeveloper();
        if (firstName.equals("")) {
            firstName = developer.getFirstName();
        }
        if (lastName.equals("")) {
            lastName = developer.getLastName();
        }

        if (email.equals("")) {
            email = developer.getEmail();
        }
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setEmail(email);
        developer.setEnable(true);
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
    public boolean checkDeveloperEditing(User user, Long id) {
        return user.getDeveloper().equals(workExperienceRepo.getById(id).getDeveloper());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByTitle(String title) {
        if (title.equals("")) {
            return  jobRepo.findAll();
        }
        return jobRepo.findByTitleIgnoreCaseLike("%" + title + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesByDesiredExperience(String experience) {
        if (experience.equals("")) {
            return  jobRepo.findAll();
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
            return new HashSet<>(jobRepo.findAll());
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
            return jobRepo.findAll();
        }
        return jobRepo.findByCompanyIgnoreCaseLike("%" + company + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Job> findEmployeesBySalaryPeriod(String salaryPeriod) {
        if (salaryPeriod.equals("")) {
            return  jobRepo.findAll();
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
        return  jobRepo.findAll();
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findEmployeesByJobDescription(String jobDescription) {
       if (findEmployeesByTitle(jobDescription).isEmpty()){
           if (findEmployeesByDesiredExperience(jobDescription).isEmpty()){
               if (findEmployeesByDescription(jobDescription).isEmpty()){
                   return new HashSet<>(jobRepo.findAll());
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
    public List<EmployeeAddress> findEmployeesByState(String state) {
        return addressRepo.findByStateIgnoreCaseLike("%" + state + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<EmployeeAddress> findEmployeesByCountry(String country) {
        return addressRepo.findByCountryIgnoreCaseLike("%" + country + "%");
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<EmployeeAddress> findEmployeesByZipPostalCode(String code) {
        if (code.equals("")) {
            return  addressRepo.findAll();
        }
        return addressRepo.findByZipPostalCode(code);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<EmployeeAddress> findEmployeesByAddress(String jobLocation) {
        if (findEmployeesByCountry(jobLocation).isEmpty()) {
            if (findEmployeesByState(jobLocation).isEmpty()) {
                if (findEmployeesByZipPostalCode(jobLocation).isEmpty()) {
                    return new HashSet<>(addressRepo.findAll());
                } else {
                    return new HashSet<>(findEmployeesByZipPostalCode(jobLocation));
                }
            } else {
                List<EmployeeAddress> employeesByState = findEmployeesByState(jobLocation);
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
            return jobs;
        }
        jobs.addAll(withAddressTitle);
        jobs.addAll(employeesByDescription);
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> findWithAddress(String jobDescription, String jobLocation) {
        if (jobDescription.equals("") && jobDescription.equals("")) {
            return new HashSet<>(jobRepo.findAll());
        }
        if (!jobDescription.equals("") && jobLocation.equals("")) {
            return findEmployeesByJobDescription(jobDescription);
        }
        if (jobDescription.equals("") && !jobLocation.equals("")) {
            Set<EmployeeAddress> employeesAddress = findEmployeesByAddress(jobLocation);
            Set<Employee> employees = new HashSet<>();
            Set<Job> jobs = new HashSet<>();
            for (EmployeeAddress address : employeesAddress) {
                employees.add(address.getEmployee());
            }
            for (Employee employee : employees) {
                jobs.addAll(employee.getJobs());
            }
            return jobs;
        }
            Set<EmployeeAddress> employeesAddress = findEmployeesByAddress(jobLocation);
            Set<Job> employeesByJobDescription = findEmployeesByJobDescription(jobDescription);
            Set<Job> jobs = new HashSet<>();
            for (Job job : employeesByJobDescription) {
                for (EmployeeAddress address : employeesAddress) {
                    if (job.getEmployee() == (address.getEmployee())) {
                        jobs.add(job);
                    }
                }
            }
            return jobs;

    }
}








