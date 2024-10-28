package com.wesley.adopet.infra.security;



import com.wesley.adopet.repository.OngRepository;
import com.wesley.adopet.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private OngRepository ongRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = recuperarToken(request);

        if(tokenJwt != null ){
            var subject = tokenService.getSubject(tokenJwt);

            var usuarios = repository.findByUsername(subject);
            if(usuarios.isPresent()){
                var usuario = usuarios.get();
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                var ongs = ongRepository.findByEmail(subject);
                if(ongs.isPresent()){
                    var ong = ongs.get();
                    var authentication = new UsernamePasswordAuthenticationToken(ong, null, ong.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    throw new UsernameNotFoundException("Usuário ou ONG não encontrado");
                }
            }

        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if(authorization != null){
            return authorization.substring(7);
        }
        return null;
    }
}
