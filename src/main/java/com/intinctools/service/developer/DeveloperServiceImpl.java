package com.intinctools.service.developer;

import com.intinctools.entities.devEntities.*;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.repo.developerRepo.DesiredJobRepo;
import com.intinctools.repo.developerRepo.DeveloperRepo;
import com.intinctools.repo.developerRepo.EducationRepo;
import com.intinctools.repo.developerRepo.WorkExperienceRepo;
import com.intinctools.repo.employeeRepo.EmployeeRepo;
import com.intinctools.repo.employeeRepo.JobRepo;
import com.intinctools.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class DeveloperServiceImpl extends UserService implements DeveloperService {


    private final UserRepo userRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final EducationRepo educationRepo;
    private final DeveloperRepo developerRepo;
    private final DesiredJobRepo desiredJobRepo;
    private final JobRepo jobRepo;
    private final EmployeeRepo employeeRepo;



    public DeveloperServiceImpl(UserRepo userRepo, WorkExperienceRepo workExperienceRepo, EducationRepo educationRepo, DeveloperRepo developerRepo,
                                DesiredJobRepo desiredJobRepo, JobRepo jobRepo, EmployeeRepo employeeRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
        this.developerRepo = developerRepo;
        this.desiredJobRepo = desiredJobRepo;
        this.jobRepo = jobRepo;
        this.employeeRepo = employeeRepo;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean checkDeveloperEditingWork(User user, Long id) {
        return user.getDeveloper().equals(workExperienceRepo.getById(id).getDeveloper());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean checkDeveloperEditingEducation(User user, Long id) {
        return user.getDeveloper().equals(educationRepo.getById(id).getDeveloper());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDeveloperSummary(User user, String summary) {
        Developer developer = user.getDeveloper();
        developer.setSummary(summary);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDeveloperAdditional(User user, String additional) {
        Developer developer = user.getDeveloper();
        developer.setAdditionalInformation(additional);
        userRepo.save(user);

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editResumeBasicInformation(User user, String country, String city, String telephone, String zipPostalCode, String email) {
        Developer developer = user.getDeveloper();
        developer.setCountry(country);
        developer.setCity(city);
        developer.setTelephone(telephone);
        developer.setZipPostalCode(zipPostalCode);
        developer.setEmail(email);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDeveloperEducation(User user, String degree, String place, String field, String city,
                                      String monthFrom, String monthTo, String yearFrom, String yearTo, Long id) {
        Education education = educationRepo.getById(id);
        education.setDegree(degree);
        education.setPlace(place);
        education.setFieldOfStudy(field);
        education.setCityOfEducation(city);
        education.setMonthFrom(monthFrom);
        education.setMonthTo(monthTo);
        education.setYearFrom(yearFrom);
        education.setYearTo(yearTo);
        Developer developer = education.getDeveloper();
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteDeveloperEducation(User user, Long id) {
        Developer developer = user.getDeveloper();
        Set<Education> educations = new HashSet<>(developer.getEducation());
        developer.getEducation().clear();
        educations.removeIf(e -> e.getId().equals(id));
        developer.setEducation(educations);
        developerRepo.save(developer);
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editDeveloperWorkExperience(User user, Long id, String jobTitle, String company, String city,
                                            String monthFrom, String monthTo, String yearFrom,
                                            String yearTo, String description) {
        WorkExperience workExperience = workExperienceRepo.getById(id);
        workExperience.setJobTitle(jobTitle);
        workExperience.setCompany(company);
        workExperience.setCity(city);
        workExperience.setMonthFrom(monthFrom);
        workExperience.setMonthTo(monthTo);
        workExperience.setYearFrom(yearFrom);
        workExperience.setYearTo(yearTo);
        workExperience.setDescription(description);
        Developer developer = workExperience.getDeveloper();
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteDeveloperWork(User user, Long id) {
        Developer developer = user.getDeveloper();
        Set<WorkExperience> workExperiences = new HashSet<>(developer.getWorkExperiences());
        developer.getWorkExperiences().clear();
        workExperiences.removeIf(e -> e.getId().equals(id));
        developer.setWorkExperiences(workExperiences);
        developerRepo.save(developer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDeveloperSkill(User user, String skill, String year) {
        Set<Specialization> specializations = new HashSet<>(user.getDeveloper().getSpecializations());
        user.getDeveloper().getSpecializations().clear();
        specializations.add(new Specialization(skill, year));
        Developer developer = user.getDeveloper();
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteDeveloperSkill(User user, Long id) {
        Developer developer = user.getDeveloper();
        Set<Specialization> specializations = new HashSet<>(developer.getSpecializations());
        developer.getSpecializations().clear();
        specializations.removeIf(s -> s.getId().equals(id));
        developer.setSpecializations(specializations);
        developerRepo.save(developer);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setDesiredJob(User user, String title, String salary, String jobType, String salaryPeriod) {
        Developer developer = user.getDeveloper();
        DesiredJob desiredJob = developer.getDesiredJob();
        if (desiredJob == null){
            desiredJob = new DesiredJob();
            desiredJobRepo.save(desiredJob);
        }
        desiredJob.setDesiredJobTitle(title);
        desiredJob.setDesiredSalary(salary);
        desiredJob.setDesiredJobType(jobType);
        desiredJob.setDesiredSalaryPeriod(salaryPeriod);
        desiredJob.setDeveloper(developer);
        developer.setDesiredJob(desiredJob);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJobByTitle(String title) {
        if (title.equals("")){
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByTitleIgnoreCaseLike("%" + title + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJobByQualifications(String qualifications) {
        if (qualifications.equals("")){
            return new HashSet<>(jobRepo.findAll());
        }
        qualifications = qualifications.trim().replaceAll(" +", " ");
        String[] words = qualifications.split("\\s");
        Set<Job> jobs = new HashSet<>();
        for (String word : words) {
            jobs.addAll(jobRepo.findByQualificationsIgnoreCaseLike("%" + word + "%"));
        }
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJobByCompany(String company) {
        if (company.equals("")){
            List<Employee> employees = employeeRepo.findAll();
            Set<Job> jobs = new HashSet<>();
            for (Employee employee : employees) {
                jobs.addAll(employee.getJobs());
            }
            return jobs;
        }
        Set<Employee> employees = employeeRepo.findByCompanyIgnoreCaseLike("%" + company + "%");
        Set<Job> jobs = new HashSet<>();
        for (Employee employee : employees) {
            jobs.addAll(employee.getJobs());
        }
        return jobs;
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJobByLocation(String location) {
        Set<Job> jobs = jobRepo.findByJobLocationIgnoreCaseLike("%" + location + "%");
        jobs.addAll(jobRepo.findByCountryIgnoreCaseLike("%" + location + "%"));
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJob(String description) {
        Set<Job> jobs = searchForJobByTitle(description);
        jobs.addAll(searchForJobByQualifications(description));
        jobs.addAll(searchForJobByCompany(description));
        return jobs;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Job> searchForJob(String whatDescription, String whereDescription) {
        if (whatDescription.equals("") && whereDescription.equals("")){
            return Collections.emptySet();
        }
        if (whatDescription.equals("") && !whereDescription.equals("")){
            return searchForJobByLocation(whereDescription);
        }
        if (!whatDescription.equals("") && whereDescription.equals("")){
            return searchForJob(whatDescription);
        }
        Set<Job> jobs = searchForJob(whatDescription);
        Set<Job> jobsByLocation = searchForJobByLocation(whereDescription);
        Set<Job> jobsFinal = new HashSet<>();
        for (Job job : jobs) {
            for (Job location : jobsByLocation) {
                if (job.equals(location)){
                    jobsFinal.add(job);
                }
            }
        }
        return jobsFinal;
    }


    // DO THIS


    @Override
    public Set<Job> searchForJobByOneWord(String oneWord) {
        return null;
    }

    @Override
    public Set<Job> searchForJobByAllWords(String allWords) {
        return null;
    }

    @Override
    public Set<Job> searchFroJobByPhrase(String phrase) {
        return null;
    }


    @Override
    public Set<Job> searchForJobByWordsInTitle(String title) {
        return null;
    }

    @Override
    public Set<Job> searchForJobByJobType(String jobType) {
        return null;
    }

    @Override
    public Set<Job> searchForJobBySalaryAndPeriod(String salary, String salaryPeriod) {
        return null;
    }

    @Override
    public Set<Job> searchForJobAdvanced(String allWords, String phrase, String oneWord, String title,
                                         String jobType, String salary, String salaryPeriod) {
        return null;
    }
}








