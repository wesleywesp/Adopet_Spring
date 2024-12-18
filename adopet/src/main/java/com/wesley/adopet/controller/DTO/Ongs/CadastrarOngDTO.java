package com.wesley.adopet.controller.DTO.Ongs;

import com.wesley.adopet.controller.DTO.endereco.DadosEnderecoDTO;
import com.wesley.adopet.domain.endereco.Endereco;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastrarOngDTO(@NotBlank(message = "Nome é obrigatório")
                              String nome,

                              @NotBlank(message = "Email é obrigatório")
                              @Email(message = "Formato do email é inválido")
                              String email,

                              @NotBlank
                              String senha,

                              @NotBlank(message = "Telefone é obrigatório")
                              @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\(?(\\d{1,4})\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
                                      message = "Número de telefone inválido")
                              String telefone,

                              @NotNull(message = "Dados do endereço são obrigatórios")
                              @Valid
                              DadosEnderecoDTO endereco) {
}
