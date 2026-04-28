package com.fsad.course_registration_backend.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fsad.course_registration_backend.dto.CourseRegistrationRequest;
import com.fsad.course_registration_backend.entity.Course;
import com.fsad.course_registration_backend.entity.Registration;
import com.fsad.course_registration_backend.entity.User;
import com.fsad.course_registration_backend.repository.CourseRepository;
import com.fsad.course_registration_backend.repository.RegistrationRepository;
import com.fsad.course_registration_backend.repository.UserRepository;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistrationController
{
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> registerCourse(
            @RequestBody CourseRegistrationRequest request)
    {
        User user =
                userRepository.findById(request.getUserId())
                        .orElse(null);

        Course course =
                courseRepository.findById(request.getCourseId())
                        .orElse(null);

        if(user == null || course == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User or course not found");
        }

        List<Registration> existing =
                registrationRepository.findByUserId(user.getId());

        for(Registration reg : existing)
        {
            if(reg.getCourse().getTimeSlot()
                    .equals(course.getTimeSlot()))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Time slot conflict");
            }
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setCourse(course);

        registrationRepository.save(registration);

        return ResponseEntity.ok("Registered successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Registration>> getRegisteredCourses(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                registrationRepository.findByUserId(userId)
        );
    }
}