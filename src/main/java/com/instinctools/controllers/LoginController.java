package com.instinctools.controllers;

import com.instinctools.entities.userEntites.Role;
import com.instinctools.entities.userEntites.User;
import com.instinctools.repo.UserRepo;
import com.instinctools.service.exceptions.ResourceNotFoundException;
import com.instinctools.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public LoginController(final UserService userService,
                           final UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping()
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String developerForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String saveDeveloper(final User user,
                                final @RequestParam(name = "role") String devOrEmp,
                                final Model model,
                                final RedirectAttributes attribute) {
        if (!userService.saveUser(user, devOrEmp)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        attribute.addFlashAttribute("activate", "Check your email to activate account");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(final Model model,
                           final @PathVariable String code) throws ResourceNotFoundException {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }

    @GetMapping("/check")
    public String check(final @AuthenticationPrincipal User user,
                        final Model model) {
        if (user.getActivationCode() == null) {
            if (user.getRoles().contains(Role.DEVELOPER)) {
                if (user.getDeveloper().getFirstName() == null || user.getDeveloper().getLastName() == null) {
                    return "developer/welcome";
                }
                return "redirect:/developer/resume";
            } else {
                if (user.getEmployee().getJobs().isEmpty()) {
                    return "employee/welcome";
                }
                return "redirect:/employee/jobs";
            }
        }
        model.addAttribute("message", "Please activate your account (" + user.getEmail() + ")");
        return "check";
    }

    @GetMapping("/change/{code}")
    public String change(final Model model,
                         final @PathVariable String code,
                         final @AuthenticationPrincipal User user,
                         final HttpServletRequest request) throws ResourceNotFoundException {
        model.addAttribute("developer", user.getDeveloper());
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            user.setEmail(String.valueOf(request.getSession().getAttribute("email")));
            user.setActivationCode(null);
            userRepo.save(user);
            model.addAttribute("message", "Email successfully changed");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "redirect:/developer/resume";
    }
}
