package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.tutor.AtualizarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.CadatrarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.ListarTutores;
import com.wesley.adopet.controller.DTO.tutor.detalharDados;
import com.wesley.adopet.domain.usuario.Usuario;
import com.wesley.adopet.repository.UsuarioRepository;

import com.wesley.adopet.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarTutor(@RequestBody @Valid CadatrarTutorDTO dados, UriComponentsBuilder uriBuilder) {
        var novoUsuario = new Usuario(dados);
        usuarioRepository.save(novoUsuario);
        var uri = uriBuilder.path("/tutor/{id}").buildAndExpand(novoUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new detalharDados(novoUsuario));
    }
    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarTutor(@RequestBody @Valid AtualizarTutorDTO dados) {
        var usuario = usuarioRepository.getReferenceById(dados.id());
        usuario.atualizar(dados);
        return ResponseEntity.ok(new detalharDados(usuario));
    }
    @DeleteMapping("desativar/{id}")
    @Transactional
    public ResponseEntity<?> desativarTutor(@PathVariable Long id) {
       var usuario= usuarioRepository.getReferenceById(id);
        return usuarioservice.desativarTutor(id);
    }
    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<?> deletarTutor(@PathVariable("id") Long id) {
       return usuarioservice.deletarTutor(id);
    }
    @GetMapping
    public ResponseEntity<Page<ListarTutores>> listarTutores(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var page = usuarioRepository.findAllByAtivoTrue(paginacao).map(ListarTutores::new);
        return ResponseEntity.ok(page);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> detalharTutor(@PathVariable Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(new detalharDados(usuario));
    }
}
