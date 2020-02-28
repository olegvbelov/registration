package org.belova.registration.controller;

import org.belova.registration.form.*;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.belova.registration.models.User;
import org.belova.registration.repositories.DistanceRepository;
import org.belova.registration.service.DistanceService;
import org.belova.registration.service.EventService;
import org.belova.registration.service.ParticipantService;
import org.belova.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    public JavaMailSender emailSender;

    Event event;

    User user;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    DistanceService distanceService;

    @RequestMapping(value = "/user/index", method = RequestMethod.GET)
    public String userIndex(Model model) {
        LocalDateTime today = LocalDateTime.now();
        List<EventForm> events = new ArrayList();
        for (Event event: eventService.fildAll()) {
            if(!today.isAfter(event.getDateCompetition())) {
                events.add(new EventForm(event));
            }
        }
        model.addAttribute("events", events);
        return "/user/index";
    }

    @RequestMapping(value = "/user/results", method = RequestMethod.GET)
    public String results(Model model) {
        LocalDateTime today = LocalDateTime.now();
        List<EventForm> events = new ArrayList();
        for (Event event: eventService.fildAll()) {
            if(today.isAfter(event.getDateCompetition())) {
                events.add(new EventForm(event));
            }
        }
        model.addAttribute("events", events);
        return "/user/results";
    }

    @RequestMapping(value = "/user/Event/{id}", method = RequestMethod.GET)
    public String showEvent(@PathVariable("id") Long id, Model model) {
        event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(eventService.findEventById(id));
        model.addAttribute("event", eventForm);
        return "/user/Event";
    }
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public String userInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userService.getByEmail(auth.getName());
        model.addAttribute("user", user);
        return "/user/info";
    }
    @RequestMapping(value = "/user/setInfo", method = RequestMethod.GET, params = "action=changeInfo")
    public String changeInfoForm(Model model) {
        ParticipantForm participantForm = new ParticipantForm();
        participantForm.setName(user.getName());
        participantForm.setGender(user.getGender());
        participantForm.setCountry(user.getCountry());
        participantForm.setCity(user.getCity());
        participantForm.setCategory(user.getCategory());
        participantForm.setClub(user.getClub());
        participantForm.setDateBith(user.getDateBith().toString());
        model.addAttribute("participantForm", participantForm);
        return "/user/changeInfo";
    }
    @RequestMapping(value = "/user/setInfo", method = RequestMethod.POST)
    public String saveChanges(@ModelAttribute ParticipantForm participantForm) {
        user.setName(participantForm.getName());
        user.setCountry(participantForm.getCountry());
        user.setCity(participantForm.getCity());
        user.setCategory(participantForm.getCategory());
        user.setClub(participantForm.getClub());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setDateBith(LocalDate.parse(participantForm.getDateBith(), formatter));
        user.setGender(participantForm.getGender());
        user = userService.addUser(user);
        return "redirect:/user/info";
    }
}
