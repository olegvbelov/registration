package org.belova.registration.controller;

import org.belova.registration.form.LoginForm;
import org.belova.registration.models.User;
import org.belova.registration.service.EventService;
import org.belova.registration.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "/login";
    }
    @RequestMapping(value = "/user/page", method = RequestMethod.GET)
    public String login(Principal principal) {
        User user = userService.getByEmail(principal.getName());

        if (user.getUserRole().equals("ADMIN")) {
            return "redirect:/admin/index";
        }
        return "redirect:/user/index";
    }

}
