package com.wesley.adopet.controller.DTO.Ongs.pets;

import com.wesley.adopet.domain.ong.pets.Animal;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CadastrarPetDTO(@NotBlank(message = "Nome é obrigatório")
                              String nome,
                              @NotNull
                              @Past
                              LocalDate nascimento,
                              @NotBlank
                              String descricao,
                              @NotBlank
                              String tipo,
                              @NotBlank
                              @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "URL inválida")
                              String foto,
                              @NotNull
                              Long idOng) {
}
