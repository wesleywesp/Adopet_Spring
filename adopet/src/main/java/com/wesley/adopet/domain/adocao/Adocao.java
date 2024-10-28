package com.wesley.adopet.domain.adocao;

import com.wesley.adopet.controller.DTO.Ongs.pets.AdocaoDados;
import com.wesley.adopet.domain.ong.pets.Pet;
import com.wesley.adopet.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "adocoes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Adocao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    private Pet idPet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tutor")
    private Usuario idTutor;

    @JoinColumn(name = "data_adocao")
    private LocalDate data;

    public Adocao(@Valid AdocaoDados dados, Pet pet, Usuario tutor) {
        this.idPet = pet;
        this.idTutor = tutor;
        this.data = dados.data();
    }
}