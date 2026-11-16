package com.bit.backend.mappers;

import com.bit.backend.dtos.ServiceZoneDto;
import com.bit.backend.dtos.StatusDto;
import com.bit.backend.entities.ServiceZoneEntity;
import com.bit.backend.entities.StatusEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ServiceZoneMapper {

    StatusDto toStatusDto(StatusEntity entity);

    @Mapping(target = "status", source = "status")
    ServiceZoneDto toDto(ServiceZoneEntity entity);

    List<ServiceZoneDto> toDtoList(List<ServiceZoneEntity> entities);

    @Mapping(target = "status", ignore = true)
    ServiceZoneEntity toEntity(ServiceZoneDto dto);
}
