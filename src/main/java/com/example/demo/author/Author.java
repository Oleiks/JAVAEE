package com.example.demo.author;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    private UUID id;
    private String name;
    private Integer debutYear;
    private Type type;
    @ToString.Exclude
    private String password;
    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
