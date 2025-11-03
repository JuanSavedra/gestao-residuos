package com.example.gestaoresiduos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_PONTO_COLETA")
public class PontoDeColeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String nome; // Ex: "Contêiner Bloco A", "Lixeira inteligente"

  private String localizacao; // Pode ser "Lat/Long" ou um endereço

  @PositiveOrZero
  @Column(nullable = false)
  private Double capacidadeMaximaKg;

  @PositiveOrZero
  @Column(nullable = false)
  private Double nivelAtualKg = 0.0; // Inicia com 0
}
