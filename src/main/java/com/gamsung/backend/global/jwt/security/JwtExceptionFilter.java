package com.gamsung.backend.global.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamsung.backend.global.exception.ErrorCode;
import com.gamsung.backend.global.exception.ErrorMessage;
import com.gamsung.backend.global.jwt.exception.JwtExpiredAccessTokenException;
import com.gamsung.backend.global.jwt.exception.JwtInvalidAccessTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtExpiredAccessTokenException e) {
            setErrorResponse(response, ErrorCode.JWT_EXPIRED_ACCESS_TOKEN);
        } catch (JwtInvalidAccessTokenException e) {
            setErrorResponse(response, ErrorCode.JWT_INVALID_ACCESS_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();
        body.put("code", Integer.parseInt(errorCode.getCode()));
        body.put("data", ErrorMessage.create(errorCode.getMessage()));

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
