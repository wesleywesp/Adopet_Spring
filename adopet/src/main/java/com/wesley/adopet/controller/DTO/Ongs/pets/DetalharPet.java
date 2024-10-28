package com.wesley.adopet.controller.DTO.Ongs.pets;

import com.wesley.adopet.domain.ong.pets.Pet;

import java.time.LocalDate;

public record DetalharPet(Long id,
                          String nome,
                          String descricao,
                          String foto,
                          String tipo,
                          LocalDate nascimento,
                          Long idOng) {
    public DetalharPet(Pet pet) {
        this(pet.getId(), pet.getNome(), pet.getDescricao(), pet.getFoto(), pet.getTipo().name(), pet.getNascimento()
                , pet.getOng().getId());
    }
}
