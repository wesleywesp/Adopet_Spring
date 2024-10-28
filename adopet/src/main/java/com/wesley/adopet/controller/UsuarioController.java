package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.tutor.AtualizarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.CadatrarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.ListarTutores;
import com.wesley.adopet.controller.DTO.tutor.detalharDados;
import com.wesley.adopet.domain.usuario.Usuario;
import com.wesley.adopet.repository.UsuarioRepository;

import com.wesley.adopet.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("tutor")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioservice;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrarTutor(@RequestBody @Valid CadatrarTutorDTO dados, UriComponentsBuilder uriBuilder) {
        var novoUsuario = new Usuario(dados);
        novoUsuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuarioRepository.save(novoUsuario);
        var uri = uriBuilder.path("/tutor/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new detalharDados(novoUsuario));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarTutor(@RequestBody @Valid AtualizarTutorDTO dados) {
        var usuario = usuarioRepository.findById(dados.id());
        if (usuario.isPresent()) {
            usuario.get().atualizar(dados, passwordEncoder);
            return ResponseEntity.ok(new detalharDados(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("desativar/{id}")
    @Transactional
    public ResponseEntity<?> desativarTutor(@PathVariable Long id) {
       var usuario= usuarioRepository.getReferenceById(id);
        return usuarioservice.desativarTutor(id);
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<?> deletarTutor(@PathVariable("id") Long id) {
       return usuarioservice.deletarTutor(id);
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<ListarTutores>> listarTutores(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = usuarioRepository.findAllByAtivoTrue(paginacao).map(ListarTutores::new);
        System.out.println(page);
        return ResponseEntity.ok(page);
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> detalharTutor(@PathVariable Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(new detalharDados(usuario));
    }

}
