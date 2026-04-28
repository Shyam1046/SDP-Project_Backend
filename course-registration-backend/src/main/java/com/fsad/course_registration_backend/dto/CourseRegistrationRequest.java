package com.fsad.course_registration_backend.dto;

import lombok.Data;

@Data
public class CourseRegistrationRequest
{
    private Long userId;
    private Long courseId;
}