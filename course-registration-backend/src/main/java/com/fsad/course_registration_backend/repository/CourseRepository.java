package com.fsad.course_registration_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fsad.course_registration_backend.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{
}