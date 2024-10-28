package com.wesley.adopet.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http.csrf(csrf->csrf.disable())
              .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(req -> {
                  // Acesso público
                  req.requestMatchers("/login/user", "/login/ongs", "/ongs/cadastrar", "/tutor/cadastrar",
                          "v3/api-docs/**","swagger-ui.html","swagger-ui/**").permitAll();

                  // Regras específicas para Adoção
                  req.requestMatchers(HttpMethod.POST, "/adocao").hasAnyRole("ONG", "USER");
                  req.requestMatchers(HttpMethod.DELETE, "/adocao/{id}").hasRole("ONG");

                  // Regras para ONGs
                  req.requestMatchers(HttpMethod.GET, "/ongs/pets/{id}").hasAnyRole("ONG", "USER");
                  req.requestMatchers(HttpMethod.GET, "/ongs/abrigos/**").hasAnyRole("ONG", "USER");
                  req.requestMatchers(HttpMethod.GET, "/ongs/pets/listar").hasAnyRole("ONG", "USER");
                  req.requestMatchers(HttpMethod.DELETE, "/ongs/pets/deleter/{id}").hasRole("ONG");
                  req.requestMatchers(HttpMethod.POST, "/ongs/pets").hasRole("ONG");
                  req.requestMatchers(HttpMethod.PUT, "/ongs/pets").hasRole("ONG");
                  req.requestMatchers(HttpMethod.PUT, "/ongs/{id}").hasRole("ONG");
                  req.requestMatchers(HttpMethod.DELETE, "ongs/desativar/{id}").hasRole("ONG");
                  req.requestMatchers(HttpMethod.DELETE, "ongs/deletar/{id}").hasRole("ONG");


                  // Regras para Tutor
                  req.requestMatchers("/tutor/**").hasRole("USER");

                  // Qualquer outra requisição precisa de autenticação
                  req.anyRequest().authenticated();
              })
              .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
              .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
