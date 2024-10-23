package com.wesley.adopet.controller.DTO.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEnderecoDTO(@NotBlank
                               String cidade,

                               @NotBlank
                               @Pattern(regexp = "^\\d{4}-\\d{3}$", message = "Formato de código postal inválido. Use 4435-759")
                               String codigoPostal,

                               @NotBlank
                               String pais,

                               String complemento,
                               String rua) {
}
