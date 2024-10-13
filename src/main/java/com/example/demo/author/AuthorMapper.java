package com.example.demo.author;

public class AuthorMapper {
    public static AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder()
                .name(author.getName())
                .debutYear(author.getDebutYear())
                .type(author.getType())
                .uuid(author.getId())
                .build();
    }
}
