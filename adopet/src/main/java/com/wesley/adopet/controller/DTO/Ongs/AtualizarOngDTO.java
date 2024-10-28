package com.wesley.adopet.controller.DTO.Ongs;

import com.wesley.adopet.controller.DTO.endereco.DadosEnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarOngDTO(@NotNull
                              Long id,
                              String nome,
                              @Email(message = "Formato do email é inválido")
                              String email,
                              String senha,
                              @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\(?(\\d{1,4})\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
                                      message = "Número de telefone inválido")
                              String telefone,

                              DadosEnderecoDTO endereco) {
}
