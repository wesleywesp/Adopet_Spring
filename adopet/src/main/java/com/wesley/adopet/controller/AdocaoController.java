package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.Adocao.DetalharAdocaoDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.AdocaoDados;
import com.wesley.adopet.domain.adocao.Adocao;
import com.wesley.adopet.repository.AdocaoRepository;
import com.wesley.adopet.repository.PetRepository;
import com.wesley.adopet.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/adocao")
public class AdocaoController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AdocaoRepository adocaoRepository;

    @PreAuthorize("hasRole('ONG', 'USER')")
    @PostMapping
    @Transactional
    public ResponseEntity<?> adotarPet(@RequestBody @Valid AdocaoDados dados) {
        var pet = petRepository.findById(dados.idpet()).orElse(null);
        var tutor = usuarioRepository.findById(dados.idtutor()).orElse(null);
        if(pet == null || tutor == null) {
            return ResponseEntity.notFound().build();
        }
        var adocao = new Adocao(dados, pet, tutor);
        pet.setAdotado(true);
        adocaoRepository.save(adocao);
        return ResponseEntity.ok(new DetalharAdocaoDTO(adocao));
    }
    @PreAuthorize("hasRole('ONG')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarAdocao(@PathVariable Long id) {
        var adocao= adocaoRepository.findById(id).orElse(null);
        if(adocao == null) {
            return ResponseEntity.notFound().build();
        }
        var pet = petRepository.findById(adocao.getIdPet().getId()).orElse(null);
        if(pet != null) {
            pet.setAdotado(false);
        }
        adocaoRepository.delete(adocao);
        return ResponseEntity.ok().build();
    }

}
