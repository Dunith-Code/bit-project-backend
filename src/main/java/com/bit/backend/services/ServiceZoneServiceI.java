package com.bit.backend.services;

import com.bit.backend.dtos.ServiceZoneDto;
import java.util.List;

public interface ServiceZoneServiceI {
    ServiceZoneDto addZone(ServiceZoneDto dto);
    List<ServiceZoneDto> getAllZones();
    ServiceZoneDto getZoneById(long id);
    ServiceZoneDto updateZone(long id, ServiceZoneDto dto);
    ServiceZoneDto deleteZone(long id);
}
