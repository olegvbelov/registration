package org.belova.registration.controller;

import org.belova.registration.form.DistanceForm;
import org.belova.registration.form.EventForm;
import org.belova.registration.form.ParticipantForm;
import org.belova.registration.models.Distance;
import org.belova.registration.models.Event;
import org.belova.registration.models.Participant;
import org.belova.registration.models.User;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class RegisterCompetitionController {

    Participant participant;

    User user;
    Event event;

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    DistanceService distanceService;

    @Autowired
    public JavaMailSender emailSender;

    public void sendMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(participant.getEmail());
        mailMessage.setSubject("Activation");
        String message = String.format("Здравствуйте!\n" + "Пожалуйста, перейдите по ссылке для подтверждения регистрации http://localhost:8080/activate/%s", participant.getToken());
        mailMessage.setText(message);
        emailSender.send(mailMessage);
    }
    @RequestMapping(value = "/unregisterUser/registrationCompetition/{id}", method = RequestMethod.GET, params = "action=register")
    public String registrationPageForUnregister(@PathVariable Long id, Model model) {
        ParticipantForm participantForm = new ParticipantForm();
        participantForm.setGender("male");
        model.addAttribute("participantForm", participantForm);
        Event event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(event);
        eventForm.setId(id);
        model.addAttribute("event", eventForm);
        return  "/unregisterUser/registrationCompetition";
    }

    @RequestMapping(value = "unregisterUser/registrationCompetition/{id}", method = RequestMethod.POST)
    public String registerParticipant(@PathVariable Long id, @ModelAttribute ParticipantForm participantForm, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Event event = eventService.findEventById(id);
        if(event.findParticipant(participantForm.getEmail())) {
            boolean auth = false;
            model.addAttribute("auth", auth);
            return "/errors/existingParticipant";
        }
        participant = new Participant(participantForm.getEmail(), participantForm.getName(), participantForm.getGender(), participantForm.getCountry(), participantForm.getCity(), LocalDate.parse(participantForm.getDateBith(), formatter), participantForm.getCategory(), participantForm.getClub(), false);
        participant.setEvent(event);
        event.addParticipant(participantService.addParticipant(participant));
        eventService.addEvent(event);
        DistanceForm distanceForm = new DistanceForm();
        EventForm eventForm= new EventForm(event);
        eventForm.setDistances(event.getDistances());
        eventForm.setId(event.getId());
        model.addAttribute("distanceForm", distanceForm);
        model.addAttribute("event", eventForm);
        return "/unregisterUser/chooseDistance";
    }

    @RequestMapping(value = "/unregisterUser/registrationCompetition/{id}", method = RequestMethod.GET, params = "action=back")
    public String backForUnregister() {
        return "redirect:/";
    }

    @RequestMapping(value = "/unregisterUser/chooseDistance/{id}", method = RequestMethod.POST)
    public String chooseDistanceForUnregister(@PathVariable Long id, @ModelAttribute Distance distanceForm) {
        Event event = eventService.findEventById(id);
        Distance distance = distanceService.findByDistanceNameAndEvent(distanceForm.getDistanceName(), event);
        participant.setDistance(distance);
        distance.addParticipant(participant);
        eventService.addEvent(event);
        participantService.addParticipant(participant);
        distanceService.addDistance(distance);
        sendMessage();
        return "redirect:/";
    }
    @RequestMapping(value = "/user/registrationCompetition/{id}", method = RequestMethod.GET, params = "action=register")
    public String registrationPage(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        Event event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(event);
        eventForm.setId(event.getId());
        eventForm.setDistances(event.getDistances());
        model.addAttribute("event", eventForm);
        if(userService.getByEmail(auth.getName()) != null) {
            DistanceForm distanceForm = new DistanceForm();
            model.addAttribute("distanceForm", distanceForm);
            return "/user/registrationCompetition";
        }
        ParticipantForm participantForm = new ParticipantForm();
        model.addAttribute("participantForm", participantForm);
        return  "/unregisterUser/registrationCompetition";
    }

    @RequestMapping(value = "/user/registrationCompetition/{id}", method = RequestMethod.GET, params = "action=back")
    public String back() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.getByEmail(auth.getName()) != null) {
            return "redirect:/user/index";
        }
        return "redirect:/mainPage";
    }

    @RequestMapping(value = "/user/confirmed", method = RequestMethod.GET)
    public String confirmed() {
        return "/user/confirmed";
    }

    @RequestMapping(value = "/user/chooseDistance/{id}", method = RequestMethod.POST)
    public String registrationUser(@PathVariable Long id, @ModelAttribute Distance distanceForm) throws MessagingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userService.getByEmail(auth.getName());
        Event event = eventService.findEventById(id);
        if(user != null){
            Distance distance = distanceService.findByDistanceNameAndEvent(distanceForm.getDistanceName(), event);
            for (Participant participant: event.getParticipantes()) {
                if(participant.getEmail().equals(user.getEmail())) {
                    return "/user/errors/existingParticipant";
                }
            }
            participant = new Participant(user.getEmail(), user.getName(), user.getGender(), user.getCountry(),user.getCity(), user.getDateBith(), user.getCategory(), user.getClub(), false);
            participant.setDistance(distance);
            participant = participantService.addParticipant(participant);
            participant.setEvent(event);
            distance.addParticipant(participant);
            event.addParticipant(participant);
            eventService.addEvent(event);
            participantService.addParticipant(participant);
            distanceService.addDistance(distance);
            sendMessage();
        }
        return "redirect:/user/index";
    }
    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    String Activate(@PathVariable("token") String token) {
        Participant participantConf = participantService.findByToken(token);
        if(participantConf != null) {
            participantConf.setConfirmed(true);
            participantService.addParticipant(participantConf);
            if(userService.getByEmail(participantConf.getEmail())!= null) {
                return "redirect:/user/confirmed";
            }
            else {
                return "/unregisterUser/confirmed";
            }
        }
        return "/user/errors/existinParticipant";
    }
    @RequestMapping(value = "/unregisterUser/confirmed", method = RequestMethod.GET)
    public String confirmedForUnregister() {
        return "/unregisterUser/confirmed";
    }
}
