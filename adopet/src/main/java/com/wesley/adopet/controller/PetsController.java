package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.Ongs.pets.CadastrarPetDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.DetalharPet;
import com.wesley.adopet.domain.ong.pets.Pet;
import com.wesley.adopet.repository.OngRepository;
import com.wesley.adopet.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("ongs/pets")
public class PetsController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OngRepository ongRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarPet(@RequestBody @Valid CadastrarPetDTO dados, UriComponentsBuilder uriBuilder) {
        var novoPet = new Pet(dados);
        var ong = ongRepository.findById(dados.idOng()).orElse(null); // Garantindo que a ONG exista
        if (ong == null) {
            return ResponseEntity.notFound().build();
        }
        novoPet.setOng(ong); // Associando a ONG ao Pet
        petRepository.save(novoPet); // Salvando o Pet
        ong.getPet().add(novoPet); // Adicionando o Pet à lista de Pets da ONG
        var uri = uriBuilder.path("/ongs/pets/{id}").buildAndExpand(novoPet.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalharPet(novoPet));
    }
}
