package com.wesley.adopet.controller.DTO.tutor;

import com.wesley.adopet.domain.usuario.Usuario;

public record ListarTutores(Long id,
                            String nome,
                            String username){
    public ListarTutores(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getUsername());
    }
}
