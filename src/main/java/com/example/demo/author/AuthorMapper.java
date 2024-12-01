package com.example.demo.author;

import java.util.List;
import java.util.UUID;

public class AuthorMapper {
    public static AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder()
                .name(author.getName())
                .debutYear(author.getDebutYear())
                .type(author.getType())
                .uuid(author.getId())
                .build();
    }

    public static Author toAuthor(AuthorCommand author) {
        return Author.builder()
                .id(UUID.randomUUID())
                .name(author.getName())
                .debutYear(author.getDebutYear())
                .type(author.getType())
                .password(author.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
