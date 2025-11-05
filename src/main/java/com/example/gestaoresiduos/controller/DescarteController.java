package com.example.gestaoresiduos.controller;

import com.example.gestaoresiduos.dto.DescarteRequestDTO;
import com.example.gestaoresiduos.entity.RegistroDescarte;
import com.example.gestaoresiduos.service.DescarteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/descartes")
public class DescarteController {

  private final DescarteService descarteService;

  public DescarteController(DescarteService descarteService) {
    this.descarteService = descarteService;
  }

  @PostMapping
  public ResponseEntity<RegistroDescarte> registrarDescarte(
          @Valid @RequestBody DescarteRequestDTO dto,
          Principal principal
  ) {
    String emailDoUsuario = principal.getName();

    RegistroDescarte registro = descarteService.registrarDescarte(dto, emailDoUsuario);
    return new ResponseEntity<>(registro, HttpStatus.CREATED);
  }
}
