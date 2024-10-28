package com.example.demo.view.config;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class HeaderIconLocation {
    @Resource(name = "headerLocation")
    private String fileLocation;

    public String getFileLocation() {
        return fileLocation;
    }
}
