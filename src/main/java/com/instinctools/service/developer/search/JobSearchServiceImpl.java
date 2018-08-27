package com.instinctools.service.developer.search;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.repo.employeeRepo.EmployeeRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import com.instinctools.service.words.WordsSpliterator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobSearchServiceImpl implements JobSearchService {
    private final static double SALARY_COEFFICIENT = 0.2;

    private final JobRepo jobRepo;
    private final EmployeeRepo employeeRepo;
    private final WordsSpliterator wordsSpliterator;

    public JobSearchServiceImpl(final JobRepo jobRepo,
                                final EmployeeRepo employeeRepo,
                                final WordsSpliterator wordsSpliterator) {
        this.jobRepo = jobRepo;
        this.employeeRepo = employeeRepo;
        this.wordsSpliterator = wordsSpliterator;
    }

    @Override
    public List<Job> searchForJobByTitle(final String title) {
        if (title.isEmpty()) {
            return jobRepo.findAll();
        }
        return jobRepo.findByTitleIgnoreCaseLike("%" + title + "%");
    }

    @Override
    public List<Job> searchForJobByQualifications(final String qualifications) {
        if (qualifications.isEmpty()) {
            return jobRepo.findAll();
        }
        List<Job> jobs = new LinkedList<>();
        for (String word :  wordsSpliterator.wordsSpliterator(qualifications)) {
            jobs.addAll(jobRepo.findByQualificationsIgnoreCaseLike("%" + word + "%"));
        }
        return jobs.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobByCompany(final String company) {
        if (company.isEmpty()) {
            return employeeRepo.findAll().stream().map(Employee::getJobs).
                    flatMap(Collection::stream).distinct().collect(Collectors.toList());
        }
        return employeeRepo.findByCompanyIgnoreCaseLike("%" + company + "%").
                stream().map(Employee::getJobs).
                        flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobByLocation(final String location) {
        return Stream.concat(jobRepo.findByJobLocationIgnoreCaseLike("%" + location + "%").stream(),
                jobRepo.findByCountryIgnoreCaseLike("%" + location + "%").stream()).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJob(final String description) {
        return Stream.of(searchForJobByTitle(description),
                         searchForJobByQualifications(description),
                         searchForJobByCompany(description)).
                    flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJob(final String whatDescription, final String whereDescription) {
        if (!whereDescription.isEmpty()) {
            return searchForJob(whatDescription);
        }
        return searchForJob(whatDescription).stream().filter(searchForJobByLocation(whereDescription)::contains).
                distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobByDesiredExperience(final String desiredExperience) {
        if (desiredExperience.isEmpty()) {
            return jobRepo.findAll();
        }
        return jobRepo.findByDesiredExperienceIgnoreCaseLike("%" + desiredExperience + "%");
    }

    @Override
    public List<Job> searchForJobByDescription(final String description) {
        if (description.isEmpty()) {
            return jobRepo.findAll();
        }
        return jobRepo.findByFullDescriptionIgnoreCaseLike("%" + description + "%");
    }

    @Override
    public List<Job> searchForJobByOneWord(final String oneWord) {
        List<Job> jobs = new LinkedList<>();
        for (String word :  wordsSpliterator.wordsSpliterator(oneWord)) {
            jobs.addAll(Stream.of(searchForJobByDesiredExperience(word),
                                  searchForJobByQualifications(word),
                                  searchForJobByTitle(word)).
                    flatMap(List::stream).distinct().collect(Collectors.toList()));
        }
        return jobs;
    }

    @Override
    public List<Job> searchForJobByAllWords(final String allWords) {
        List<Job> jobs = new LinkedList<>();
        long number = 0;
        for (String word : wordsSpliterator.wordsSpliterator(allWords)) {
            if (!searchForJobByDesiredExperience(word).isEmpty()
             || !searchForJobByQualifications(word).isEmpty()
             || !searchForJobByDescription(word).isEmpty()) {
                number++;
            }

            jobs.addAll(Stream.of(searchForJobByDesiredExperience(word),
                                  searchForJobByQualifications(word),
                                  searchForJobByDescription(word)).
                    flatMap(List::stream).distinct().collect(Collectors.toList()));
        }
        if (number < wordsSpliterator.wordsSpliterator(allWords).size()) {
            return Collections.emptyList();
        }
        return jobs;
    }

    @Override
    public List<Job> searchFroJobByPhrase(final String phrase) {
        return Stream.of(searchForJobByDesiredExperience(phrase),
                         searchForJobByQualifications(phrase),
                         searchForJobByDescription(phrase)).
                flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobByWordsInTitle(final String title) {
        if (title.isEmpty()) {
            return jobRepo.findAll();

        }
        List<Job> jobs = new LinkedList<>();
        for (String word : wordsSpliterator.wordsSpliterator(title)) {
            jobs.addAll(searchForJobByTitle(word));
        }
        return jobs.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobByJobType(final String jobType) {
        if ("All job types".equals(jobType)) {
            return jobRepo.findAll();
        }
        return jobRepo.findByJobTypeIgnoreCaseLike("%" + jobType + "%");
    }

    @Override
    public List<Job> searchForJobBySalaryAndPeriod(final String salary) {
        if (salary.isEmpty()) {
            return jobRepo.findAll();
        }
        return jobRepo.findByToSalaryBetween(Long.valueOf(salary),
                                            (long) (Long.valueOf(salary) + (Long.valueOf(salary) * SALARY_COEFFICIENT))).stream().
                       filter(jobRepo.findByFromSalaryBetween((long) (Long.valueOf(salary) - (Long.valueOf(salary) * SALARY_COEFFICIENT)),
                              Long.valueOf(salary))::contains).
                distinct().collect(Collectors.toList());
    }

    @Override
    public List<Job> searchForJobAdvanced(final SearchDto searchDto) {
        return searchForJobByOneWord(searchDto.getOneWord()).stream().
                filter(searchFroJobByPhrase(searchDto.getPhrase())::contains).
                filter(searchForJobByAllWords(searchDto.getAllWords())::contains).
                filter(searchForJobByWordsInTitle(searchDto.getTitle())::contains).
                filter(searchForJobByJobType(searchDto.getJobType())::contains).
                filter(searchForJobBySalaryAndPeriod(searchDto.getSalary())::contains).
                distinct().collect(Collectors.toList());
    }
}
