package com.fsad.course_registration_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    private final DataSource dataSource;

    public HomeController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            response.put("database", connection.getCatalog());
            response.put("url", connection.getMetaData().getURL());
            response.put("username", connection.getMetaData().getUserName());
            response.put("status", "Connected");
        } catch (Exception e) {
            response.put("status", "Failed");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
