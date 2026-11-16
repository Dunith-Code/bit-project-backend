package com.bit.backend.dtos;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class ServiceZoneDto {
    private Long id;
    private String zoneCode;
    private String zoneName;
    private String city;
    private String description;
    private StatusDto status;

    public ServiceZoneDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getZoneCode() { return zoneCode; }
    public void setZoneCode(String zoneCode) { this.zoneCode = zoneCode; }
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public StatusDto getStatus() { return status; }
    public void setStatus(StatusDto status) { this.status = status; }

    /** */
    @JsonSetter("status")
    public void setStatusFromJson(JsonNode node) {
        if (node == null || node.isNull()) { this.status = null; return; }
        StatusDto dto = new StatusDto();
        if (node.isNumber() || node.isTextual()) {
            dto.setId(node.asLong());
        } else if (node.isObject()) {
            if (node.hasNonNull("id")) dto.setId(node.get("id").asLong());
            if (node.hasNonNull("name")) dto.setName(node.get("name").asText());
        }
        this.status = dto;
    }
}
