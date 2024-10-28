package com.wesley.adopet.controller.DTO.login;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(@NotBlank String username, @NotBlank String password) {
}
