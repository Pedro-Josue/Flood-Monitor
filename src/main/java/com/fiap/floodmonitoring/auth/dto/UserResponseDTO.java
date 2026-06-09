package com.fiap.floodmonitoring.auth.dto;

import com.fiap.floodmonitoring.auth.model.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role
) {
}
