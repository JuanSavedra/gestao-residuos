# language: pt

Funcionalidade: Busca de Instruções de Resíduos
  Como um cidadão consciente
  Eu quero saber como descartar corretamente diferentes tipos de resíduos
  Para evitar a contaminação do meio ambiente

  Cenário: Buscar instruções para um tipo de resíduo existente
    Dado que a API de resíduos está disponível
    Quando eu solicito as instruções para o resíduo com ID 1
    Então o status code deve ser 200
    E o corpo da resposta deve conter "Lavar e secar embalagens"

  Cenário: Buscar instruções para um tipo de resíduo inexistente
    Dado que a API de resíduos está disponível
    Quando eu solicito as instruções para o resíduo com ID 999
    Então o status code deve ser 404
