package com.example.demo.controller.rest;

import com.example.demo.author.AuthorCommand;
import com.example.demo.author.AuthorDto;
import com.example.demo.author.AuthorService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/authors")
public class AuthorController {
    private AuthorService authorService;

    @EJB
    public void setService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuthorDto> getAuthors() {
        return authorService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorDto getAuthor(@PathParam("id") UUID id) {
        return authorService.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putAuthor(AuthorCommand request) {
        authorService.createAuthor(request);
        return Response.ok().build();
    }
}
