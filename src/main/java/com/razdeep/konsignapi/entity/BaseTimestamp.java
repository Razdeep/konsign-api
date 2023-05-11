package com.razdeep.konsignapi.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseTimestamp {

    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false, nullable = false)
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private LocalDateTime updateTimestamp;

    @Column(name = "agency_id")
    private String agencyId;

}
