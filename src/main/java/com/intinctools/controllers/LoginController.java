package com.intinctools.controllers;


import  com.intinctools.entities.userEntites.Role;
import com.intinctools.entities.userEntites.User;
import com.intinctools.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(final UserService userService) {
        this.userService = userService;
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
    public String saveDeveloper(User user,
                                @RequestParam(name = "role", required = false) String devOrEmp,
                                Model model,
                                RedirectAttributes attribute) {
        if (!userService.saveUser(user, devOrEmp)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        attribute.addFlashAttribute("activate", "Check your email to activate account");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }

    @GetMapping("/check")
    public String check(@AuthenticationPrincipal User user,
                        Model model){
        if (user.getActivationCode() == null){
            if (user.getRoles().contains(Role.DEVELOPER)){
                if (user.getDeveloper().getFirstName() == null || user.getDeveloper().getLastName() == null){
                    return "developer/welcome";
                }
                return "redirect:/developer/resume";
            } else {
                if(user.getEmployee().getJobs().isEmpty()){
                    return "employee/welcome";
                }
                return "redirect:/employee/jobs";
            }
        }
        model.addAttribute("message", "Please activate your account");
        return "check";
    }

    @GetMapping("/change/{code}")
    public String change(Model model, @PathVariable String code, @AuthenticationPrincipal User user) {
        model.addAttribute("developer", user.getDeveloper());
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "Email successfully changed");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }
        return "redirect:/developer/resume";
    }

}
