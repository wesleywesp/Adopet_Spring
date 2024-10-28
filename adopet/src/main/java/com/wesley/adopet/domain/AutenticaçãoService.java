package com.wesley.adopet.domain;

import com.wesley.adopet.repository.OngRepository;
import com.wesley.adopet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticaçãoService  implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private OngRepository ongRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepository.findByUsernameIgnoreCase(username);
        if (usuario.isPresent()) {
            return usuario.get();
        }

        // Se não encontrou, tenta pelo repositório de ONGs
        var ong = ongRepository.findByEmail(username);
        if (ong.isPresent()) {
            return ong.get();
        }

        // Caso não encontre em nenhum dos repositórios, lança a exceção
        throw new UsernameNotFoundException("Usuário ou ONG não encontrado");
    }
}

