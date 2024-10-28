package com.wesley.adopet.controller.DTO.Ongs;

import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.ong.ONG;

public record DetalharOngsDTO(Long id, String nome, String email, String telefone, Endereco endereco) {

    public DetalharOngsDTO(ONG ong) {
        this(ong.getId(), ong.getNome(), ong.getEmail(), ong.getTelefone(), ong.getEndereco());
    }
}
