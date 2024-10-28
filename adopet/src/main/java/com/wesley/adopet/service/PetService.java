package com.wesley.adopet.service;

import com.wesley.adopet.controller.DTO.Ongs.pets.CadastrarPetDTO;
import com.wesley.adopet.controller.DTO.Ongs.pets.DetalharPet;
import com.wesley.adopet.domain.ong.pets.Pet;
import com.wesley.adopet.repository.OngRepository;
import com.wesley.adopet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PetService {
    @Autowired
    private  PetRepository petRepository;
    @Autowired
    private OngRepository ongRepository;

    public ResponseEntity<?> cadastrarPet(CadastrarPetDTO dados, UriComponentsBuilder uriBuilder) {

        var novoPet = new Pet(dados);
        var ong = ongRepository.findById(dados.idOng()).orElse(null); // Garantindo que a ONG exista
        if (ong == null) {
            return ResponseEntity.notFound().build();
        }
        novoPet.setOng(ong); // Associando a ONG ao Pet
        petRepository.save(novoPet); // Salvando o Pet
        ong.getPet().add(novoPet); // Adicionando o Pet à lista de Pets da ONG
        var uri = uriBuilder.path("/ongs/pets/{id}").buildAndExpand(novoPet.getId()).toUri();
        System.out.println("Pet cadastrado com sucesso!");
        return ResponseEntity.created(uri).body(new DetalharPet(novoPet));
    }

    public ResponseEntity<?> deletarPet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return ResponseEntity.ok("Pet deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> adotarPet(Long id) {
        if (petRepository.existsById(id)) {
            var pet = petRepository.getReferenceById(id);
            pet.adotado();
            return ResponseEntity.ok("Pet adotado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
