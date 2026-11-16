package com.bit.backend.repositories;

import com.bit.backend.entities.ServiceZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceZoneRepository extends JpaRepository<ServiceZoneEntity, Long> {
    boolean existsByZoneNameIgnoreCase(String zoneName);
    boolean existsByZoneNameIgnoreCaseAndIdNot(String zoneName, Long id);
}
