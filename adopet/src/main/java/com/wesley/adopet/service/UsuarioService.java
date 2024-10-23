package com.wesley.adopet.service;

import com.wesley.adopet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public ResponseEntity<?> deletarTutor(Long id) {
        if (usuarioRepository.existsById(id)) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Tutor deletado com sucesso");
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
         }
    }

    public ResponseEntity<?> desativarTutor(Long id) {
        if (usuarioRepository.existsById(id)) {
            var tutor = usuarioRepository.getReferenceById(id);
            tutor.desativar();
            return ResponseEntity.ok("Tutor desativado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutor não encontrado");
        }
    }
}