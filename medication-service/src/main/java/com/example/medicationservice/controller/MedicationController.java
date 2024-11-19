package com.example.medicationservice.controller;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/medications")
public class MedicationController {
    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<Medication>> getMedications(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        List<Medication> medications = medicationService.getAllMedications(name, category);
        return ResponseEntity.ok(medications);
    }

    @PostMapping
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication createdMedication = medicationService.addMedication(medication);
        return ResponseEntity.status(201).body(createdMedication);
    }

    @PutMapping("/{medication_id}")
    public ResponseEntity<Medication> updateMedication(
            @PathVariable("medication_id") Long medicationId,
            @RequestBody Medication medication) {
        Medication updatedMedication = medicationService.updateMedication(medicationId, medication);
        return ResponseEntity.ok(updatedMedication);
    }

    @DeleteMapping("/{medication_id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable("medication_id") Long medicationId) {
        medicationService.deleteMedication(medicationId);
        return ResponseEntity.noContent().build();
    }
}