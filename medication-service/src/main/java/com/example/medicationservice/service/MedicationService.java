package com.example.medicationservice.service;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;

    @Cacheable("medications")
    public List<Medication> getAllMedications(String name, String category) {
        if (name != null && name.length() > 0) {
            return medicationRepository.findByNameContainingIgnoreCase(name);
        }
        if (category != null && category.length() > 0) {
            return medicationRepository.findByCategoryContainingIgnoreCase(category);
        }
        return medicationRepository.findAll();
    }

    public Medication addMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(Long id, Medication medication) {
        medication.setId(id);
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}