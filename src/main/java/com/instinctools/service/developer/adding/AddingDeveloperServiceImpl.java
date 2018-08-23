package com.instinctools.service.developer.adding;

import com.instinctools.controllers.Dto.UserDto;
import com.instinctools.controllers.Dto.EducationDto;
import com.instinctools.controllers.Dto.WorkExperienceDto;
import com.instinctools.controllers.Dto.SkillDto;
import com.instinctools.controllers.Dto.DesiredJobDto;
import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.devEntities.DesiredJob;
import com.instinctools.entities.devEntities.Education;
import com.instinctools.entities.devEntities.Specialization;
import com.instinctools.entities.devEntities.WorkExperience;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.EducationRepo;
import com.instinctools.repo.developerRepo.WorkExperienceRepo;
import com.instinctools.repo.developerRepo.DeveloperRepo;
import com.instinctools.repo.developerRepo.SpecializationRepo;
import com.instinctools.repo.developerRepo.DesiredJobRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AddingDeveloperServiceImpl implements AddDeveloperService {
    private final UserRepo userRepo;
    private final EducationRepo educationRepo;
    private final DeveloperRepo developerRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final SpecializationRepo specializationRepo;
    private final DesiredJobRepo desiredJobRepo;

    public AddingDeveloperServiceImpl(final UserRepo userRepo,
                                      final EducationRepo educationRepo,
                                      final DeveloperRepo developerRepo,
                                      final WorkExperienceRepo workExperienceRepo,
                                      final SpecializationRepo specializationRepo,
                                      final DesiredJobRepo desiredJobRepo) {
        this.userRepo = userRepo;
        this.educationRepo = educationRepo;
        this.developerRepo = developerRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.specializationRepo = specializationRepo;
        this.desiredJobRepo = desiredJobRepo;
    }


    @Override
    public void setBasicQualities(final User user,
                                  final UserDto userDTO) {
        Developer developer = user.getDeveloper();
        if (!userDTO.getCountry().isEmpty() && userDTO.getCountry() != null) {
            if (userDTO.getZipPostalCode().isEmpty()) {
                final String zipPostalCode = developer.getZipPostalCode();
                userDTO.setZipPostalCode(zipPostalCode);
            }
            if (userDTO.getTelephone().isEmpty()) {
                 final String telephone = developer.getTelephone();
                 userDTO.setTelephone(telephone);
            }
            ModelMapper mapper = new ModelMapper();
            mapper.map(userDTO, developer);
            user.setDeveloper(developer);
            userRepo.save(user);
        }
    }

    @Override
    public void setEducation(final User user,
                             final EducationDto educationDTO) {
        if (!"1".equals(educationDTO.getDegree()) && !educationDTO.getFieldOfStudy().isEmpty() && !educationDTO.getPlace().isEmpty()) {
            final Developer developer = user.getDeveloper();
            ModelMapper mapper = new ModelMapper();
            Education education = new Education();
            mapper.map(educationDTO, education);
            education.setDeveloper(developer);
            educationRepo.save(education);
            developer.setEducation(educationRepo.findByDeveloper(developer));
            developerRepo.save(developer);
        }

    }

    @Override
    public void setWorkExperience(final User user,
                                  final WorkExperienceDto workExperienceDTO,
                                  final String check) {
        final Developer developer = user.getDeveloper();
        if (check != null) {
            developer.setJobExperience(false);
            user.setDeveloper(developer);
            userRepo.save(user);
        } else {
            WorkExperience workExperience = new WorkExperience();
            ModelMapper mapper = new ModelMapper();
            mapper.map(workExperienceDTO, workExperience);
            workExperience.setDeveloper(user.getDeveloper());
            workExperienceRepo.save(workExperience);
            developer.setWorkExperiences(workExperienceRepo.findByDeveloper(developer));
            developerRepo.save(developer);
        }

    }

    @Override
    public void setDeveloperSummary(final User user,
                                    final String summary) {
        Developer developer = user.getDeveloper();
        developer.setSummary(summary);
        developerRepo.save(developer);
    }

    @Override
    public void setDeveloperAdditional(final User user, final String additional) {
        Developer developer = user.getDeveloper();
        developer.setAdditionalInformation(additional);
        userRepo.save(user);

    }


    @Override
    public void setDeveloperEducation(final User user, final EducationDto educationDTO, final Long id) {
        Education education = educationRepo.getOne(id);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(educationDTO, education);
        education.setDeveloper(user.getDeveloper());
        educationRepo.save(education);
    }

    @Override
    public void setDeveloperSkill(final User user, final SkillDto skillDto) {
        final Developer developer = user.getDeveloper();
        Specialization specialization  = new Specialization();
        specialization.setDeveloper(user.getDeveloper());
        specialization.setExperience(skillDto.getExperience());
        specialization.setSkill(skillDto.getSkill());
        specializationRepo.save(specialization);
        developer.setSpecializations(specializationRepo.findByDeveloper(developer));
        developerRepo.save(developer);
    }

    @Override
    public void setDesiredJob(final User user, final DesiredJobDto desiredJobDTO) {
        Developer developer = user.getDeveloper();
        DesiredJob desiredJob = developer.getDesiredJob();
        if (desiredJob == null) {
            desiredJob = new DesiredJob();
            developer.setDesiredJob(desiredJob);
            desiredJob.setDeveloper(developer);
            desiredJobRepo.save(desiredJob);
        }
        ModelMapper mapper = new ModelMapper();
        mapper.map(desiredJobDTO, desiredJob);
        desiredJob.setDeveloper(developer);
        developer.setDesiredJob(desiredJob);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

}
