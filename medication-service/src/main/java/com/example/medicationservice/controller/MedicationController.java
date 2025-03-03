package com.example.medicationservice.controller;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.model.MedicationFilterDto;
import com.example.medicationservice.model.MedicationResponseDto;
import com.example.medicationservice.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medications")
public class MedicationController {
    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public ResponseEntity<Page<Medication>> getMedications(
            @PageableDefault(sort = "name") Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        MedicationFilterDto filterDto = MedicationFilterDto.builder()
                .category(category)
                .name(name)
                .build();
        Page<Medication> medications = medicationService.getAllMedications(filterDto, pageable);
        return ResponseEntity.ok(medications);
    }

    @PostMapping("/by-ids")
    public ResponseEntity<List<MedicationResponseDto>> getMedicationById(@RequestBody List<Long> ids){
        return ResponseEntity.ok(medicationService.getAllMedicationByIds(ids));
    }

    @PostMapping
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication createdMedication = medicationService.addMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedication);
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