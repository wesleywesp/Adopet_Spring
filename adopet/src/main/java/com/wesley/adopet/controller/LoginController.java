package com.wesley.adopet.controller;

import com.wesley.adopet.controller.DTO.login.DadosLogin;
import com.wesley.adopet.controller.DTO.login.DadosLoginOng;
import com.wesley.adopet.controller.DTO.login.DadosTokenJWT;
import com.wesley.adopet.domain.ong.ONG;
import com.wesley.adopet.domain.usuario.Usuario;
import com.wesley.adopet.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/user")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dados){
        var tokenAuthemtication = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        var authentication =manager.authenticate(tokenAuthemtication);

        var tokenJWT = tokenService.gerarToken((Usuario)authentication.getPrincipal(), null);

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
    @PostMapping("/ongs")
    public ResponseEntity efetuarLoginOng(@RequestBody @Valid DadosLoginOng dados){
        var tokenAuthemtication = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());
        var authentication =manager.authenticate(tokenAuthemtication);

        var tokenJWT = tokenService.gerarToken(null, (ONG)authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
