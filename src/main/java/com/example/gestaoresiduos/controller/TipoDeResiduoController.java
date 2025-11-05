package com.example.gestaoresiduos.controller;

import com.example.gestaoresiduos.service.TipoDeResiduoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residuos")
public class TipoDeResiduoController {

  private final TipoDeResiduoService tipoDeResiduoService;

  public TipoDeResiduoController(TipoDeResiduoService tipoDeResiduoService) {
    this.tipoDeResiduoService = tipoDeResiduoService;
  }

  @GetMapping("/{id}/instrucoes")
  public ResponseEntity<String> getInstrucoesPorId(@PathVariable Long id) {
    String instrucoes = tipoDeResiduoService.buscarInstrucoesPorId(id);
    return ResponseEntity.ok(instrucoes);
  }
}
