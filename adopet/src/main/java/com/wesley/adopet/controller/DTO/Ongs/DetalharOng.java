package com.wesley.adopet.controller.DTO.Ongs;


import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.ong.ONG;


public record DetalharOng(String nome,
                          String email,
                          String senha,
                          String telefone,
                          Endereco endereco) {
    public DetalharOng(ONG novaOng) {
        this(novaOng.getNome(), novaOng.getEmail(), novaOng.getSenha(), novaOng.getTelefone(), novaOng.getEndereco());
    }
}
