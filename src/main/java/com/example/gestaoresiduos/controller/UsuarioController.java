package com.example.gestaoresiduos.controller;

import com.example.gestaoresiduos.entity.RegistroDescarte;
import com.example.gestaoresiduos.service.DescarteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final DescarteService descarteService;

  public UsuarioController(DescarteService descarteService) {
    this.descarteService = descarteService;
  }

  @GetMapping("/me/historico")
  public ResponseEntity<List<RegistroDescarte>> getMeuHistorico(Principal principal) {
    String emailDoUsuario = principal.getName();
    List<RegistroDescarte> historico = descarteService.buscarHistoricoUsuario(emailDoUsuario);
    return ResponseEntity.ok(historico);
  }
}
