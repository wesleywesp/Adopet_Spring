package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.Ongs.pets.AtualizarPetsDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.CadastrarPetDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.DetalharPet;
import com.wesley.adopet.controller.DTO.Ongs.pets.DetalharPetDTO;
import com.wesley.adopet.controller.DTO.tutor.AtualizarTutorDTO;
import com.wesley.adopet.controller.DTO.tutor.detalharDados;
import com.wesley.adopet.domain.ong.pets.Pet;
import com.wesley.adopet.repository.OngRepository;
import com.wesley.adopet.repository.PetRepository;
import com.wesley.adopet.service.PetService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("ongs/pets")
public class PetsController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;

    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarPet(@RequestBody @Valid CadastrarPetDTO dados, UriComponentsBuilder uriBuilder) {
       return petService.cadastrarPet(dados, uriBuilder);
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('ONG', 'USER')")
    @GetMapping("/listar")
    public ResponseEntity<Page<DetalharPet>> listarPets(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao) {
        var pets = petRepository.findAllByAdotadoFalse(paginacao);
        if (pets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pets.map(DetalharPet::new));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('ONG', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DetalharPetDTO> detalharPet(@PathVariable Long id) {
        var pet = petRepository.findById(id).orElse(null);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalharPetDTO(pet));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @PutMapping
    @Transactional
    public  ResponseEntity<?> atualizarPet(@RequestBody @Valid AtualizarPetsDTO dados) {
        var pet = petRepository.getReferenceById(dados.id());
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        pet.atualizar(dados);
        return ResponseEntity.ok(new DetalharPet(pet));
    }
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ONG')")
    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<?> deletarPet(@PathVariable Long id) {
        return petService.deletarPet(id);
    }

}
