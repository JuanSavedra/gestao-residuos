package com.example.gestaoresiduos.controller;

import com.example.gestaoresiduos.entity.PontoDeColeta;
import com.example.gestaoresiduos.service.PontoDeColetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos-coleta")
public class PontoDeColetaController {

  private final PontoDeColetaService pontoDeColetaService;

  public PontoDeColetaController(PontoDeColetaService pontoDeColetaService) {
    this.pontoDeColetaService = pontoDeColetaService;
  }

  // Endpoint 3: GET /api/pontos-coleta/alertas
  // (Segurança: ROLE_ADMIN ou ROLE_COLETA, conforme SecurityConfig)
  @GetMapping("/alertas")
  public ResponseEntity<List<PontoDeColeta>> getPontosComLimiteAtingido() {
    List<PontoDeColeta> lista = pontoDeColetaService.listarPontosComLimiteAtingido();
    return ResponseEntity.ok(lista);
  }

  // Endpoint 5: PUT /api/pontos-coleta/{id}/esvaziar
  // (Segurança: ROLE_ADMIN ou ROLE_COLETA, conforme SecurityConfig)
  @PutMapping("/{id}/esvaziar")
  public ResponseEntity<PontoDeColeta> esvaziarPonto(@PathVariable Long id) {
    PontoDeColeta ponto = pontoDeColetaService.esvaziarPontoDeColeta(id);
    return ResponseEntity.ok(ponto);
  }
}
