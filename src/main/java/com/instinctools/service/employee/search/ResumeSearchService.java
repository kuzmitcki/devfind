package com.instinctools.service.employee.search;

import com.instinctools.entities.devEntities.*;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.developerRepo.*;
import com.instinctools.service.words.WordsSpliterator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResumeSearchService implements ResumeSearch {
    private final WordsSpliterator wordsSpliterator;

    private final SpecializationRepo specializationRepo;

    private final DeveloperRepo developerRepo;

    private final WorkExperienceRepo workExperienceRepo;

    private final DesiredJobRepo desiredJobRepo;

    private final EducationRepo educationRepo;

    public ResumeSearchService(final WordsSpliterator wordsSpliterator, final SpecializationRepo specializationRepo,
                               final DeveloperRepo developerRepo, final WorkExperienceRepo workExperienceRepo,
                               final DesiredJobRepo desiredJobRepo, final EducationRepo educationRepo) {
        this.wordsSpliterator = wordsSpliterator;
        this.specializationRepo = specializationRepo;
        this.developerRepo = developerRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.desiredJobRepo = desiredJobRepo;
        this.educationRepo = educationRepo;
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
        if (location.isEmpty()){
            return new HashSet<>(developerRepo.findAll());
        }
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
                .filter(searchForDeveloperByExperience(experience)::contains).collect(Collectors.toSet());
    }
}
