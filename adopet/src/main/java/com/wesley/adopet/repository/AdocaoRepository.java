package com.wesley.adopet.repository;

import com.wesley.adopet.domain.adocao.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
}