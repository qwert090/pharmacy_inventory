package com.example.medicationservice.repository;

import com.example.medicationservice.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByNameContainingIgnoreCase(String name);
    List<Medication> findByCategoryContainingIgnoreCase(String category);
}