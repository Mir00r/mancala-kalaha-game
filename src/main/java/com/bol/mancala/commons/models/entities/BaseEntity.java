package com.bol.mancala.commons.models.entities;

import com.bol.mancala.utils.DateUtil;
import com.bol.mancala.utils.TimeUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "uuid_str", nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    private void onBasePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
        this.createdBy = this.getLoggedInUsername();
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = this.getLoggedInUsername();
    }

    public boolean isNew() {
        return this.id == null;
    }

    @JsonIgnore
    public String getLoggedInUsername() {
        return "admin";
    }

    @JsonIgnore
    public String getCreatedAtReadable() {
        return TimeUtility.readableDateTimeFromInstant(this.createdAt);
    }

    @JsonIgnore
    public String getUpdatedAtReadable() {
        return TimeUtility.readableDateTimeFromInstant(this.updatedAt);
    }

    public String getReadableDate(Date date) {
        return DateUtil.getReadableDate(date);
    }
}
