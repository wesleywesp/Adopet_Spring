package com.wesley.adopet.controller.DTO.Ongs.pets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

public record AdocaoDados(@NotNull
                          Long idpet,
                          @NotNull
                          Long idtutor,
                          @Past
                          LocalDate data) {
}
