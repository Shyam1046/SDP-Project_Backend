package com.fsad.course_registration_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDTO 
{
    private Long id;
    private String name;
    private String instructor;
    private int duration;
}