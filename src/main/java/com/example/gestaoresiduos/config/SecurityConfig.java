package com.example.gestaoresiduos.config;

import com.example.gestaoresiduos.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
    return email -> usuarioRepository.findByEmail(email)
            .map(usuario -> new User(
                    usuario.getEmail(),
                    usuario.getSenha(),
                    Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole().name()))
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(authz -> authz
                    .requestMatchers(HttpMethod.GET, "/api/residuos/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/pontos-coleta/alertas").hasAnyRole("ADMIN", "COLETA")
                    .requestMatchers(HttpMethod.PUT, "/api/pontos-coleta/**").hasAnyRole("ADMIN", "COLETA")
                    .requestMatchers(HttpMethod.POST, "/api/descartes").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/api/usuarios/me/historico").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/api/auth/registrar").permitAll()
                    .anyRequest().authenticated()
            )

            .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}
