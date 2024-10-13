package com.example.demo.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Builder
@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String name;
    private Integer debutYear;
    private Type type;
    private UUID uuid;
}