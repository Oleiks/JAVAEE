package com.example.demo.controller;

import com.example.demo.author.AuthorService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "api/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private AuthorService authorService;

    private final Jsonb jsonb = JsonbBuilder.create();


    @Override
    public void init() throws ServletException {
        System.out.println("Kurwa");
        authorService = (AuthorService) getServletContext().getAttribute("authorService");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (path.matches("/authors/?")) {
            response.setContentType("application/json");
            response.getWriter().write(jsonb.toJson(authorService.findAll()));
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
}
