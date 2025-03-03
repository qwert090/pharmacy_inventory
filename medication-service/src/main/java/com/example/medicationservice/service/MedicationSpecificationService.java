package com.example.medicationservice.service;

import com.example.medicationservice.model.Medication;
import com.example.medicationservice.model.MedicationFilterDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MedicationSpecificationService {
    private static final String LIKE_MASK = "%%%s%%";

    public Specification<Medication> getSpecification(MedicationFilterDto filterDto){
        return Specification.allOf(
                nameLike(filterDto.getName()),
                categoryLike(filterDto.getCategory())
        );
    }

    private Specification<Medication> nameLike(String value) {
        if (value == null) {
            return ((root, query, builder) -> builder.conjunction());
        } else {
            return ((root, query, builder) -> builder.equal(root.get("name"), LIKE_MASK.formatted(value)));
        }
    }

    private Specification<Medication> categoryLike(String value) {
        if (value == null) {
            return ((root, query, builder) -> builder.conjunction());
        } else {
            return ((root, query, builder) -> builder.equal(root.get("category"), LIKE_MASK.formatted(value)));
        }
    }
}
