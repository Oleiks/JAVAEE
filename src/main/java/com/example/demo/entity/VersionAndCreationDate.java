package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VersionAndCreationDate {

    @Version
    private Long version;

    @Column(name = "creationDateTime")
    private LocalDateTime creationDateTime;

    @Column(name = "updateDateTime")
    private LocalDateTime updateDateTime;

    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
        updateDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateUpdateDateTime() {
        updateDateTime = LocalDateTime.now();
    }
}
