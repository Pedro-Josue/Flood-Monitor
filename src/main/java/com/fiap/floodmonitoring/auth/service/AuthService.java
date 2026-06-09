package com.fiap.floodmonitoring.auth.service;

import com.fiap.floodmonitoring.auth.dto.AuthResponseDTO;
import com.fiap.floodmonitoring.auth.dto.LoginRequestDTO;
import com.fiap.floodmonitoring.auth.dto.RegisterRequestDTO;
import com.fiap.floodmonitoring.auth.dto.UserResponseDTO;
import com.fiap.floodmonitoring.auth.model.User;
import com.fiap.floodmonitoring.auth.repository.UserRepository;
import com.fiap.floodmonitoring.auth.security.JwtService;
import com.fiap.floodmonitoring.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public UserResponseDTO register(RegisterRequestDTO request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new BusinessException("Ja existe usuario cadastrado com este e-mail.");
        }

        User user = User.builder()
                .name(request.name().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        return toResponse(userRepository.save(user));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(normalizedEmail);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponseDTO(token, TOKEN_TYPE);
    }

    private UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
