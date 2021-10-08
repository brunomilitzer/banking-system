package com.brunomilitzer.bank.userfront.services.UserServiceImpl;

import com.brunomilitzer.bank.userfront.dao.AppointmentDAO;
import com.brunomilitzer.bank.userfront.entities.Appointment;
import com.brunomilitzer.bank.userfront.services.AppointmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public void createAppointment(Appointment appointment) {
        appointmentDAO.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDAO.findAll();
    }

    @Override
    public Appointment findByAppointment(Long id) {
        return appointmentDAO.findAppointmentById(id);
    }

    @Override
    public void confirmAppointment(Long id) {
        Appointment appointment = findByAppointment(id);
        appointment.setConfirmed(true);
        appointmentDAO.save(appointment);
    }
}
