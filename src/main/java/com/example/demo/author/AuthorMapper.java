package com.example.demo.author;

public class AuthorMapper {
    public static AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder()
                .name(author.getName())
                .uuid(author.getId())
                .age(author.getAge())
                .build();
    }
}
