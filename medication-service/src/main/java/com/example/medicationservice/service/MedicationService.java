package com.example.medicationservice.service;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.model.MedicationFilterDto;
import com.example.medicationservice.model.MedicationResponseDto;
import com.example.medicationservice.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationSpecificationService specificationService;

    @Cacheable("medications")
    public Page<Medication> getAllMedications(MedicationFilterDto filterDto, Pageable pageable) {
        Specification<Medication> specification = specificationService.getSpecification(filterDto);
        return medicationRepository.findAll(specification, pageable);
    }

    public List<MedicationResponseDto> getAllMedicationByIds(List<Long> medicationIds) {
        return medicationRepository.findAllById(medicationIds).stream()
                .map(medication -> MedicationResponseDto.builder()
                        .category(medication.getCategory())
                        .name(medication.getName())
                        .id(medication.getId())
                        .build())
                .toList();
    }

    public Medication addMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(Long id, Medication medication) {
        medicationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No medication by id " + id));
        medication.setId(id);
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}