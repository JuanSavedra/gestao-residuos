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
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Deixa o DB gerenciar o ID
  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 3, max = 100)
  @Column(nullable = false, length = 100)
  private String nome;

  @Email(message = "Formato de e-mail inválido")
  @NotBlank(message = "E-mail é obrigatório")
  @Column(nullable = false, unique = true) // E-mail deve ser único
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6) // Em um projeto real, NUNCA armazene a senha em texto plano
  @Column(nullable = false)
  private String senha; // Lembre-se: Isso será criptografado pelo Spring Security

  @Enumerated(EnumType.STRING) // Grava o nome do Enum ("ROLE_USER") no DB
  @Column(nullable = false)
  private Role role;

  // Relacionamento: Um usuário pode ter vários registros de descarte
  @OneToMany(mappedBy = "usuario")
  private List<RegistroDescarte> registros;
}
