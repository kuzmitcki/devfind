package com.instinctools.service.mail;


import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.empEntites.Employee;
import com.instinctools.entities.empEntites.Job;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.repo.developerRepo.DeveloperRepo;
import com.instinctools.repo.employeeRepo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Service
public class MailSender implements Mail {
    private final JavaMailSender mailSender;

    private final DeveloperRepo developerRepo;

    private final UserRepo userRepo;

    private final JobRepo jobRepo;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public MailSender(final JavaMailSender mailSender, final DeveloperRepo developerRepo,
                      final UserRepo userRepo, final JobRepo jobRepo) {
        this.mailSender = mailSender;
        this.developerRepo = developerRepo;
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
    }

    @Override
    public void send(final String emailTo, final String subject, final String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendOfferToDeveloper(final User user, final Long id,
                                     final RedirectAttributes attributes) {
        Developer developer = developerRepo.getOne(id);
        if (!StringUtils.isEmpty(developer.getUser().getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Please, go to this page to say that your agree to an offer: http://localhost:8080/developer/job-offer. Here is my email to respond (%s)",
                    developer.getUser().getUsername(),
                    user.getEmail()
            );
            send(developer.getUser().getEmail(), "Job ", message);
        }
        attributes.addFlashAttribute("message", "You successfully send message with offer to " + developer.getUser().getEmail() + ". Please expect a reply.");
    }

    @Override
    public void getOfferFromEmployee(final User user) {
        Developer developer = user.getDeveloper();
        developer.setEnable(false);
        user.setDeveloper(developer);
        userRepo.save(user);
    }

    @Override
    public void sendActivationCode(final User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome. Please, visit next link to activate your account: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            send(user.getEmail(), "Activation code", message);
        }
    }

    @Override
    public void sendResumeToEmployee(final User user, final Long id,
                                     final RedirectAttributes attributes) {
        Job job = jobRepo.getOne(id);
        Employee employee = job.getEmployee();

        if (!StringUtils.isEmpty(employee.getUser().getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Please, go to this page to see my resume: http://localhost:8080/resume/preview/%s. Here is my email to respond (%s)",
                    employee.getUser().getUsername(),
                    user.getDeveloper().getId(),
                    user.getEmail()
            );
            send(employee.getUser().getEmail(), "Job", message);
        }
        attributes.addFlashAttribute("message", "You successfully send message with your resume to " + employee.getUser().getEmail() + ". Please expect a reply.");
    }
}