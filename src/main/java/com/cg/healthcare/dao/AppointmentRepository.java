package com.cg.healthcare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.healthcare.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

}
