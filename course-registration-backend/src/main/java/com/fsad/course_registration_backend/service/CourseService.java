package com.fsad.course_registration_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsad.course_registration_backend.dto.CourseDTO;
import com.fsad.course_registration_backend.entity.Course;
import com.fsad.course_registration_backend.repository.CourseRepository;

import org.modelmapper.ModelMapper;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public CourseDTO saveCourse(CourseDTO dto) {
        Course course = modelMapper.map(dto, Course.class);
        Course saved = repository.save(course);
        return modelMapper.map(saved, CourseDTO.class);
    }

    public List<CourseDTO> getAllCourses() {
        return repository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }
}