package org.belova.registration.controller;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.belova.registration.form.AgeGroupForm;
import org.belova.registration.form.DistanceForm;
import org.belova.registration.form.EventForm;
import org.belova.registration.models.*;
import org.belova.registration.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    EventService eventService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    DistanceService distanceService;

    @Autowired
    AgeGroupService ageGroupService;

    @Autowired
    FileSystemService fileSystemService;

    private List<StartListRow> startListRows;

    private static final String DIRECTORY = "D:/";
    private static final String DEFAULT_FILE_NAME = "startList.xls";

    public static String determineAgeGroup(Participant participant, Event event, String distanceName, DistanceService distanceService) {
        Set<AgeGroup> ageGroups = distanceService.findByDistanceNameAndEvent(distanceName, event).getAgeGroups();
        int age = (new Date()).getYear() - participant.getDateBith().getYear();
        for (AgeGroup ageGroup:ageGroups) {
            if (ageGroup.getNameGroup().equals("абсолют")) {
                return ageGroup.getNameGroup();
            }
            if (ageGroup.getGroupEnd().equals("и моложе")) {
               if (ageGroup.getAgeType().equals("year")) {
                    if (Integer.parseInt(ageGroup.getGroupBegin()) <= participant.getDateBith().getYear()) {
                        return ageGroup.getNameGroup();
                    }
                } else {
                    if (Integer.parseInt(ageGroup.getGroupBegin()) >= age) {
                        return ageGroup.getNameGroup();
                    }
                }
            }
            else {
                if (ageGroup.getGroupEnd().equals("и старше")) {
                    if (ageGroup.getAgeType().equals("год")) {
                        if (Integer.parseInt(ageGroup.getGroupBegin()) >= participant.getDateBith().getYear()) {
                            return ageGroup.getNameGroup();
                        }
                    } else {
                        if (Integer.parseInt(ageGroup.getGroupBegin()) <= age) {
                            return ageGroup.getNameGroup();
                        }
                    }
                }
                else {
                    if ((Integer.parseInt(ageGroup.getGroupBegin()) <= participant.getDateBith().getYear()) && (Integer.parseInt(ageGroup.getGroupEnd()) >= participant.getDateBith().getYear())) {
                        return ageGroup.getNameGroup();
                    }
                }
            }
        }
        return "абсолют";
    }

    @RequestMapping(value = "admin/index", method = RequestMethod.GET)
    String indexPage(Model model){
        LocalDateTime today = LocalDateTime.now();
        List<EventForm> events = new ArrayList();
        for (Event event: eventService.fildAll()) {
            if(!today.isAfter(event.getDateCompetition())) {
                events.add(new EventForm(event));
            }
        }
        model.addAttribute("events", events);
        return "/admin/index";
    }

    @RequestMapping(value = "admin/archive", method = RequestMethod.GET)
    String archive(Model model){
        LocalDateTime today = LocalDateTime.now();
        List<EventForm> events = new ArrayList();
        for (Event event: eventService.fildAll()) {
            if(today.isAfter(event.getDateCompetition())) {
                events.add(new EventForm(event));
            }
        }
        model.addAttribute("events", events);
        return "/admin/archive";
    }

    @RequestMapping(value = "admin/Event/{id}", method = RequestMethod.GET)
    String showEvent(@PathVariable("id") Long id, Model model) {
        EventForm eventForm = new EventForm(eventService.findEventById(id));
        model.addAttribute("event", eventForm);
        return "admin/Event";
    }

    @RequestMapping(value = "admin/action/{id}", method = RequestMethod.GET, params = "action=startList")
    String startList(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        startListRows = new ArrayList<>();
        String info = "";
        for (Participant participant: participantService.findByEvent(event)) {
            info = participant.getName()+ " ";
            Date now = new Date();
            String age = Integer.toString(now.getYear() - participant.getDateBith().getYear());
            info = info + participant.getCountry() + " " + participant.getCity()+ " " + participant.getClub() + " " + participant.getCategory();
            startListRows.add(new StartListRow(participant.getName(), info, participant.getGender(), determineAgeGroup(participant, event, participant.getDistance().getDistanceName(), distanceService), participant.getDistance().getDistanceName()));
        }
        model.addAttribute("distances", distanceService.findDistancesByEvent(event));
        model.addAttribute("startListRows", startListRows);
        model.addAttribute("event", event);
        return "admin/startList";
    }
    @RequestMapping(value = "/admin/print/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> print(@PathVariable Long id, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Event event = eventService.findEventById(id);
        for (Distance distance:event.getDistances()) {
            for (AgeGroup ageGroup:distance.getAgeGroups()) {
                HSSFSheet sheetMale = workbook.createSheet(distance.getDistanceName() + " " + ageGroup.getNameGroup() + " Мужчины");
                HSSFSheet sheetFemale = workbook.createSheet(distance.getDistanceName() + " " + ageGroup.getNameGroup() + " Женщины");

                int rowNumM = 0;
                int rowNumF = 0;
                Row rowM = sheetMale.createRow(rowNumM);
                Row rowF = sheetFemale.createRow(rowNumF);

                Cell cellM = rowM.createCell(0, CellType.NUMERIC);
                cellM.setCellValue("н/п");
                Cell cellF = rowM.createCell(0, CellType.NUMERIC);
                cellF.setCellValue("н/п");

                cellM = rowM.createCell(1, CellType.STRING);
                cellM.setCellValue("Спортсмен");
                cellF = rowF.createCell(1, CellType.STRING);
                cellF.setCellValue("Спортсмен");

                cellM = rowM.createCell(2, CellType.NUMERIC);
                cellM.setCellValue("Номер");
                cellF = rowF.createCell(2, CellType.NUMERIC);
                cellF.setCellValue("Номер");

                cellM = rowM.createCell(3, CellType.STRING);
                cellM.setCellValue("Результат");
                cellF = rowF.createCell(3, CellType.STRING);
                cellF.setCellValue("Результат");
                for (StartListRow startListRow: startListRows) {
                    if (distance.getDistanceName().equals(startListRow.getDistanceName()) && ageGroup.getNameGroup().equals(startListRow.getAgeGroupName())) {
                        if (startListRow.getGender().equals("male")) {
                            rowNumM++;
                            rowM = sheetMale.createRow(rowNumM);

                            cellM = rowM.createCell(0, CellType.NUMERIC);
                            cellM.setCellValue(rowNumM);

                            cellM = rowM.createCell(1, CellType.STRING);
                            cellM.setCellValue(startListRow.getParticipant());
                        } else {
                            rowNumF++;
                            rowF = sheetFemale.createRow(rowNumF);

                            cellF = rowF.createCell(0, CellType.NUMERIC);
                            cellF.setCellValue(rowNumF);

                            cellF = rowF.createCell(1, CellType.STRING);
                            cellF.setCellValue(startListRow.getParticipant());
                        }
                    }
                }
            }
        }
        /*FileOutputStream fileOut = new FileOutputStream("startList.xls");
        response.setHeader("content-disposition", "attachment;filename=startList.xls");
        response.setContentType("application/vnd.ms-excel");
        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();*/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }

        byte[] bytes = bos.toByteArray();

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/vnd.ms-excel;");
        headers.set("content-length",Integer.toString(bytes.length));
        headers.set("Content-Disposition", "attachment; filename=startList.xls");

        return  new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "admin/action/{id}", method = RequestMethod.GET, params = "action=change")
    String changeCompetition(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        EventForm eventForm = new EventForm(event);
        model.addAttribute("eventForm", eventForm);
        return "/admin/changeCompetition";
    }
    @RequestMapping(value = "admin/action/{id}", method = RequestMethod.GET, params = "action=delete")
    String deleteCompetition(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id);
        for (Participant participant: participantService.findByEvent(event)) {
            participantService.deleteParticipant(participant);
        }
        for (Distance distance: distanceService.findDistancesByEvent(event)) {
            for (AgeGroup ageGroup: distance.getAgeGroups()) {
                ageGroupService.deleteAgeGroup(ageGroup);
            }
            distanceService.deleteDistance(distance);
        }
        eventService.deleteEvent(event);
        return "redirect:/admin/index";
    }
    @RequestMapping(value = "admin/action/{id}", method = RequestMethod.GET, params = "action=back")
    String back() {
        return "redirect:/admin/index";
    }
}
