package com.pm.patientservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

  private PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    List<PatientResponseDTO> patients = patientService.getPatients();
    return ResponseEntity.ok().body(patients);
  }

  @PostMapping
  public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientResponseDTO patientResponseDTO) {
    PatientResponseDTO createdPatient = patientService.createPatient(patientResponseDTO);
    return ResponseEntity.ok().body(createdPatient);
  }


  
}
