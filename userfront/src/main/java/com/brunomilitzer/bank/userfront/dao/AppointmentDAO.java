package com.brunomilitzer.bank.userfront.dao;

import com.brunomilitzer.bank.userfront.entities.Appointment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentDAO extends CrudRepository<Appointment, Long> {

    List<Appointment> findAll();

    Appointment findAppointmentById(Long id);
}
