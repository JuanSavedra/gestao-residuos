# language: pt

Funcionalidade: Gestão de Pontos de Coleta
  Como um administrador ou membro da equipe de coleta
  Eu quero gerenciar os pontos de coleta
  Para garantir que eles não transbordem

  Contexto:
    Dado que a API de pontos de coleta está disponível

  Cenário: Listar pontos com limite atingido como Admin
    Dado que estou autenticado com o usuário "admin@gestao.com" e senha "admin123"
    Quando eu solicito a lista de alertas
    Então o status code deve ser 200
    E a resposta deve ser uma lista

  Cenário: Esvaziar um ponto de coleta como Coleta
    Dado que estou autenticado com o usuário "coleta@gestao.com" e senha "coleta123"
    Quando eu solicito para esvaziar o ponto com ID 1
    Então o status code deve ser 200
    E o nivelAtualKg do ponto deve ser "0.0"

  Cenário: Tentar esvaziar ponto sem permissão (Usuário Comum)
    Dado que estou autenticado com o usuário "user@gestao.com" e senha "user123"
    Quando eu solicito para esvaziar o ponto com ID 1
    Então o status code deve ser 403
