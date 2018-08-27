package com.instinctools.service.employee.search;

import com.instinctools.controllers.Dto.SearchDto;
import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.Specialization;
import com.instinctools.entities.devEntities.DesiredJob;
import com.instinctools.entities.devEntities.Education;
import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.repo.developerRepo.DesiredJobRepo;
import com.instinctools.repo.developerRepo.SpecializationRepo;
import com.instinctools.repo.developerRepo.DeveloperRepo;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.service.words.WordsSpliterator;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResumeSearchServiceImpl implements ResumeSearchService {
    private final WordsSpliterator wordsSpliterator;
    private final SpecializationRepo specializationRepo;
    private final DeveloperRepo developerRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final DesiredJobRepo desiredJobRepo;
    private final EducationRepo educationRepo;

    public ResumeSearchServiceImpl(final WordsSpliterator wordsSpliterator,
                                   final SpecializationRepo specializationRepo,
                                   final DeveloperRepo developerRepo,
                                   final WorkExperienceRepo workExperienceRepo,
                                   final DesiredJobRepo desiredJobRepo,
                                   final EducationRepo educationRepo) {
        this.wordsSpliterator = wordsSpliterator;
        this.specializationRepo = specializationRepo;
        this.developerRepo = developerRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.desiredJobRepo = desiredJobRepo;
        this.educationRepo = educationRepo;
    }

    @Override
    public List<Developer> searchForResumeByTitleAndDesiredTitle(final String title) {
        if (title.isEmpty()) {
            return developerRepo.findAll();
        }
        return Stream.concat(desiredJobRepo.findByDesiredJobTitleIgnoreCaseLike("%" + title + "%").stream().map(DesiredJob::getDeveloper),
                             workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%").stream().map(WorkExperience::getDeveloper)).
                distinct().
                collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByWorkTitle(final String title) {
        if (title.isEmpty()) {
            return developerRepo.findAll();
        }
        return workExperienceRepo.findByJobTitleIgnoreCaseLike("%" + title + "%").
                stream().map(WorkExperience::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByCompany(final String company) {
        if (company.isEmpty()) {
            return developerRepo.findAll();
        }
        return workExperienceRepo.findByCompanyIgnoreCase(company).stream().map(WorkExperience::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByWorkDescription(final String description) {
        if (description.isEmpty()) {
            return developerRepo.findAll();
        }
        return  workExperienceRepo.findByDescriptionIgnoreCaseLike("%" + description + "%").stream().
                map(WorkExperience::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeBySkills(final String skills) {
        if (skills.isEmpty()) {
            return specializationRepo.findAll().stream().map(Specialization::getDeveloper).distinct().collect(Collectors.toList());
        }
        List<Specialization> specializations = new LinkedList<>();
        for (String word : wordsSpliterator.wordsSpliterator(skills)) {
            specializations.addAll(specializationRepo.findBySkillIgnoreCase(word));
        }
        return specializations.stream().map(Specialization::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByEducationPlace(final String place) {
        if (place.isEmpty()) {
            return developerRepo.findAll();
        }
        return educationRepo.findByPlaceIgnoreCaseLike("%" + place + "%").stream().map(Education::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByEducationDegree(final String degree) {
        if ("1".equals(degree)) {
            return developerRepo.findAll();
        }
        return educationRepo.findByDegreeIgnoreCase(degree).stream().map(Education::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByFieldOfStudy(final String fieldOfStudy) {
        if (fieldOfStudy.equals("")) {
            return developerRepo.findAll();
        }
        return educationRepo.findByFieldOfStudyIgnoreCaseLike("%" + fieldOfStudy + "%").
               stream().map(Education::getDeveloper).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByDescription(final String description) {
        return Stream.of(searchForResumeByTitleAndDesiredTitle(description),
                         searchForResumeByCompany(description),
                         searchForResumeBySkills(description)).
                flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForDeveloperByLocation(final String location) {
        if (location.isEmpty()) {
            return developerRepo.findAll();
        }
        return developerRepo.findByCityIgnoreCaseOrZipPostalCodeIgnoreCaseOrCountryIgnoreCase(location, location, location);
    }

    @Override
    public List<Developer> searchForResume(final SearchDto searchDto) {
        if (searchDto.getWhereDescription().isEmpty()) {
            return searchForResumeByDescription(searchDto.getWhatDescription());
        }
        return searchForDeveloperByLocation(searchDto.getWhereDescription()).stream().
                    filter(searchForResumeByDescription(searchDto.getWhatDescription())::contains).
                        distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByAdditionalInformation(final String additional) {
        if (additional.isEmpty()) {
            return developerRepo.findAll();
        }
        return developerRepo.findByAdditionalInformationIgnoreCaseLike("%" + additional + "%");
    }

    @Override
    public List<Developer> searchForResumeBySummary(final String summary) {
        if (summary.isEmpty()) {
            return developerRepo.findAll();
        }
        return developerRepo.findBySummaryIgnoreCaseLike("%" + summary + "%");
    }

    @Override
    public List<Developer> searchForDeveloperByExperience(final Long experience) {
        if (experience == 15) {
            return developerRepo.findAll();
        }
        List<Developer> developers = developerRepo.findAll();
        List<Developer> devs = new LinkedList<>();
        for (Developer developer : developers) {
            long sum = developer.getWorkExperiences().stream().
                                                         mapToLong(workExperience -> Long.parseLong(workExperience.getYearFrom()) -
                                                                                     Long.parseLong(workExperience.getYearTo())).
                    sum();
            if (sum >= experience){
                devs.add(developer);
            }
        }
        return devs.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByWords(final String allWords) {
        long number = 0;
        List<Developer> developers = new LinkedList<>();
        for (String word : wordsSpliterator.wordsSpliterator(allWords)) {
            if (!searchForResumeBySkills(word).isEmpty()
             || !searchForResumeByAdditionalInformation(word).isEmpty()
             || !searchForResumeBySummary(word).isEmpty()
             || !searchForResumeByWorkDescription(word).isEmpty()) {
                number++;
            }
            developers.addAll(Stream.of(searchForResumeBySkills(word),
                                        searchForResumeByWorkDescription(word),
                                        searchForResumeByAdditionalInformation(word),
                                        searchForResumeBySummary(word)).flatMap(List::stream).
                    distinct().collect(Collectors.toList()));
        }
        if (number < wordsSpliterator.wordsSpliterator(allWords).size()) {
            return Collections.emptyList();
        }
        return developers.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByPhrase(final String phrase) {
        return Stream.of(searchForResumeBySkills(phrase),
                         searchForResumeByWorkDescription(phrase),
                         searchForResumeByAdditionalInformation(phrase),
                         searchForResumeBySummary(phrase)).
                flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Developer> searchForResumeByOneWord(final String oneWord) {
        List<String> words = wordsSpliterator.wordsSpliterator(oneWord);
        List<Developer> developers = new LinkedList<>();
        for (String word : words) {
            developers.addAll(Stream.of(searchForResumeBySkills(word),
                                        searchForResumeByWorkDescription(word),
                                        searchForResumeByAdditionalInformation(word),
                                        searchForResumeBySummary(word)).
                              flatMap(List::stream).distinct().collect(Collectors.toSet()));
        }
        return developers;
    }

    @Override
    public List<Developer> searchForResumeAdvanced(final SearchDto searchDto) {
        return searchForResumeByOneWord(searchDto.getOneWord()).stream().
                filter(searchForResumeByWords(searchDto.getAllWords())::contains).
                filter(searchForResumeByPhrase(searchDto.getPhrase())::contains).
                filter(searchForResumeByWorkTitle(searchDto.getTitle())::contains).
                filter(searchForResumeByCompany(searchDto.getCompany())::contains).
                filter(searchForResumeByEducationPlace(searchDto.getPlace())::contains).
                filter(searchForResumeByEducationDegree(searchDto.getDegree())::contains).
                filter(searchForResumeByFieldOfStudy(searchDto.getField())::contains).
                filter(searchForDeveloperByLocation(searchDto.getLocation())::contains).
                filter(searchForDeveloperByExperience(searchDto.getExperience())::contains).
               distinct().collect(Collectors.toList());
    }
}
