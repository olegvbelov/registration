package org.belova.registration.controller;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.belova.registration.form.AgeGroupForm;
import org.belova.registration.form.DistanceForm;
import org.belova.registration.form.EventForm;
import org.belova.registration.models.AgeGroup;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.service.AgeGroupService;
import org.belova.registration.service.DistanceService;
import org.belova.registration.service.EventService;
import org.belova.registration.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
public class CreateCompetitionController {
    @Autowired
    private EventService eventService;
    @Autowired
    private DistanceService distanceService;
    @Autowired
    private AgeGroupService ageGroupService;
    private Event event;
    private Distance distance;

    @Autowired
    FileSystemService fileSystemService;


    @RequestMapping(value = "/admin/createCompetition", method = RequestMethod.GET)
    String createPage(Model model) {
        EventForm eventForm = new EventForm();
        model.addAttribute("eventForm", eventForm);
        return "/admin/createCompetition";
    }

    @RequestMapping(value="/admin/createCompetition", method=RequestMethod.POST)
    String handleFileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute EventForm eventF){
        try {
            Event eventForm = new Event();
            eventForm.setNameCompetition(eventF.getNameCompetition());
            eventForm.setInfo(eventF.getInfo());
            eventForm.setLocation(eventF.getLocation());
            eventForm.setPosition(fileSystemService.store(file));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            eventForm.setDateCompetition(LocalDateTime.parse(eventF.getDateCompetition(), formatter));
            event = eventService.addEvent(eventForm);
            return "redirect:/admin/addDistance";
        } catch (Exception e) {
            return "redirect:/admin/createCompetition";
        }
    }

    @RequestMapping(value = "/admin/addDistance", method = RequestMethod.GET)
    String addDistancePage(Model model) {
        DistanceForm distanceForm = new DistanceForm();
        model.addAttribute("event", event);
        model.addAttribute("distanceForm", distanceForm);
        return "/admin/addDistance";
    }

    //почему дистанции добавляются как Long, а потом считаются char?
    @RequestMapping(value = "/admin/addDistance", method = RequestMethod.POST)
    String addDistance(@ModelAttribute Distance distanceForm, Model model) {
        distanceForm.setEvent(event);
        distance = distanceService.addDistance(distanceForm);
        event.addDistance(distance);
        eventService.addEvent(event);
        DistanceForm distanceForm1 = new DistanceForm();
        model.addAttribute("event", event);
        model.addAttribute("distanceForm", distanceForm1);
        return "redirect:/admin/addAgeGroup";
    }

    @RequestMapping(value = "/admin/saveDistance", method = RequestMethod.GET)
    String saveDistance() {
        return "redirect:/admin/addDistance";
    }

    @RequestMapping(value = "/admin/addAgeGroup", method = RequestMethod.GET)
    String addAgeGroupPage(Model model) {
        AgeGroupForm ageGroupForm = new AgeGroupForm();
        ageGroupForm.setAgeType("year");
        model.addAttribute("distance", distance);
        model.addAttribute("ageGroupForm", ageGroupForm);
        return "/admin/addAgeGroup";
    }

    @RequestMapping(value = "admin/addAgeGroup", method = RequestMethod.POST)
    String addAgeGroup(@ModelAttribute AgeGroup ageGroupForm) {
        ageGroupForm.setDistance(distance);
        if(ageGroupForm.getGroupEnd().isEmpty() && ageGroupForm.getGroupBegin().isEmpty()) {
            ageGroupForm.setGroupBegin("абсолют");
            ageGroupForm.setGroupEnd("абсолют");
            ageGroupForm.setNameGroup("абсолют");
        }
        else {
            if(ageGroupForm.getGroupBegin().isEmpty()) {
                ageGroupForm.setGroupBegin(ageGroupForm.getGroupEnd());
                ageGroupForm.setGroupEnd("и моложе");
            }
            else {
                if(ageGroupForm.getGroupEnd().isEmpty()) {
                    ageGroupForm.setGroupEnd("и старше");
                }
            }
            ageGroupForm.setNameGroup(ageGroupForm.getGroupBegin() + " - " + ageGroupForm.getGroupEnd());
        }
        distance.addAgeGroup(ageGroupForm);
        ageGroupService.addAgeGroup(ageGroupForm);
        distanceService.addDistance(distance);
        return "redirect:/admin/addAgeGroup";
    }
    @RequestMapping(value="/admin/deleteDistance/{id}", method = RequestMethod.GET)
    public String deleteDistance(@PathVariable("id") Long id, Model model) {
        distanceService.deleteDistance(distanceService.findDistanceById(id));
        DistanceForm distanceForm = new DistanceForm();
        model.addAttribute("event", event);
        model.addAttribute("distanceForm", distanceForm);
        return "/admin/addDistance";
    }
}
