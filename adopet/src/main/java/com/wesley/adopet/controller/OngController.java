package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.Ongs.AtualizarOngDTO;
import com.wesley.adopet.controller.DTO.Ongs.CadastrarOngDTO;
import com.wesley.adopet.controller.DTO.Ongs.DetalharOng;
import com.wesley.adopet.controller.DTO.Ongs.DetalharOngsDTO;
import com.wesley.adopet.domain.ong.ONG;
import com.wesley.adopet.repository.OngRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("ongs")
public class OngController {
    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrarOng(@RequestBody @Valid CadastrarOngDTO dados, UriComponentsBuilder uriBuilder) {
        var novaOng = new ONG(dados);
        novaOng.setSenha(passwordEncoder.encode(dados.senha()));
        ongRepository.save(novaOng);
        var uri = uriBuilder.path("/ongs/{id}").buildAndExpand(novaOng.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharOng(novaOng));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('ONG', 'USER')")
    @GetMapping("/abrigos")
    public ResponseEntity<Page<DetalharOngsDTO>> listarOngs(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = ongRepository.findAllByAtivoTrue(paginacao).map(DetalharOngsDTO::new);
        if(page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @GetMapping("/abrigos/{id}")
    public ResponseEntity<DetalharOng> detalharOng(@PathVariable Long id) {
        var ong = ongRepository.findById(id).orElse(null);
        if (ong == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalharOng(ong));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @PutMapping("/abrigos")
    @Transactional
    public ResponseEntity<?> atualizarOng(@RequestBody @Valid AtualizarOngDTO dados) {
        var ong = ongRepository.findById(dados.id());
        if (ong.isPresent()) {
            ong.get().atualizar(dados, passwordEncoder);
            return ResponseEntity.ok(new DetalharOng(ong.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @DeleteMapping("/desativar/{id}")
    @Transactional
    public ResponseEntity<?> desativarOng(@PathVariable Long id) {
        var ong = ongRepository.getReferenceById(id);
        if (ong == null) {
            return ResponseEntity.notFound().build();
        }
        ong.desativar();
        return ResponseEntity.ok(new DetalharOng(ong));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity<?> deletarOng(@PathVariable Long id) {
        var ong = ongRepository.getReferenceById(id);
        if (ong == null) {
            return ResponseEntity.notFound().build();
        }
        ongRepository.delete(ong);
        return ResponseEntity.ok().build();
    }
}
