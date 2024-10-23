package com.wesley.adopet.domain.ong;

import com.wesley.adopet.controller.DTO.Ongs.CadastrarOngDTO;
import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.ong.pets.Pet;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ongs")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
@Getter
@Setter
public class ONG {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    @Embedded
    private Endereco endereco;
    private String senha;
    private boolean ativo = true;

    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pet = new ArrayList<>();

    public ONG(@Valid CadastrarOngDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.senha = dados.senha();
        this.endereco = new Endereco(dados.endereco());
    }

    public ONG(@NotNull Long aLong) {
        this.pet = new ArrayList<>();
    }

    public boolean isAtiva() {return
        this.ativo;
    }
    public void  setPet(List<Pet> pet) {
        this.pet = pet;
    }
}
