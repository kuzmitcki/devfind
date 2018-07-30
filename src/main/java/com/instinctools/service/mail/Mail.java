package com.instinctools.service.mail;

import com.instinctools.entities.userEntites.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface Mail {
    void send(String emailTo, String subject, String message);

    void sendOfferToDeveloper(User user, Long id, RedirectAttributes attributes);

    void getOfferFromEmployee(User user);

    void sendActivationCode(User user);

    void sendResumeToEmployee(User user, Long id, RedirectAttributes attributes);
}
