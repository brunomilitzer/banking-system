package com.brunomilitzer.bank.userfront.services;

import com.brunomilitzer.bank.userfront.entities.Appointment;
import java.util.List;

public interface AppointmentService {
    void createAppointment(Appointment appointment);

    List<Appointment> findAll();
    Appointment findByAppointment(Long id);

    void confirmAppointment(Long id);
}
