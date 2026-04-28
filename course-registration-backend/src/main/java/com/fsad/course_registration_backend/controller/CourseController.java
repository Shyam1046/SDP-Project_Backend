package com.fsad.course_registration_backend.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fsad.course_registration_backend.entity.Course;
import com.fsad.course_registration_backend.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CourseController
{
    private final CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses()
    {
        return ResponseEntity.ok(courseRepository.findAll());
    }
}