package com.example.demo.author;

import lombok.*;

import java.util.UUID;

@Builder
@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String name;

    private UUID uuid;

    private Integer age;

}