package com.wesley.adopet.controller.DTO.Ongs.pets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record AtualizarPetsDTO(@NotNull
                               Long id,

                               String nome,
                               @Past
                               LocalDate nascimento,
                               String descricao,
                               String tipo,
                               @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "URL inválida")
                               String foto,

                               Long idOng){
}
