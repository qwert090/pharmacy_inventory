package com.example.orderservice.client;

import com.example.orderservice.dto.MedicationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "${discovery-service.application-name.medication-service}",
        path = "/api/v1/medications"
)
public interface MedicationServiceClient {

    @PostMapping("/by-ids")
    ResponseEntity<List<MedicationResponseDto>> getMedicationById(@RequestBody List<Long> ids);
}
