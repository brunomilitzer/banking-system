package com.brunomilitzer.bank.userfront.controllers;

import com.brunomilitzer.bank.userfront.entities.Appointment;
import com.brunomilitzer.bank.userfront.entities.User;
import com.brunomilitzer.bank.userfront.services.AppointmentService;
import com.brunomilitzer.bank.userfront.services.UserService;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createAppointment(Model model) {
        Appointment appointment = new Appointment();

        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");

        return "appointment";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment,
                                        @ModelAttribute("dateString") String date, Principal principal)
        throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date appointmentDate = sdf.parse(date);
        appointment.setDate(appointmentDate);

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);

        appointmentService.createAppointment(appointment);

        return "redirect:/userFront";
    }
}
