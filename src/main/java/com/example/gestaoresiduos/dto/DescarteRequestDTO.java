package com.example.gestaoresiduos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DescarteRequestDTO {
  @NotNull(message = "ID do Ponto de Coleta é obrigatório")
  private Long pontoDeColetaId;

  @NotNull(message = "ID do Tipo de Resíduo é obrigatório")
  private Long tipoDeResiduoId;

  @NotNull(message = "Quantidade é obrigatória")
  @Positive(message = "A quantidade deve ser positiva")
  private Double quantidadeKg;
}
