package com.example.gestaoresiduos.service;

import com.example.gestaoresiduos.dto.DescarteRequestDTO;
import com.example.gestaoresiduos.entity.PontoDeColeta;
import com.example.gestaoresiduos.entity.RegistroDescarte;
import com.example.gestaoresiduos.entity.TipoDeResiduo;
import com.example.gestaoresiduos.entity.Usuario;
import com.example.gestaoresiduos.exception.BusinessException;
import com.example.gestaoresiduos.exception.ResourceNotFoundException;
import com.example.gestaoresiduos.repository.PontoDeColetaRepository;
import com.example.gestaoresiduos.repository.RegistroDescarteRepository;
import com.example.gestaoresiduos.repository.TipoDeResiduoRepository;
import com.example.gestaoresiduos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DescarteService {
  private final RegistroDescarteRepository registroDescarteRepository;
  private final UsuarioRepository usuarioRepository;
  private final PontoDeColetaRepository pontoDeColetaRepository;
  private final TipoDeResiduoRepository tipoDeResiduoRepository;

  public DescarteService(RegistroDescarteRepository registroDescarteRepository,
                         UsuarioRepository usuarioRepository,
                         PontoDeColetaRepository pontoDeColetaRepository,
                         TipoDeResiduoRepository tipoDeResiduoRepository) {
    this.registroDescarteRepository = registroDescarteRepository;
    this.usuarioRepository = usuarioRepository;
    this.pontoDeColetaRepository = pontoDeColetaRepository;
    this.tipoDeResiduoRepository = tipoDeResiduoRepository;
  }

  @Transactional
  public RegistroDescarte registrarDescarte(DescarteRequestDTO dto, String emailUsuario) {
    Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

    PontoDeColeta ponto = pontoDeColetaRepository.findById(dto.getPontoDeColetaId())
            .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado."));

    TipoDeResiduo tipo = tipoDeResiduoRepository.findById(dto.getTipoDeResiduoId())
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de resíduo não encontrado."));

    double novoNivel = ponto.getNivelAtualKg() + dto.getQuantidadeKg();
    if (novoNivel > ponto.getCapacidadeMaximaKg()) {
      throw new BusinessException("Capacidade do ponto de coleta excedida. " +
              "Espaço restante: " + (ponto.getCapacidadeMaximaKg() - ponto.getNivelAtualKg()) + "kg");
    }

    ponto.setNivelAtualKg(novoNivel);
    pontoDeColetaRepository.save(ponto);

    RegistroDescarte novoRegistro = new RegistroDescarte();
    novoRegistro.setUsuario(usuario);
    novoRegistro.setPontoDeColeta(ponto);
    novoRegistro.setTipoDeResiduo(tipo);
    novoRegistro.setQuantidadeKg(dto.getQuantidadeKg());
    novoRegistro.setDataHora(LocalDateTime.now());

    return registroDescarteRepository.save(novoRegistro);
  }

  public List<RegistroDescarte> buscarHistoricoUsuario(String emailUsuario) {
    Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

    return registroDescarteRepository.findByUsuarioIdOrderByDataHoraDesc(usuario.getId());
  }
}
