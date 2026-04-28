package com.fsad.course_registration_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsad.course_registration_backend.entity.Registration;

public interface RegistrationRepository
        extends JpaRepository<Registration, Long>
{
    List<Registration> findByUserId(Long userId);
}