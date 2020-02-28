package org.belova.registration.controller;

import org.belova.registration.form.DistanceForm;
import org.belova.registration.form.EventForm;
import org.belova.registration.form.ParticipantForm;
import org.belova.registration.models.*;
import org.belova.registration.service.DistanceService;
import org.belova.registration.service.EventService;
import org.belova.registration.service.ParticipantService;
import org.belova.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UnregisterUserController {

    Event event;

    Participant participant;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    DistanceService distanceService;

    @RequestMapping(value = "unregisterUser/Competitions", method = RequestMethod.GET)
    public String showCompetitions(Model model) {
        model.addAttribute("events", eventService.fildAll());
        return "/unregisterUser/Competitions";
    }

    @RequestMapping(value = "/unregisterUser/Event/{id}", method = RequestMethod.GET)
    public String showEvent(@PathVariable("id") Long id, Model model) {
        event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(eventService.findEventById(id));
        model.addAttribute("event", eventForm);
        return "/unregisterUser/Event";
    }



    @RequestMapping(value = "/unregisterUser/result/{id}", method = RequestMethod.GET)
    public String getResults(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        List<ResultRow> resultRowList = new ArrayList<>();
        for (Distance distance: event.getDistances()) {
            for (Participant participant: distance.getParticipants()) {
                String info = participant.getCountry() + " " + participant.getCity() + " " + participant.getClub();
                String gender;
                if(participant.getGender().equals("male")) {
                    gender = "M";
                }
                else {
                    gender = "Ж";
                }
                resultRowList.add(new ResultRow(participant.getName(), info, gender, distance.getDistanceName(), AdminController.determineAgeGroup(participant, event, distance.getDistanceName(), distanceService), participant.getResult()));
            }
        }
        model.addAttribute("event", event);
        model.addAttribute("results", resultRowList);
        model.addAttribute("distances", event.getDistances());
        return "/unregisterUser/eventResult";
    }
}
