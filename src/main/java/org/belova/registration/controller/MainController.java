package org.belova.registration.controller;

import org.belova.registration.form.EventForm;
import org.belova.registration.models.Event;
import org.belova.registration.service.EventService;
import org.belova.registration.service.FileSystemService;
import org.belova.registration.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    EventService eventService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    FileSystemService fileSystemService;

    @RequestMapping(value = "/load/{filename}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileSystemService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = {"/", "/mainPage"}, method = RequestMethod.GET)
    public String mainPage(Model model) {
        LocalDateTime today = LocalDateTime.now();
        List <EventForm> events = new ArrayList<EventForm>();
        for (Event event: eventService.fildAll()) {
            if(!today.isAfter(event.getDateCompetition())) {
                events.add(new EventForm(event));
            }
        }
        model.addAttribute("events", events);
        return "/mainPage";
    }
    @RequestMapping(value = "/unregisterUser/results", method = RequestMethod.GET)
    String results(Model model){
        LocalDateTime today = LocalDateTime.now();
        List<EventForm> events = new ArrayList<EventForm>();
        for (Event event: eventService.fildAll()) {
            if(today.isAfter(event.getDateCompetition())) {
                if(!participantService.findByEvent(event).isEmpty() && !participantService.findByEvent(event).get(0).getResult().equals("none")) {
                    events.add(new EventForm(event));
                }
            }
        }
        model.addAttribute("events", events);
        return "/unregisterUser/results";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
