package com.wesley.adopet.controller.DTO.Adocao;

import com.wesley.adopet.domain.adocao.Adocao;

import java.time.LocalDate;

public record  DetalharAdocaoDTO(String Pet, String Tutor, LocalDate data) {
    public DetalharAdocaoDTO(Adocao adocao) {
        this(adocao.getIdPet().getNome(), adocao.getIdTutor().getUsername(), adocao.getData());
    }
}
