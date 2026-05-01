package com.pm.patientservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

@Service
public class PatientService {

  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingClient;


  public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingClient) {
    this.patientRepository = patientRepository;
    this.billingClient = billingClient;
  }

  public List<PatientResponseDTO> getPatients() {
    return patientRepository.findAll().stream()
        .map(patient -> new PatientMapper().toDTO(patient))
        .toList();
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
    }
    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
    billingClient.createBillingAccount(
            newPatient.getId().toString(), 
            newPatient.getName(), 
            newPatient.getEmail()
        );
    return new PatientMapper().toDTO(newPatient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

    if (!patient.getEmail().equals(patientRequestDTO.getEmail())
        && patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
    }

    patient.setName(patientRequestDTO.getName());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

    return new PatientMapper().toDTO(patientRepository.save(patient));
  }

  public void deletePatient(UUID id) {
    if (!patientRepository.existsById(id)) {
      throw new PatientNotFoundException("Patient not found with id: " + id);
    }
    patientRepository.deleteById(id);
  }
  

}
