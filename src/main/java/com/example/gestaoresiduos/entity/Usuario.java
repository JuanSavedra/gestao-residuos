package com.example.gestaoresiduos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "TB_USUARIO")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 3, max = 100)
  @Column(nullable = false, length = 100)
  private String nome;

  @Email(message = "Formato de e-mail inválido")
  @NotBlank(message = "E-mail é obrigatório")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6)
  @Column(nullable = false)
  private String senha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToMany(mappedBy = "usuario")
  private List<RegistroDescarte> registros;
}
