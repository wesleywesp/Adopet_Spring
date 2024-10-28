package com.wesley.adopet.controller.DTO.tutor;

import com.wesley.adopet.controller.DTO.endereco.DadosEnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadatrarTutorDTO(@NotBlank(message = "Nome é obrigatório")
                               String nome,

                               @NotBlank(message = "username é obrigatório")
                               String username,

                               @NotBlank(message = "Email é obrigatório")
                               @Email(message = "Formato do email é inválido")
                               String email,

                               @NotBlank(message = "CRM é obrigatório")
                               @Pattern(regexp = "^\\+?\\d{1,3}?[-.\\s]?\\(?(\\d{1,4})\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$",
                                       message = "Número de telefone inválido")
                               String telefone,

                               @NotBlank(message = "Senha é obrigatória")
                               String senha,
                               @NotNull(message ="Dados do endereço são obrigatórios")
                               @Valid
                               DadosEnderecoDTO endereco) {

}