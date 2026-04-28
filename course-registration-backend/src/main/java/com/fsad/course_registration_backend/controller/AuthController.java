package com.fsad.course_registration_backend.controller;

import com.fsad.course_registration_backend.dto.AuthRequest;
import com.fsad.course_registration_backend.entity.User;
import com.fsad.course_registration_backend.repository.UserRepository;
import com.fsad.course_registration_backend.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username and password are required");
        }

        Optional<User> optUser = repo.findByUsername(req.getUsername());

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        User user = optUser.get();

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(
                Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "role", user.getRole(),
                        "token", token
                )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username and password are required");
        }

        if (repo.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(
                req.getRole() != null && !req.getRole().isBlank()
                        ? req.getRole()
                        : "student"
        );

        repo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "id", user.getId(),
                                "username", user.getUsername(),
                                "role", user.getRole(),
                                "message", "User registered successfully"
                        )
                );
    }
}
