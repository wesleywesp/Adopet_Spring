package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.Ongs.CadastrarOngDTO;
import com.wesley.adopet.controller.DTO.Ongs.DetalharOng;
import com.wesley.adopet.domain.ong.ONG;
import com.wesley.adopet.repository.OngRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("ongs")
public class OngController {
    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    private ResponseEntity<?> cadastrarOng(@RequestBody @Valid CadastrarOngDTO dados, UriComponentsBuilder uriBuilder) {
        var novaOng = new ONG(dados);
        ongRepository.save(novaOng);
        var uri = uriBuilder.path("/ongs/{id}").buildAndExpand(novaOng.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharOng(novaOng));
    }
}
