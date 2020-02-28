package org.belova.registration.controller;

import org.belova.registration.form.EventForm;
import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.belova.registration.service.AgeGroupService;
import org.belova.registration.service.DistanceService;
import org.belova.registration.service.EventService;
import org.belova.registration.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChangeCompetitionController {
    @Autowired
    EventService eventService;
    @Autowired
    DistanceService distanceService;
    @Autowired
    AgeGroupService ageGroupService;
    @Autowired
    ParticipantService participantService;
    Event event;

    @RequestMapping(value = "admin/addDistance/{id}", method = RequestMethod.GET, params = "action=change")
    String changePage(@PathVariable("id") Long id, Model model) {
        Event event = eventService.findEventById(id);
        EventForm eventForm = new EventForm();
        eventForm.setNameCompetition(event.getNameCompetition());
        eventForm.setInfo(event.getInfo());
        eventForm.setDateCompetition(event.getDateCompetition().toString());
        eventForm.setLocation(event.getLocation());
        model.addAttribute("eventForm", eventForm);
        return "/admin/changeCompetition";
    }
    @RequestMapping(value = "admin/addDistance/{id}", method = RequestMethod.GET, params = "action=cancel")
    String deleteCompetition(@PathVariable("id") Long id) {
        Event event = eventService.findEventById(id);
        for (Distance distance: event.getDistances()) {
            for (AgeGroup ageGroup: distance.getAgeGroups()) {
                ageGroupService.deleteAgeGroup(ageGroup);
            }
            for (Participant participant: distance.getParticipants()) {
                participantService.deleteParticipant(participant);
            }
            distanceService.deleteDistance(distance);
        }
        eventService.deleteEvent(event);
        return "/admin/index";
    }

    @RequestMapping(value = "admin/saveChange", method = RequestMethod.POST)
    public String saveChangeEvent(@ModelAttribute EventForm eventForm, Model model) {
        event.setNameCompetition(eventForm.getNameCompetition());
        event.setPosition(eventForm.getPosition());
        event.setLocation(eventForm.getLocation());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        event.setDateCompetition(LocalDateTime.parse(eventForm.getDateCompetition(), formatter));
        eventService.addEvent(event);
        model.addAttribute("event", event);
        return "admin/addDistance";
    }
}
