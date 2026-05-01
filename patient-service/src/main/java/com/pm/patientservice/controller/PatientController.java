package com.pm.patientservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "API for managing patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    return ResponseEntity.ok(patientService.getPatients());
  }

  @PostMapping
  @Operation(summary = "Create a new patient", description = "Create a new patient with the provided details")
  public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a patient", description = "Update the details of an existing patient")
  public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
      @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
    return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDTO));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a patient", description = "Delete an existing patient by ID")  
  public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
