package com.fsad.course_registration_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsad.course_registration_backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndRole(String username, String role);
}