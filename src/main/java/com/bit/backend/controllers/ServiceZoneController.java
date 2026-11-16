package com.bit.backend.controllers;

import com.bit.backend.dtos.ApiListResponse;
import com.bit.backend.dtos.ServiceZoneDto;
import com.bit.backend.services.ServiceZoneServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class ServiceZoneController {

    private final com.bit.backend.services.ServiceZoneServiceI zoneService;

    public ServiceZoneController(com.bit.backend.services.ServiceZoneServiceI zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/service-zone")
    public ResponseEntity<ApiListResponse<ServiceZoneDto>> getAll() {
        return ResponseEntity.ok(ApiListResponse.of(zoneService.getAllZones()));
    }

    @GetMapping("/service-zone/{id}")
    public ResponseEntity<ApiListResponse<ServiceZoneDto>> getById(@PathVariable long id) {
        return ResponseEntity.ok(ApiListResponse.ofOne(zoneService.getZoneById(id)));
    }

    @PostMapping("/service-zone")
    public ResponseEntity<ApiListResponse<ServiceZoneDto>> add(@RequestBody ServiceZoneDto dto) {
        ServiceZoneDto created = zoneService.addZone(dto);
        return ResponseEntity.created(URI.create("/api/v1/service-zone/" + created.getId()))
                .body(ApiListResponse.ofOne(created));
    }

    @PutMapping("/service-zone/{id}")
    public ResponseEntity<ApiListResponse<ServiceZoneDto>> update(@PathVariable long id,
                                                                  @RequestBody ServiceZoneDto dto) {
        return ResponseEntity.ok(ApiListResponse.ofOne(zoneService.updateZone(id, dto)));
    }

    @DeleteMapping("/service-zone/{id}")
    public ResponseEntity<ApiListResponse<ServiceZoneDto>> delete(@PathVariable long id) {
        return ResponseEntity.ok(ApiListResponse.ofOne(zoneService.deleteZone(id)));
    }
}
