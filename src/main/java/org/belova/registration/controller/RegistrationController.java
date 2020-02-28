package org.belova.registration.controller;

import org.belova.registration.form.UserForm;
import org.belova.registration.models.User;
import org.belova.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationModel(Model model) {
        UserForm userForm = new UserForm();
        userForm.setGender("male");
        model.addAttribute("userForm", userForm);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserForm userForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        User user = new User(userForm.getEmail(), userForm.getName(), userForm.getGender(), userForm.getCountry(), userForm.getCity(), LocalDate.parse(userForm.getDateBith(), formatter), userForm.getCategory(), userForm.getClub(), userForm.getPassword(), "USER");
        userService.addUser(user);
        return "redirect:/login";
    }







}
