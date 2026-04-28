package com.fsad.course_registration_backend.dto;

import lombok.Data;

@Data
public class AuthRequest 
{
    private String username;
    private String password;
    private String role;
}