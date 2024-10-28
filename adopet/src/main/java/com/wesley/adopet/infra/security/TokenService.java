package com.wesley.adopet.infra.security;

// https://jwt.io/libraries?language=Java
// https://github.com/auth0/java-jwt?tab=readme-ov-file

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wesley.adopet.domain.ong.ONG;
import com.wesley.adopet.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.secutiry.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario, ONG ong) {
        if (usuario != null) {
            try {
                var algorithm = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("AdotePet_API")// nome daaplicação dona do token
                        .withSubject(usuario.getUsername())// dados do cliente
                        .withClaim("email", usuario.getEmail())
                        .withClaim("Id", usuario.getId())//dados do cliente
                        .withExpiresAt(Date.from(dataExpiracao()))// data de expiração
                        .sign(algorithm); // assinatura metodo de criptografia
            } catch (JWTCreationException exception) {
                //Invalid Signing configuration / Couldn't convert Claims.
                throw new RuntimeException("Erro ao gerar token Tutor ", exception);
            }
        }
        if (ong != null) {
        }
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("AdotePet_API")// nome daaplicação dona do token
                    .withSubject(ong.getEmail())// dados do cliente
                    .withClaim("email", ong.getEmail())
                    .withClaim("Id", ong.getId())//dados do cliente
                    .withExpiresAt(Date.from(dataExpiracao()))// data de expiração
                    .sign(algorithm); // assinatura metodo de criptografia
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Erro ao gerar token ONG ", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
           var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("AdotePet_API") // nome da aplicação dona do token
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido", exception);
        }
    }
    private Instant dataExpiracao() {
        return Instant.now().plusSeconds(1800);
    }
}


