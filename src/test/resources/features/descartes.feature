# language: pt

Funcionalidade: Registro de Descartes
  Como um usuário do sistema
  Eu quero registrar meus descartes de resíduos
  Para acompanhar meu histórico e contribuir para a gestão

  Contexto:
    Dado que a API de descartes está disponível

  Cenário: Registrar descarte com sucesso
    Dado que estou autenticado com o usuário "user@gestao.com" e senha "user123"
    Quando eu envio uma requisição POST para "/api/descartes" com:
      | pontoDeColetaId | 1   |
      | tipoDeResiduoId | 1   |
      | quantidadeKg    | 5.5 |
    Então o status code deve ser 201
    E o corpo da resposta deve validar o contrato "schemas/descarte_schema.json"
    E a quantidadeKg no corpo da resposta deve ser "5.5"
    E o nível do ponto de coleta 1 deve ter aumentado em "5.5" kg

  Cenário: Tentar registrar descarte sem autenticação
    Quando eu envio uma requisição POST para "/api/descartes" sem autenticação
    Então o status code deve ser 401

  Cenário: Tentar registrar descarte com dados inválidos
    Dado que estou autenticado com o usuário "user@gestao.com" e senha "user123"
    Quando eu envio uma requisição POST para "/api/descartes" com:
      | pontoDeColetaId | 1    |
      | tipoDeResiduoId | 1    |
      | quantidadeKg    | -1.0 |
    Então o status code deve ser 400
