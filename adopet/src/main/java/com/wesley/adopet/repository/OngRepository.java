package com.wesley.adopet.repository;

import com.wesley.adopet.domain.ong.ONG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<ONG, Long> {
    Page<ONG> findAllByAtivoTrue(Pageable pageable);
}
