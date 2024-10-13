package com.example.demo.author;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private UUID id;

    private String name;

    private Integer age;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] portrait;
}
