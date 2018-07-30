package com.instinctools.service.employee;

import com.instinctools.entities.devEntities.*;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.*;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.mail.MailSender;
import com.instinctools.service.user.UserService;
import com.instinctools.service.words.WordsSpliterator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl extends UserService implements EmployeeService {
    private final UserRepo userRepo;

    private final JobRepo jobRepo;

    private final WordsSpliterator wordsSpliterator;

    private final SpecializationRepo specializationRepo;

    private final DeveloperRepo developerRepo;

    private final EmployeeRepo employeeRepo;

    private final WorkExperienceRepo workExperienceRepo;

    private final DesiredJobRepo desiredJobRepo;

    private final EducationRepo educationRepo;

    public EmployeeServiceImpl(final UserRepo userRepo, final JobRepo jobRepo, MailSender sender,
                               final WordsSpliterator wordsSpliterator, final SpecializationRepo specializationRepo,
                               final DeveloperRepo developerRepo, final EmployeeRepo employeeRepo,
                               final WorkExperienceRepo workExperienceRepo, final DesiredJobRepo desiredJobRepo,
                               final EducationRepo educationRepo) {
        super(userRepo, sender);
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.wordsSpliterator = wordsSpliterator;
        this.specializationRepo = specializationRepo;
        this.developerRepo = developerRepo;
        this.employeeRepo = employeeRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.desiredJobRepo = desiredJobRepo;
        this.educationRepo = educationRepo;
    }

    @Override
    public void editJobLocation(final User user, final String country, final String location, final Long id) {
        Job job = jobRepo.getOne(id);
        job.setJobLocation(location);
        job.setCountry(country);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDescription(final User user, final String description,
                                   final Long id) {
        Job job = jobRepo.getOne(id);
        job.setFullDescription(description);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobDesiredDescription(final User user, final String experience,
                                          final Long id) {
        Job job = jobRepo.getOne(id);
        job.setDesiredExperience(experience);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editJobTitle(final User user, final String title, final Long id) {
        Job job = jobRepo.getOne(id);
        job.setTitle(title);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public void editEmployeeCompany(final User user, final String company) {
        Employee employee = user.getEmployee();
        employee.setCompany(company);
        employeeRepo.save(employee);
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
    public void setEmployeeBasicInformation(final User user, final RedirectAttributes redirectedJob, final String jobTitle,
                                            final String company, final String jobLocation, final String country) {
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

    @Override
    public void editJobQualification(final User user, final String qualification, final Long id) {
        Job job = jobRepo.getOne(id);
        job.setQualifications(qualification);
        Employee employee = job.getEmployee();
        user.setEmployee(employee);
        userRepo.save(user);
    }

    @Override
    public boolean checkEmployeeEditing(final User user, final Long id) {
        return !user.getEmployee().equals(jobRepo.getOne(id).getEmployee());
    }

    @Override
    public Set<Developer> searchForResumeByTitleAndDesiredTitle(final String title) {
        if (title.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return Stream.concat(desiredJobRepo.findByDesiredJobTitleIgnoreCaseLike("%" + title + "%").stream().map(DesiredJob::getDeveloper),
                             workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%").stream().map(WorkExperience::getDeveloper))
               .collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByWorkTitle(final String title) {
        if (title.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%").stream().map(WorkExperience::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByCompany(final String company) {
        if (company.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return workExperienceRepo.findByCompanyIgnoreCase(company).stream().map(WorkExperience::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByWorkDescription(final String description) {
        if (description.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return  workExperienceRepo.findByDescriptionIgnoreCaseLike("%" + description + "%").stream().
                map(WorkExperience::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeBySkills(final String skills) {
        if (skills.isEmpty()) {
            return specializationRepo.findAll().stream().map(Specialization::getDeveloper).collect(Collectors.toSet());
        }
        Set<Specialization> specializations = new HashSet<>();
        for (String word : wordsSpliterator.wordsSpliterator(skills)) {
            specializations.addAll(specializationRepo.findBySkillIgnoreCase(word));
        }
        return specializations.stream().map(Specialization::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByEducationPlace(final String place) {
        if (place.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return educationRepo.findByPlaceIgnoreCaseLike("%" + place + "%").stream().map(Education::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByEducationDegree(final String degree) {
        if (degree.equals("1")) {
            return new HashSet<>(developerRepo.findAll());
        }
        return educationRepo.findByDegreeIgnoreCase(degree).stream().map(Education::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByFieldOfStudy(final String fieldOfStudy) {
        if (fieldOfStudy.equals("")) {
            return new HashSet<>(developerRepo.findAll());
        }
        return educationRepo.findByFieldOfStudyIgnoreCaseLike("%" + fieldOfStudy + "%").
               stream().map(Education::getDeveloper).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByDescription(final String description) {
        return Stream.of(searchForResumeByTitleAndDesiredTitle(description),
                          searchForResumeByCompany(description),
                          searchForResumeBySkills(description))
               .flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForDeveloperByLocation(final String location) {
        return developerRepo.findByCityIgnoreCaseOrZipPostalCodeIgnoreCaseOrCountryIgnoreCase(location, location, location);


    }

    @Override
    public Set<Developer> searchForResume(final String whatDescription, final String whereDescription) {
        if (whereDescription.isEmpty()) {
            return searchForResumeByDescription(whatDescription);
        }
        return searchForDeveloperByLocation(whereDescription).stream()
                                                              .filter(searchForResumeByDescription(whatDescription)::contains)
                                                              .collect(Collectors.toSet());
    }

    @Override
    public Set<Developer> searchForResumeByAdditionalInformation(final String additional) {
        if (additional.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findByAdditionalInformationIgnoreCaseLike("%" + additional + "%");
    }

    @Override
    public Set<Developer> searchForResumeBySummary(final String summary) {
        if (summary.isEmpty()) {
            return new HashSet<>(developerRepo.findAll());
        }
        return developerRepo.findBySummaryIgnoreCaseLike("%" + summary + "%");
    }

    @Override
    public Set<Developer> searchForDeveloperByExperience(final Long experience) {
        if (experience == 15) {
            return new HashSet<>(developerRepo.findAll());
        }
        Set<Developer> developers = new HashSet<>(developerRepo.findAll());
        Set<Developer> devs = new HashSet<>();
        for (Developer developer : developers) {
            long dbExperience = 0;
            for (WorkExperience workExperience : developer.getWorkExperiences()) {
                dbExperience = dbExperience + Long.parseLong(workExperience.getYearFrom()) - Long.parseLong(workExperience.getYearTo());
            }
            if (dbExperience >= experience) {
                devs.add(developer);
            }
        }
        return devs;
    }

    @Override
    public Set<Developer> searchForResumeByWords(final String allWords) {
        AtomicLong number = new AtomicLong(0);
        Set<Developer> developers = new HashSet<>();
        for (String word : wordsSpliterator.wordsSpliterator(allWords)) {
            if (!searchForResumeBySkills(word).isEmpty() ||
                !searchForResumeByAdditionalInformation(word).isEmpty() ||
                !searchForResumeBySummary(word).isEmpty() ||
                !searchForResumeByWorkDescription(word).isEmpty()) {
                number.incrementAndGet();
            }
            developers.addAll(Stream.of(searchForResumeBySkills(word),
                                        searchForResumeByWorkDescription(word),
                                        searchForResumeByAdditionalInformation(word),
                                        searchForResumeBySummary(word)).flatMap(Set::stream).
                              collect(Collectors.toSet()));
        }
        if (number.get() < wordsSpliterator.wordsSpliterator(allWords).size()) {
            return Collections.emptySet();
        }
        return developers;
    }

    @Override
    public Set<Developer> searchForResumeByPhrase(final String phrase) {
        return Stream.of(searchForResumeBySkills(phrase),
                         searchForResumeByWorkDescription(phrase),
                         searchForResumeByAdditionalInformation(phrase),
                         searchForResumeBySummary(phrase)).
                flatMap(Set::stream).collect(Collectors.toSet());

    }

    @Override
    public Set<Developer> searchForResumeByOneWord(final String oneWord) {
        List<String> words = wordsSpliterator.wordsSpliterator(oneWord);
        Set<Developer> developers = new HashSet<>();
        for (String word : words) {
            developers.addAll(Stream.of(searchForResumeBySkills(word),
                                        searchForResumeByWorkDescription(word),
                                        searchForResumeByAdditionalInformation(word),
                                        searchForResumeBySummary(word)).
                              flatMap(Set::stream).collect(Collectors.toSet()));
        }
        return developers;
    }

    @Override
    public Set<Developer> searchForResumeAdvanced(final User user, final String allWords,
                                                  final String phrase, final String oneWord,
                                                  final String title, final String company,
                                                  final Long experience, final String place,
                                                  final String degree, final String field,
                                                  final String location) {
        return searchForResumeByOneWord(oneWord).stream()
                .filter(searchForResumeByWords(allWords)::contains)
                .filter(searchForResumeByPhrase(phrase)::contains)
                .filter(searchForResumeByWorkTitle(title)::contains)
                .filter(searchForResumeByCompany(company)::contains)
                .filter(searchForResumeByEducationPlace(place)::contains)
                .filter(searchForResumeByEducationDegree(degree)::contains)
                .filter(searchForResumeByFieldOfStudy(field)::contains)
                .filter(searchForDeveloperByLocation(location)::contains)
                .filter(searchForResumeByEducationDegree(degree)::contains)
                .filter(searchForDeveloperByExperience(experience)::contains).collect(Collectors.toSet());
    }
}
