# language: pt

Funcionalidade: Busca de Instruções de Resíduos
  Como um cidadão consciente
  Eu quero saber como descartar corretamente diferentes tipos de resíduos
  Para evitar a contaminação do meio ambiente

  Esquema do Cenário: Buscar instruções para diferentes tipos de resíduos
    Dado que a API de resíduos está disponível
    Quando eu solicito as instruções para o resíduo com ID <id>
    Então o status code deve ser 200
    E o corpo da resposta deve conter "<instrucao>"

    Exemplos:
      | id | instrucao                       |
      | 1  | Lavar e secar embalagens        |
      | 2  | Cuidado ao descartar vidros     |
      | 3  | Restos de alimentos             |

  Cenário: Buscar instruções para um tipo de resíduo inexistente
    Dado que a API de resíduos está disponível
    Quando eu solicito as instruções para o resíduo com ID 999
    Então o status code deve ser 404
