package com.wesley.adopet.domain.usuario;

import com.wesley.adopet.controller.DTO.tutor.AtualizarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.CadatrarTutorDTO;
import com.wesley.adopet.domain.endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String username;
    private String email;
    private String telefone;
    private String senha;
    @Embedded
    private Endereco endereco;
    private boolean ativo = true;




    public Usuario(CadatrarTutorDTO dados) {

        this.ativo = true;
        this.nome = dados.nome();
        this.username = dados.username();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.senha = dados.senha();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizar(@Valid AtualizarTutorDTO dados, PasswordEncoder passwordEncoder) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.username() != null) {
            this.username = dados.username();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }if (dados.senha() != null) {
            this.senha = passwordEncoder.encode(dados.senha());
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void desativar() {
        this.ativo=false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return username;
    }
}
