package com.rslakra.microservice.productservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base class for entities that need audit fields.
 * Uses @MappedSuperclass to allow inheritance of audit fields.
 *
 * @author Rohtash Lakra
 * @created 3/2/24 2:46 PM
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Auditable {

    // Audit fields
    @Column(name = "created_on", nullable = false)
    private Long createdOn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 64)
    private String createdBy;

    @Column(name = "updated_on", nullable = false)
    private Long updatedOn;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false, length = 64)
    private String updatedBy;

}
