package com.instinctools.service.developer.adding;


import com.instinctools.entities.devEntities.*;
import com.instinctools.entities.devEntities.dto.DesiredJobDTO;
import com.instinctools.entities.devEntities.dto.DeveloperDTO;
import com.instinctools.entities.devEntities.dto.EducationDTO;
import com.instinctools.entities.devEntities.dto.WorkExperienceDTO;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AddingDeveloperService  implements AddDeveloper {

    private final UserRepo userRepo;
    private final EducationRepo educationRepo;
    private final DeveloperRepo developerRepo;
    private final WorkExperienceRepo workExperienceRepo;
    private final SpecializationRepo specializationRepo;
    private final DesiredJobRepo desiredJobRepo;

    public AddingDeveloperService(UserRepo userRepo, EducationRepo educationRepo, DeveloperRepo developerRepo,
                                  WorkExperienceRepo workExperienceRepo, SpecializationRepo specializationRepo,
                                  DesiredJobRepo desiredJobRepo) {
        this.userRepo = userRepo;
        this.educationRepo = educationRepo;
        this.developerRepo = developerRepo;
        this.workExperienceRepo = workExperienceRepo;
        this.specializationRepo = specializationRepo;
        this.desiredJobRepo = desiredJobRepo;
    }


    @Override
    public void setBasicQualities(final User user, final DeveloperDTO developerDTO) {
        Developer developer = user.getDeveloper();
        if (!developerDTO.getCountry().isEmpty() && developerDTO.getCountry() != null) {
            if (developerDTO.getZipPostalCode().isEmpty()) {
                final String zipPostalCode = developer.getZipPostalCode();
                developerDTO.setZipPostalCode(zipPostalCode);
            }
            if (developerDTO.getTelephone().isEmpty()) {
                 final String telephone = developer.getTelephone();
                 developerDTO.setTelephone(telephone);
            }
            ModelMapper mapper = new ModelMapper();
            mapper.map(developerDTO, developer);
            user.setDeveloper(developer);
            userRepo.save(user);
        }
    }

    @Override
    public void setEducation(final User user, EducationDTO educationDTO) {
        if (!"1".equals(educationDTO.getDegree()) && !educationDTO.getFieldOfStudy().isEmpty() && !educationDTO.getPlace().isEmpty()) {
            final Developer developer = user.getDeveloper();
            ModelMapper mapper = new ModelMapper();
            Education education = new Education();
            mapper.map(educationDTO, education);
            educationRepo.save(education);
            developer.setEducation(educationRepo.findByDeveloper(developer));
            developerRepo.save(developer);
        }

    }

    @Override
    public void setWorkExperience(final User user, final WorkExperienceDTO workExperienceDTO, final String check) {
        final Developer developer = user.getDeveloper();
        if (check != null) {
            developer.setJobExperience(false);
            user.setDeveloper(developer);
            userRepo.save(user);
        } else {
            WorkExperience workExperience = new WorkExperience();
            ModelMapper mapper = new ModelMapper();
            mapper.map(workExperienceDTO, workExperience);
            workExperienceRepo.save(workExperience);
            developer.setWorkExperiences(workExperienceRepo.findByDeveloper(developer));
            developerRepo.save(developer);
        }

    }

    @Override
    public void setDeveloperSummary(final User user, final String summary) {
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
    public void setDeveloperEducation(final User user, final  EducationDTO educationDTO, final Long id) {
        Education education = educationRepo.getOne(id);

        ModelMapper mapper = new ModelMapper();
        mapper.map(educationDTO, education);

        Developer developer = education.getDeveloper();
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void setDeveloperSkill(final User user, final String skill, final String year) {
        final Developer developer = user.getDeveloper();

        specializationRepo.save(new Specialization(skill, year, developer));
        developer.setSpecializations(specializationRepo.findByDeveloper(developer));

        developerRepo.save(developer);
    }

    @Override
    public void setDesiredJob(final User user, final DesiredJobDTO desiredJobDTO) {
        Developer developer = user.getDeveloper();
        DesiredJob desiredJob = developer.getDesiredJob();
        if (desiredJob == null) {
            desiredJob = new DesiredJob();
            desiredJobRepo.save(desiredJob);
        }
        ModelMapper mapper = new ModelMapper();
        mapper.map(desiredJobDTO, desiredJob);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

}
