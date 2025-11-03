package com.example.gestaoresiduos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_TIPO_RESIDUO")
public class TipoDeResiduo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String nome;

  @Lob
  private String instrucoesDescarte;
}
