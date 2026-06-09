package com.fiap.floodmonitoring.auth.dto;

import com.fiap.floodmonitoring.auth.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Nome e obrigatorio.")
        String name,

        @NotBlank(message = "E-mail e obrigatorio.")
        @Email(message = "E-mail deve ser valido.")
        String email,

        @NotBlank(message = "Senha e obrigatoria.")
        @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres.")
        String password,

        @NotNull(message = "Role e obrigatoria.")
        Role role
) {
}
