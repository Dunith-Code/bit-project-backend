package com.bit.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "service_zone")
public class ServiceZoneEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_code")
    private String zoneCode;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    @Column(name = "city")
    private String city;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    public ServiceZoneEntity() {}

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
    public StatusEntity getStatus() { return status; }
    public void setStatus(StatusEntity status) { this.status = status; }

}
