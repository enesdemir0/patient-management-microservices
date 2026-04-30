package com.pm.patientservice.mapper;
import java.time.LocalDate;

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
    return patientDTO;
  }
  

  public static Patient toModel(PatientResponseDTO patientResponseDTO) {
    Patient patient = new Patient();
    patient.setName(patientResponseDTO.getName());
    patient.setAddress(patientResponseDTO.getAddress());
    patient.setEmail(patientResponseDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientResponseDTO.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientResponseDTO.getRegistrationDate()));
    return patient;
  }
}
