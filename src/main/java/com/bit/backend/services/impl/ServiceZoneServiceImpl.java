package com.bit.backend.services.impl;

import com.bit.backend.dtos.ServiceZoneDto;
import com.bit.backend.entities.ServiceZoneEntity;
import com.bit.backend.entities.StatusEntity;
import com.bit.backend.exceptions.AppException;
import com.bit.backend.mappers.ServiceZoneMapper;
import com.bit.backend.repositories.ServiceZoneRepository;
import com.bit.backend.repositories.StatusRepository;
import com.bit.backend.services.ServiceZoneServiceI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceZoneServiceImpl implements ServiceZoneServiceI {

    private final ServiceZoneRepository zoneRepository;
    private final StatusRepository statusRepository;
    private final ServiceZoneMapper zoneMapper;

    public ServiceZoneServiceImpl(ServiceZoneRepository zoneRepository,
                               StatusRepository statusRepository,
                               ServiceZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.statusRepository = statusRepository;
        this.zoneMapper = zoneMapper;
    }

    @Override
    @Transactional
    public ServiceZoneDto addZone(ServiceZoneDto dto) {
        validate(dto);
        if (zoneRepository.existsByZoneNameIgnoreCase(dto.getZoneName().trim())) {
            throw new AppException("A zone with this name already exists", HttpStatus.BAD_REQUEST);
        }
        ServiceZoneEntity entity = zoneMapper.toEntity(dto);
        entity.setId(null);
        entity.setZoneName(dto.getZoneName().trim());
        entity.setStatus(resolveStatus(dto));

        ServiceZoneEntity saved = zoneRepository.save(entity);
        if (saved.getZoneCode() == null || saved.getZoneCode().isBlank()) {
            saved.setZoneCode(String.format("Zone-%03d", saved.getId()));
            saved = zoneRepository.save(saved);
        }
        return zoneMapper.toDto(saved);
    }

    @Override
    public List<ServiceZoneDto> getAllZones() {
        return zoneMapper.toDtoList(zoneRepository.findAll());
    }

    @Override
    public ServiceZoneDto getZoneById(long id) {
        return zoneMapper.toDto(find(id));
    }

    @Override
    @Transactional
    public ServiceZoneDto updateZone(long id, ServiceZoneDto dto) {
        validate(dto);
        ServiceZoneEntity existing = find(id);
        if (zoneRepository.existsByZoneNameIgnoreCaseAndIdNot(dto.getZoneName().trim(), id)) {
            throw new AppException("A zone with this name already exists", HttpStatus.BAD_REQUEST);
        }
        existing.setZoneName(dto.getZoneName().trim());
        existing.setCity(dto.getCity());
        existing.setDescription(dto.getDescription());
        existing.setStatus(resolveStatus(dto));
        return zoneMapper.toDto(zoneRepository.save(existing));
    }

    @Override
    @Transactional
    public ServiceZoneDto deleteZone(long id) {
        ServiceZoneEntity existing = find(id);
        ServiceZoneDto dto = zoneMapper.toDto(existing);
        try {
            zoneRepository.delete(existing);
            zoneRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(
                    "This zone is used by other records. Set it to Inactive instead of deleting.",
                    HttpStatus.CONFLICT);
        }
        return dto;
    }

    private ServiceZoneEntity find(long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new AppException("Zone not found", HttpStatus.NOT_FOUND));
    }

    private void validate(ServiceZoneDto dto) {
        if (dto.getZoneName() == null || dto.getZoneName().isBlank()) {
            throw new AppException("Zone name is required", HttpStatus.BAD_REQUEST);
        }
    }

    private StatusEntity resolveStatus(ServiceZoneDto dto) {
        if (dto.getStatus() == null || dto.getStatus().getId() == null) {
            throw new AppException("Status is required", HttpStatus.BAD_REQUEST);
        }
        return statusRepository.findById(dto.getStatus().getId())
                .orElseThrow(() -> new AppException("Status not Found", HttpStatus.BAD_REQUEST));
    }
}


