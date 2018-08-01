package com.instinctools.service.developer.search;

import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.words.WordsSpliterator;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobJobSearchService implements JobSearch {

    private final static double SALARY_COEFFICIENT = 0.2;

    private final JobRepo jobRepo;
    private final EmployeeRepo employeeRepo;
    private final WordsSpliterator wordsSpliterator;

    public JobJobSearchService(JobRepo jobRepo, EmployeeRepo employeeRepo, WordsSpliterator wordsSpliterator) {
        this.jobRepo = jobRepo;
        this.employeeRepo = employeeRepo;
        this.wordsSpliterator = wordsSpliterator;
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
        return employeeRepo.findByCompanyIgnoreCaseLike("%" + company + "%").stream().map(Employee::getJobs).
                flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobByLocation(final String location) {
        return Stream.concat(jobRepo.findByJobLocationIgnoreCaseLike("%" + location + "%").stream(),
                jobRepo.findByCountryIgnoreCaseLike("%" + location + "%").stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJob(final String description) {
        return Stream.of(searchForJobByTitle(description),
                searchForJobByQualifications(description),
                searchForJobByCompany(description)).
                flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJob(final String whatDescription, final String whereDescription) {
        if (!whereDescription.isEmpty()) {
            return searchForJob(whatDescription);
        }
        return searchForJob(whatDescription).stream().filter(searchForJobByLocation(whereDescription)::contains)
                .collect(Collectors.toSet());
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
            jobs.addAll(Stream.of(searchForJobByDesiredExperience(word),
                    searchForJobByQualifications(word),
                    searchForJobByTitle(word))
                    .flatMap(Set::stream).collect(Collectors.toSet()));
        }
        return jobs;
    }

    @Override
    public Set<Job> searchForJobByAllWords(final String allWords) {
        Set<Job> jobs = new HashSet<>();
        Integer number = 0;
        for (String word : wordsSpliterator.wordsSpliterator(allWords)) {
            if (!searchForJobByDesiredExperience(word).isEmpty()
                || !searchForJobByQualifications(word).isEmpty()
                || !searchForJobByDescription(word).isEmpty()) {
                number ++;
            }

            jobs.addAll(Stream.of(searchForJobByDesiredExperience(word),
                    searchForJobByQualifications(word),
                    searchForJobByDescription(word))
                    .flatMap(Set::stream).collect(Collectors.toSet()));
        }
        if (number < wordsSpliterator.wordsSpliterator(allWords).size()) {
            return Collections.emptySet();
        }
        return jobs;
    }

    @Override
    public Set<Job> searchFroJobByPhrase(final String phrase) {
        return Stream.of(searchForJobByDesiredExperience(phrase),
                         searchForJobByQualifications(phrase),
                         searchForJobByDescription(phrase))
                .flatMap(Set::stream).collect(Collectors.toSet());
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
    public Set<Job> searchForJobBySalaryAndPeriod(final String salary) {
        if (salary.isEmpty()) {
            return new HashSet<>(jobRepo.findAll());
        }
        return jobRepo.findByToSalaryBetween(Long.valueOf(salary), (long) (Long.valueOf(salary) + (Long.valueOf(salary) * SALARY_COEFFICIENT))).stream().
                filter(jobRepo.findByFromSalaryBetween((long) (Long.valueOf(salary) - (Long.valueOf(salary) * SALARY_COEFFICIENT)), Long.valueOf(salary))::contains)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Job> searchForJobAdvanced(final String allWords, final String phrase, final String oneWord,
                                         final String title, final String jobType, final String salary) {
        return searchForJobByOneWord(oneWord).stream().
                filter(searchFroJobByPhrase(phrase)::contains).
                filter(searchForJobByAllWords(allWords)::contains).
                filter(searchForJobByWordsInTitle(title)::contains).
                filter(searchForJobByJobType(jobType)::contains).
                filter(searchForJobBySalaryAndPeriod(salary)::contains).
                collect(Collectors.toSet());
    }
}
