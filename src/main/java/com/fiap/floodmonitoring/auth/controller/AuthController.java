package com.fiap.floodmonitoring.auth.controller;

import com.fiap.floodmonitoring.auth.dto.AuthResponseDTO;
import com.fiap.floodmonitoring.auth.dto.LoginRequestDTO;
import com.fiap.floodmonitoring.auth.dto.RegisterRequestDTO;
import com.fiap.floodmonitoring.auth.dto.UserResponseDTO;
import com.fiap.floodmonitoring.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacao", description = "Cadastro, login e geracao de JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Cadastra um novo usuario")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Autentica usuario e retorna token JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
