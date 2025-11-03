package com.example.gestaoresiduos.service;

import com.example.gestaoresiduos.entity.TipoDeResiduo;
import com.example.gestaoresiduos.exception.ResourceNotFoundException;
import com.example.gestaoresiduos.repository.TipoDeResiduoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoDeResiduoService {
  private final TipoDeResiduoRepository tipoDeResiduoRepository;

  public TipoDeResiduoService(TipoDeResiduoRepository tipoDeResiduoRepository) {
    this.tipoDeResiduoRepository = tipoDeResiduoRepository;
  }

  public String buscarInstrucoesPorId(Long id) {
    TipoDeResiduo residuo = tipoDeResiduoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de resíduo não encontrado com ID: " + id));

    return residuo.getInstrucoesDescarte();
  }
}
