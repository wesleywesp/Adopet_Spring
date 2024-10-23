package com.wesley.adopet.domain.endereco;


import com.wesley.adopet.controller.DTO.endereco.DadosEnderecoDTO;
import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Endereco {
    private String cidade;
    private String codigoPostal;
    private String pais;
    private String complemento;
    private String rua;

    public Endereco(DadosEnderecoDTO endereco) {
        this.cidade = endereco.cidade();
        this.codigoPostal = endereco.codigoPostal();
        this.pais = endereco.pais();
        this.complemento = endereco.complemento();
        this.rua = endereco.rua();
    }
    public void atualizarInformacoes(DadosEnderecoDTO endereco) {
        if (endereco.cidade() != null) {
            this.cidade = endereco.cidade();
        }
        if (endereco.rua() != null) {
            this.rua = endereco.rua();
        }
        if (endereco.pais() != null) {
            this.pais = endereco.pais();
        }
        if (endereco.codigoPostal() != null) {
            this.codigoPostal = endereco.codigoPostal();
        }
        if (endereco.complemento() != null) {
            this.complemento = endereco.complemento();
        }
    }
}
