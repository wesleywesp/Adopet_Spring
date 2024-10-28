package com.wesley.adopet.domain.ong.pets;


import com.wesley.adopet.controller.DTO.Ongs.pets.AtualizarPetsDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.CadastrarPetDTO;
import com.wesley.adopet.domain.ong.ONG;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;


@Entity(name="Pet")
@Table(name= "pets")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate nascimento;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Animal tipo;

    private String foto;
    private boolean adotado = false;


    @ManyToOne
    private ONG ong;

    public Pet(@Valid CadastrarPetDTO dados) {
        this.nome = dados.nome();
        this.nascimento = dados.nascimento();
        this.descricao = dados.descricao();
        this.tipo = Animal.valueOf(dados.tipo().toUpperCase());
        this.foto = dados.foto();
        this.adotado = false;
    }

    public void atualizar(@Valid AtualizarPetsDTO dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.nascimento() != null) {
            this.nascimento = dados.nascimento();
        }
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if(dados.tipo() != null) {
            this.tipo = Animal.valueOf(dados.tipo().toUpperCase());
        }
        if (dados.foto() != null) {
            this.foto = dados.foto();
        }
    }

    public void adotado() {
        this.adotado = true;
    }
}
