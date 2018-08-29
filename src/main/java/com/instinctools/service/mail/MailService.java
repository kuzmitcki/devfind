package com.instinctools.service.mail;

import com.instinctools.entities.userEntites.User;
import com.instinctools.service.exceptions.DeveloperNotFoundException;
import com.instinctools.service.exceptions.JobNotFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MailService {
    void send(String emailTo, String subject, String message);

    void sendOfferToDeveloper(User user, Long id, RedirectAttributes attributes) throws DeveloperNotFoundException;

    void getOfferFromEmployee(User user);

    void sendActivationCode(User user);

    void sendResumeToEmployee(User user, Long id, RedirectAttributes attributes) throws JobNotFoundException;
}
