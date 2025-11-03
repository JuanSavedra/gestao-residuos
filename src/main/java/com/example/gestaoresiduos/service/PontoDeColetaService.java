package com.example.gestaoresiduos.service;

import com.example.gestaoresiduos.entity.PontoDeColeta;
import com.example.gestaoresiduos.exception.ResourceNotFoundException;
import com.example.gestaoresiduos.repository.PontoDeColetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PontoDeColetaService {
  private final PontoDeColetaRepository pontoDeColetaRepository;

  public PontoDeColetaService(PontoDeColetaRepository pontoDeColetaRepository) {
    this.pontoDeColetaRepository = pontoDeColetaRepository;
  }

  public List<PontoDeColeta> listarPontosComLimiteAtingido() {
    return pontoDeColetaRepository.findPontosDeColetaComLimiteAtingido();
  }

  @Transactional
  public PontoDeColeta esvaziarPontoDeColeta(Long id) {
    PontoDeColeta ponto = pontoDeColetaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado com ID: " + id));

    ponto.setNivelAtualKg(0.0);

    return pontoDeColetaRepository.save(ponto);
  }
}
