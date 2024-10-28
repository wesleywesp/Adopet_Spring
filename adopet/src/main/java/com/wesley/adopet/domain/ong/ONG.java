package com.wesley.adopet.domain.ong;

import com.wesley.adopet.controller.DTO.Ongs.AtualizarOngDTO;
import com.wesley.adopet.controller.DTO.Ongs.CadastrarOngDTO;
import com.wesley.adopet.domain.endereco.Endereco;
import com.wesley.adopet.domain.ong.pets.Pet;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "ongs")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
@Getter
@Setter
public class ONG implements UserDetails {
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

    public void atualizar(@Valid AtualizarOngDTO dados, PasswordEncoder passwordEncoder) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.email() != null) {
            this.email = dados.email();
        }if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }if (dados.endereco() != null) {
            this.endereco = new Endereco(dados.endereco());
        }
        if (dados.senha() != null) {
            this.senha = passwordEncoder.encode(dados.senha());
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ONG"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
