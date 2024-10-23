package com.wesley.adopet.controller.DTO.tutor;

import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.usuario.Usuario;

public record detalharDados(String name,
                            String username,
                            String Email,
                            String telefone,
                            String senha,
                            Endereco endereco) {
    public detalharDados(Usuario usuario) {
        this(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getTelefone(),
                usuario.getSenha(), usuario.getEndereco());
    }
}
