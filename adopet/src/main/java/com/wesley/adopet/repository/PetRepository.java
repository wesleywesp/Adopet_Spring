package com.wesley.adopet.repository;

import com.wesley.adopet.domain.ong.pets.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAllByAdotadoFalse(Pageable pageable);
}
