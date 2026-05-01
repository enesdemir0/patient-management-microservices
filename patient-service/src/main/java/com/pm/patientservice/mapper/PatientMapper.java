package com.pm.patientservice.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

  public PatientResponseDTO toDTO(Patient patient) {
    PatientResponseDTO patientDTO = new PatientResponseDTO();
    patientDTO.setId(patient.getId().toString());
    patientDTO.setName(patient.getName());
    patientDTO.setAddress(patient.getAddress());
    patientDTO.setEmail(patient.getEmail());
    patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
    patientDTO.setRegisteredDate(patient.getRegisteredDate().toString());
    patientDTO.setAge(String.valueOf(ChronoUnit.YEARS.between(patient.getDateOfBirth(), LocalDate.now())));
    return patientDTO;
  }

  public static Patient toModel(PatientRequestDTO patientRequestDTO) {
    Patient patient = new Patient();
    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    String registeredDate = patientRequestDTO.getRegisteredDate();
    patient.setRegisteredDate(registeredDate != null ? LocalDate.parse(registeredDate) : LocalDate.now());
    return patient;
  }
}
