package com.fsad.course_registration_backend.security;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fsad.course_registration_backend.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter 
{
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException 
    {
        String authHeader =
                request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) 
        {
            String token = authHeader.substring(7);

            try 
            {
                if (!jwtUtil.validateToken(token)) 
                {
                    response.setStatus(
                            HttpServletResponse.SC_UNAUTHORIZED
                    );
                    return;
                }
            } 
            catch (Exception e) 
            {
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}