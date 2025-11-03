package com.example.gestaoresiduos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_REGISTRO_DESCARTE")
public class RegistroDescarte {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @PastOrPresent // O descarte não pode ser no futuro
  @Column(nullable = false)
  private LocalDateTime dataHora;

  @NotNull
  @Positive
  @Column(nullable = false)
  private Double quantidadeKg;

  // Relacionamento: Muitos registros para UM Usuário
  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  // Relacionamento: Muitos registros para UM Ponto de Coleta
  @ManyToOne
  @JoinColumn(name = "ponto_coleta_id", nullable = false)
  private PontoDeColeta pontoDeColeta;

  // Relacionamento: Muitos registros para UM Tipo de Resíduo
  @ManyToOne
  @JoinColumn(name = "tipo_residuo_id", nullable = false)
  private TipoDeResiduo tipoDeResiduo;
}
