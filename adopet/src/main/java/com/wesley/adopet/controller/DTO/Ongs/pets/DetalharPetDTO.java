package com.wesley.adopet.controller.DTO.Ongs.pets;

import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.ong.ONG;
import com.wesley.adopet.domain.ong.pets.Pet;

import java.time.LocalDate;

public record DetalharPetDTO(Long id,
                             String nome,
                             String descricao,
                             String foto,
                             String tipo,
                             LocalDate nascimento,
                             Long idOng,
                             boolean adotado) {
    public DetalharPetDTO(Pet pet) {
        this(pet.getId(), pet.getNome(), pet.getDescricao(), pet.getFoto(), pet.getTipo().name(), pet.getNascimento()
                , pet.getOng().getId(), pet.isAdotado());
    }
}
