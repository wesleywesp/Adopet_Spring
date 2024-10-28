package com.wesley.adopet.controller.DTO.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosLoginOng(@NotBlank
                            @Email(message = "Email inválido")
                            String email,
                            @NotBlank
                            String password) {
}
