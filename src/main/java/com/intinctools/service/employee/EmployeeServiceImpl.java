package com.intinctools.service.employee;

import com.intinctools.entities.devEntities.*;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.repo.developerRepo.*;
import com.intinctools.repo.employeeRepo.EmployeeRepo;
import com.intinctools.repo.employeeRepo.JobRepo;
import com.intinctools.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class EmployeeServiceImpl extends UserService implements EmployeeService {

    private final UserRepo userRepo;
    private final JobRepo jobRepo;
    private final SpecializationRepo specializationRepo;
    private final DeveloperRepo developerRepo;
    private final EmployeeRepo employeeRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final DesiredJobRepo desiredJobRepo;
    private final EducationRepo educationRepo;

    public EmployeeServiceImpl(final UserRepo userRepo, JobRepo jobRepo, SpecializationRepo specializationRepo, DeveloperRepo developerRepo,
                               EmployeeRepo employeeRepo, WorkExperienceRepo workExperienceRepo, DesiredJobRepo desiredJobRepo, EducationRepo educationRepo) {
        super(userRepo);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.specializationRepo = specializationRepo;
        this.developerRepo = developerRepo;
        this.employeeRepo = employeeRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.desiredJobRepo = desiredJobRepo;
        this.educationRepo = educationRepo;
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
    public void editJobLocation(User user, String country, String location, Long id) {
        Job job = jobRepo.getById(id);
        job.setJobLocation(location);
        job.setCountry(country);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editJobDescription(User user, String description, Long id) {
        Job job = jobRepo.getById(id);
        job.setFullDescription(description);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editJobDesiredDescription(User user, String experience, Long id) {
        Job job = jobRepo.getById(id);
        job.setDesiredExperience(experience);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editJobTitle(User user, String title, Long id) {
        Job job = jobRepo.getById(id);
        job.setTitle(title);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editEmployeeCompany(User user, String company) {
        Employee employee = user.getEmployee();
        employee.setCompany(company);
        employeeRepo.save(employee);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setEmployeeDescription(User user, String desiredExperience,
                                       String fullDescription, HttpServletRequest request) {
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setEmployeeJobSalary(String jobType, String fromSalary, String toSalary,
                                     String salaryPeriod, String qualifications,
                                     RedirectAttributes redirectJob, HttpServletRequest request) {
        Job job = (Job) request.getSession().getAttribute("attribute");
        job.setJobType(jobType);
        job.setFromSalary(Long.valueOf(fromSalary));
        job.setToSalary(Long.valueOf(toSalary));
        job.setSalaryPeriod(salaryPeriod);
        job.setQualifications(qualifications);
        redirectJob.addFlashAttribute("job", job);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setEmployeeBasicInformation(User user, RedirectAttributes redirectedJob, String jobTitle,
                                            String company, String jobLocation, String country) {
        Job job = new Job();
        job.setTitle(jobTitle);
        job.setCountry(country);
        job.setJobLocation(jobLocation);
        Employee employee = user.getEmployee();
        employee.setCompany(company);
        user.setEmployee(employee);
        userRepo.save(user);
        redirectedJob.addFlashAttribute("job", job);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setEmployeeAccountInformation(User user, String company, String name, String phone, String email) {
        Employee employee = user.getEmployee();
        employee.setName(name);
        if (phone.equals("")){
            phone = employee.getPhone();
        }
        if (email.equals("")){
            email = employee.getEmail();
        }
        employee.setCompany(company);
        employee.setPhone(phone);
        employee.setEmail(email);
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void editJobQualification(User user, String qualification, Long id) {
        Job job = jobRepo.getById(id);
        job.setQualifications(qualification);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean checkEmployeeEditing(User user, Long id) {
        return !user.getEmployee().equals(jobRepo.getById(id).getEmployee());
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByTitleAndDesiredTitle(String title) {
        if (title.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<WorkExperience> workExperiences = workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%");
        Set<DesiredJob> desiredJobs = desiredJobRepo.findByDesiredJobTitleIgnoreCaseLike("%" + title + "%");
        Set<Developer> developers = new HashSet<>();
        for (WorkExperience workExperience : workExperiences) {
            developers.add(workExperience.getDeveloper());
        }
        for (DesiredJob job : desiredJobs) {
            developers.add(job.getDeveloper());
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByWorkTitle(String title) {
        if (title.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<WorkExperience> workExperiences = workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%");
        Set<Developer> developers = new HashSet<>();
        for (WorkExperience workExperience : workExperiences) {
            developers.add(workExperience.getDeveloper());
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByCompany(String company) {
        if (company.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<WorkExperience> workExperiences = workExperienceRepo.findByCompanyIgnoreCase(company);
        Set<Developer> developers = new HashSet<>();
        for (WorkExperience workExperience : workExperiences) {
            developers.add(workExperience.getDeveloper());
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByWorkDescription(String description) {
        if (description.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<WorkExperience> workExperiences = workExperienceRepo.findByDescriptionIgnoreCaseLike("%" + description + "%");
        Set<Developer> developers = new HashSet<>();
        for (WorkExperience workExperience : workExperiences) {
            developers.add(workExperience.getDeveloper());
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeBySkills(String skills) {
        if (skills.equals("")){
            Set<Specialization> specializations = new HashSet<>(specializationRepo.findAll());
            Set<Developer> developers = new HashSet<>();
            for (Specialization specialization : specializations) {
                developers.add(specialization.getDeveloper());
            }
            return developers;
        }
        skills = skills.trim().replaceAll(" +", " ");
        String[] words = skills.split("\\s");
        Set<Specialization> specializations = new HashSet<>();
        Set<Developer> developers = new HashSet<>();
        for (String word : words) {
            specializations.addAll(specializationRepo.findBySkillIgnoreCase(word));
        }
        for (Specialization specialization : specializations) {
            developers.add(specialization.getDeveloper());
        }
        return developers;
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByEducationPlace(String place) {
        if (place.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<Education> educations = educationRepo.findByPlaceIgnoreCaseLike("%" + place + "%");
        Set<Developer> developers = new HashSet<>();
        for (Education education : educations) {
            developers.add(education.getDeveloper());
        }
        return developers;
    }



    // DO THIS METHOD
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByEducationDegree(String degree) {
            return new HashSet<>(developerRepo.findAll());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByFieldOfStudy(String fieldOfStudy) {
        if (fieldOfStudy.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        Set<Education> educations = educationRepo.findByFieldOfStudyIgnoreCaseLike("%" + fieldOfStudy + "%");
        Set<Developer> developers = new HashSet<>();
        for (Education education : educations) {
            developers.add(education.getDeveloper());
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByCountry(String country) {
        if (country.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findByCountryIgnoreCase(country);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByCity(String city) {
        if (city.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findByCityIgnoreCase(city);
    }

    @Override
    public Set<Developer> searchForResumeByZipPostalCode(String zipPostalCode) {
        if (zipPostalCode.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findByZipPostalCode(zipPostalCode);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByDescription(String description) {
        if (searchForResumeByTitleAndDesiredTitle(description).isEmpty()){
            if (searchForResumeByCompany(description).isEmpty()){
                if (searchForResumeBySkills(description).isEmpty()){
                   return Collections.emptySet();
                }else {
                    return searchForResumeBySkills(description);
                }
            }else {
                return searchForResumeByCompany(description);
            }
        }
        Set<Developer> developers = searchForResumeByTitleAndDesiredTitle(description);
        developers.addAll(searchForResumeByCompany(description));
        developers.addAll(searchForResumeBySkills(description));
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForDeveloperByLocation(String location) {
        Set<Developer> developers = searchForResumeByCountry(location);
        developers.addAll(searchForResumeByZipPostalCode(location));
        developers.addAll(searchForResumeByCity(location));
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResume(String whatDescription, String whereDescription) {
        if (!whatDescription.equals("") && whereDescription.equals("")){
            return searchForResumeByDescription(whatDescription);
        }
        if (!whereDescription.equals("") && whatDescription.equals("")){
            return searchForDeveloperByLocation(whereDescription);
        }
        if (whatDescription.equals("") && whereDescription.equals("")){
            return Collections.emptySet();
        }
        Set<Developer> location = searchForDeveloperByLocation(whereDescription);
        Set<Developer> description = searchForResumeByDescription(whatDescription);
        Set<Developer> developers = new HashSet<>();
        for (Developer descriptionDev : description) {
            for (Developer locationDev : location) {
                if (descriptionDev == locationDev){
                    developers.add(descriptionDev);
                }
            }
        }
        return developers;
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByAdditionalInformation(String additional) {
        if (additional.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findByAdditionalInformationIgnoreCaseLike("%" + additional + "%");
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeBySummary(String summary) {
        if (summary.equals("")){
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findBySummaryIgnoreCaseLike("%" + summary + "%");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForDeveloperByExperience(Long experience) {
        if (experience == 15){
            return new HashSet<>(developerRepo.findAll());
        }
        List<Developer> developersFromDb = developerRepo.findAll();
        Set<Developer> developers = new HashSet<>();
        for (Developer developer : developersFromDb) {
            long dbExperience = 0;
            Set<WorkExperience> workExperiences = developer.getWorkExperiences();
            for (WorkExperience workExperience : workExperiences) {
                 dbExperience = dbExperience + Long.parseLong(workExperience.getYearTo()) - Long.parseLong(workExperience.getYearFrom());
            }
            if (dbExperience >= experience){
                developers.add(developer);
            }

        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByWords(String allWords) {
        long number = 0;
        allWords = allWords.trim().replaceAll(" +", " ");
        String[] words = allWords.split("\\s");
        Set<Developer> developers = new HashSet<>();
        for (String word : words) {
            if (!searchForResumeBySkills(word).isEmpty() ||
                    !searchForResumeByAdditionalInformation(word).isEmpty() ||
                    !searchForResumeBySummary(word).isEmpty() ||
                    !searchForResumeByWorkDescription(word).isEmpty() ){
                number++;
            }
            developers.addAll(searchForResumeBySkills(word));

            developers.addAll(searchForResumeByAdditionalInformation(word));
            developers.addAll(searchForResumeBySummary(word));
            developers.addAll( searchForResumeByWorkDescription(word));
        }
        if (number < words.length) {
            return Collections.emptySet();
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByPhrase(String phrase) {
        Set<Developer> developers = new HashSet<>();
        developers.addAll(searchForResumeBySkills(phrase));
        developers.addAll(searchForResumeByAdditionalInformation(phrase));
        developers.addAll(searchForResumeBySummary(phrase));
        developers.addAll( searchForResumeByWorkDescription(phrase));
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeByOneWord(String oneWord) {
        oneWord = oneWord.trim().replaceAll(" +", " ");
        String[] words = oneWord.split("\\s");
        Set<Developer> developers = new HashSet<>();
        for (String word : words) {
            developers.addAll(searchForResumeBySkills(word));
            developers.addAll(searchForResumeByAdditionalInformation(word));
            developers.addAll(searchForResumeBySummary(word));
            developers.addAll( searchForResumeByWorkDescription(word));
        }
        return developers;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Set<Developer> searchForResumeAdvanced(User user, String allWords, String phrase, String oneWord,
                                                  String title, String company, Long experience, String place,
                                                  String degree, String field, String location) {
        Set<Developer> oneWordDevelopers = searchForResumeByOneWord(oneWord);
        Set<Developer> allWordDevelopers = searchForResumeByWords(allWords);
        Set<Developer> phraseDevelopers = searchForResumeByPhrase(phrase);
        Set<Developer> titleDevelopers = searchForResumeByWorkTitle(title);
        Set<Developer> companyDevelopers = searchForResumeByCompany(company);
        Set<Developer> educationPlaceDevelopers = searchForResumeByEducationPlace(place);
        Set<Developer> educationDegreeDevelopers = searchForResumeByEducationDegree(degree);
        Set<Developer> educationFieldOfStudyDevelopers = searchForResumeByFieldOfStudy(field);
        Set<Developer> locationDevelopers = searchForDeveloperByLocation(location);
        Set<Developer> experienceDevelopers = searchForDeveloperByExperience(experience);
        Set<Developer> developers = new HashSet<>();


        for (Developer oneWordDeveloper : oneWordDevelopers) {
            for (Developer allWordDeveloper : allWordDevelopers) {
                if (oneWordDeveloper.equals(allWordDeveloper)){
                    for (Developer phraseDeveloper : phraseDevelopers) {
                        if (allWordDeveloper.equals(phraseDeveloper)){
                            for (Developer titleDeveloper : titleDevelopers) {
                                if (phraseDeveloper.equals(titleDeveloper)){
                                    for (Developer companyDeveloper : companyDevelopers) {
                                        if (titleDeveloper.equals(companyDeveloper)){
                                            for (Developer educationPlaceDeveloper : educationPlaceDevelopers) {
                                                if (companyDeveloper.equals(educationPlaceDeveloper)){
                                                    for (Developer educationDegreeDeveloper : educationDegreeDevelopers) {
                                                        if (educationPlaceDeveloper.equals(educationDegreeDeveloper)){
                                                            for (Developer educationFieldOfStudyDeveloper : educationFieldOfStudyDevelopers) {
                                                                if (educationDegreeDeveloper.equals(educationFieldOfStudyDeveloper)){
                                                                    for (Developer locationDeveloper : locationDevelopers) {
                                                                        if (educationFieldOfStudyDeveloper.equals(locationDeveloper)){
                                                                            for (Developer experienceDeveloper : experienceDevelopers) {
                                                                                if (locationDeveloper.equals(experienceDeveloper)){
                                                                                    developers.add(oneWordDeveloper);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return developers;
    }
}
