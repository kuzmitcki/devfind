package com.intinctools.service.developer;

import com.intinctools.entities.devEntities.*;
import com.intinctools.entities.empEntites.Employee;
import com.intinctools.entities.empEntites.Job;
import com.intinctools.entities.userEntites.User;
import com.intinctools.repo.UserRepo;
import com.intinctools.repo.developerRepo.DesiredJobRepo;
import com.intinctools.repo.developerRepo.EducationRepo;
import com.intinctools.repo.developerRepo.WorkExperienceRepo;
import com.intinctools.repo.employeeRepo.EmployeeRepo;
import com.intinctools.repo.employeeRepo.JobRepo;
import com.intinctools.service.mail.MailSender;
import com.intinctools.service.user.UserService;
import com.intinctools.service.words.WordsSpliterator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class DeveloperServiceImpl extends UserService implements DeveloperService {
    private final UserRepo userRepo;

    private final MailSender mailSender;

    private final WorkExperienceRepo workExperienceRepo;

    private final EducationRepo educationRepo;

    private final WordsSpliterator wordsSpliterator;

    private final DesiredJobRepo desiredJobRepo;

    private final JobRepo jobRepo;

    private final EmployeeRepo employeeRepo;

    private final static double SALARY_COEFFICIENT = 0.1;

    public DeveloperServiceImpl(final UserRepo userRepo, MailSender mailSender, final WorkExperienceRepo workExperienceRepo,
                                final EducationRepo educationRepo, final WordsSpliterator wordsSpliterator,
                                final DesiredJobRepo desiredJobRepo, final JobRepo jobRepo, final EmployeeRepo employeeRepo) {
        super(userRepo, mailSender);
        this.userRepo = userRepo;
        this.mailSender = mailSender;
        this.workExperienceRepo = workExperienceRepo;
        this.educationRepo = educationRepo;
        this.wordsSpliterator = wordsSpliterator;
        this.desiredJobRepo = desiredJobRepo;
        this.jobRepo = jobRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void setBasicQualities(final User user, final String firstName, final String lastName,
                                  final String country, final String city,
                                  String zipPostalCode,  String telephone) {
        Developer developer = user.getDeveloper();
        if (!country.isEmpty()) {
            if (zipPostalCode.isEmpty()) {
                zipPostalCode = developer.getZipPostalCode();
            }
            if (telephone.isEmpty()) {
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
    public void setEducation(final User user, final String degree, final String place, final String fieldOfStudy,
                             final String city, final String monthFrom, final String monthTo,
                             final String yearFrom, final String yearTo) {
      if (!degree.isEmpty() && !fieldOfStudy.isEmpty() && !place.isEmpty()) {
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
    public void setWorkExperience(final User user, final String jobTitle, final String company,
                                  final String city, final String monthFrom, final String monthTo,
                                  final String yearFrom, final String yearTo, final String description, final String check) {
        Developer developer = user.getDeveloper();
        if (check != null) {
            developer.setJobExperience(false);
            user.setDeveloper(developer);
            userRepo.save(user);
        } else {
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
    public boolean checkDeveloperEditingWork(final User user, final Long id) {
        return user.getDeveloper().equals(workExperienceRepo.getById(id).getDeveloper());
    }

    @Override
    public boolean checkDeveloperEditingEducation(final User user, final Long id) {
        return user.getDeveloper().equals(educationRepo.getOne(id).getDeveloper());
    }

    @Override
    public void setDeveloperSummary(final User user, final String summary) {
        Developer developer = user.getDeveloper();
        developer.setSummary(summary);
        userRepo.save(user);
    }

    @Override
    public void setDeveloperAdditional(final User user, final String additional) {
        Developer developer = user.getDeveloper();
        developer.setAdditionalInformation(additional);
        userRepo.save(user);

    }

    @Override
    public void editResumeBasicInformation(final User user, final String country, final String city,
                                           final String telephone, final String zipPostalCode, final String email) {
        Developer developer = user.getDeveloper();
        developer.setCountry(country);
        developer.setCity(city);
        developer.setTelephone(telephone);
        developer.setZipPostalCode(zipPostalCode);
        if (!user.getEmail().equals(email)){
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome. Please, visit next link to activate your account: http://localhost:8080/change/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void setDeveloperEducation(final User user, final String degree, final String place, final String field, final String city,
                                      final String monthFrom, final String monthTo, final String yearFrom, final String yearTo, final Long id) {
        Education education = educationRepo.getOne(id);
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
    public void deleteDeveloperEducation(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<Education> educations = new HashSet<>(developer.getEducation());
        developer.getEducation().clear();
        educations.removeIf(e -> e.getId().equals(id));
        developer.setEducation(educations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void editDeveloperWorkExperience(final User user, final Long id, final String jobTitle, final String company, final String city,
                                            final String monthFrom, final String monthTo, final String yearFrom,
                                            final String yearTo, final String description) {
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
    public void deleteDeveloperWork(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<WorkExperience> workExperiences = new HashSet<>(developer.getWorkExperiences());
        developer.getWorkExperiences().clear();
        workExperiences.removeIf(e -> e.getId().equals(id));
        developer.setWorkExperiences(workExperiences);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void setDeveloperSkill(final User user, final String skill, final String year) {
        Set<Specialization> specializations = new HashSet<>(user.getDeveloper().getSpecializations());
        user.getDeveloper().getSpecializations().clear();
        specializations.add(new Specialization(skill, year));
        Developer developer = user.getDeveloper();
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void deleteDeveloperSkill(final User user, final Long id) {
        Developer developer = user.getDeveloper();
        Set<Specialization> specializations = new HashSet<>(developer.getSpecializations());
        developer.getSpecializations().clear();
        specializations.removeIf(s -> s.getId().equals(id));
        developer.setSpecializations(specializations);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void setDesiredJob(final User user, final String title, final String salary, final String jobType, final String salaryPeriod) {
        Developer developer = user.getDeveloper();
        DesiredJob desiredJob = developer.getDesiredJob();
        if (desiredJob == null) {
            desiredJob = new DesiredJob();
            desiredJobRepo.save(desiredJob);
        }
        desiredJob.setDesiredJobTitle(title);
        desiredJob.setDesiredSalary(Long.valueOf(salary));
        desiredJob.setDesiredJobType(jobType);
        desiredJob.setDesiredSalaryPeriod(salaryPeriod);
        desiredJob.setDeveloper(developer);
        developer.setDesiredJob(desiredJob);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public Set<Job> searchForJobByTitle(final String title) {
        if (title.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByTitleIgnoreCaseLike("%" + title + "%");
    }

    @Override
    public Set<Job> searchForJobByQualifications(final String qualifications) {
        if (qualifications.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());
        }
        Set<Job> jobs = new HashSet<>();
        for (String word :  wordsSpliterator.wordsSpliterator(qualifications)) {
            jobs.addAll(jobRepo.findByQualificationsIgnoreCaseLike("%" + word + "%"));
        }
        return jobs;
    }

    @Override
    public Set<Job> searchForJobByCompany(final String company) {
        if (company.isEmpty()) {
            return employeeRepo.findAll().stream().map(Employee::getJobs).flatMap(Collection::stream).collect(Collectors.toSet());
        }
        return employeeRepo.findByCompanyIgnoreCaseLike("%" + company + "%").stream().map(Employee::getJobs).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobByLocation(final String location) {
        return Stream.concat(jobRepo.findByJobLocationIgnoreCaseLike("%" + location + "%").stream(),
                jobRepo.findByCountryIgnoreCaseLike("%" + location + "%").stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJob(final String description) {
        return Stream.concat(Stream.concat(searchForJobByTitle(description).stream(),
                searchForJobByQualifications(description).stream()),
                searchForJobByCompany(description).stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJob(final String whatDescription, final String whereDescription) {
        if (!whereDescription.isEmpty()) {
            return searchForJob(whatDescription);
        }
        return searchForJob(whatDescription).stream().filter(searchForJobByLocation(whereDescription)::contains).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobByDesiredExperience(final String desiredExperience) {
        if (desiredExperience.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByDesiredExperienceIgnoreCaseLike("%" + desiredExperience + "%");
    }

    @Override
    public Set<Job> searchForJobByDescription(final String description) {
        if (description.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByFullDescriptionIgnoreCaseLike("%" + description + "%");
    }

    @Override
    public Set<Job> searchForJobByOneWord(final String oneWord) {
        Set<Job> jobs = new HashSet<>();
        for (String word :  wordsSpliterator.wordsSpliterator(oneWord)) {
            jobs.addAll(Stream.concat(Stream.concat(searchForJobByDesiredExperience(word).stream(),searchForJobByQualifications(word).stream()),
                    searchForJobByTitle(word).stream()).collect(Collectors.toSet()));
        }
        return jobs;
    }

    @Override
    public Set<Job> searchForJobByAllWords(final String allWords) {
        Set<Job> jobs = new HashSet<>();
        AtomicLong number = new AtomicLong(0);
        for (String word : wordsSpliterator.wordsSpliterator(allWords)) {
            if (!searchForJobByDesiredExperience(word).isEmpty()
                    || !searchForJobByQualifications(word).isEmpty()
                    || !searchForJobByDescription(word).isEmpty()) {
                number.incrementAndGet();
            }
            jobs.addAll(Stream.concat(Stream.concat(searchForJobByDesiredExperience(word).stream(), searchForJobByQualifications(word).stream()),
                    searchForJobByDescription(word).stream()).collect(Collectors.toSet()));
        }
        if (number.get() < wordsSpliterator.wordsSpliterator(allWords).size()) {
            return Collections.emptySet();
        }
        return jobs;
    }

    @Override
    public Set<Job> searchFroJobByPhrase(final String phrase) {
        return Stream.concat(Stream.concat(searchForJobByDesiredExperience(phrase).stream(), searchForJobByQualifications(phrase).stream()),
                searchForJobByDescription(phrase).stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobByWordsInTitle(final String title) {
        if (title.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());

        }
        Set<Job> jobs = new HashSet<>();
        for (String word : wordsSpliterator.wordsSpliterator(title)) {
            jobs.addAll(searchForJobByTitle(word));
        }
        return jobs;
    }

    @Override
    public Set<Job> searchForJobByJobType(final String jobType) {
        if (jobType.equals("All job types")) {
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByJobTypeIgnoreCaseLike("%" + jobType + "%");
    }

    @Override
    public Set<Job> searchForJobBySalaryAndPeriod(final Long salary) {
        return jobRepo.findByToSalaryBetween(salary, (long) (salary + (salary * SALARY_COEFFICIENT))).stream().
                filter(jobRepo.findByFromSalaryBetween((long) (salary - (salary * SALARY_COEFFICIENT)), salary)::contains)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobAdvanced(final String allWords, final String phrase, final String oneWord, final String title,
                                         final String jobType, final Long salary) {
        return searchForJobByOneWord(oneWord).stream().
                            filter(searchFroJobByPhrase(phrase)::contains).
                            filter(searchForJobByAllWords(allWords)::contains).
                            filter(searchForJobByWordsInTitle(title)::contains).
                            filter(searchForJobByJobType(jobType)::contains).
                            filter(searchForJobBySalaryAndPeriod(salary)::contains).
                            collect(Collectors.toSet());
    }
}








