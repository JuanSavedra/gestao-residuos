# Projeto: Microserviço de Gestão de Resíduos e Reciclagem

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-success.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)

Este é um microserviço RESTful desenvolvido em Spring Boot como parte de uma atividade acadêmica. O projeto implementa um sistema de gerenciamento de resíduos, focando no rastreamento, alertas e segurança, utilizando Docker, Spring Security e um banco de dados Oracle.

---

## 🎯 Tema do Projeto

O projeto foi baseado no seguinte tema:

> **Gestão de resíduos e reciclagem:**
> * Rastreamento da coleta seletiva e descarte correto de resíduos.
> * Alertas automáticos para coleta de materiais recicláveis quando o limite é atingido.
> * Notificações para usuários sobre a destinação correta dos resíduos.

---

## ✨ Funcionalidades Implementadas

* **API RESTful:** 5 endpoints cobrindo as principais regras de negócio.
* **Segurança:** Autenticação e Autorização por endpoint usando Spring Security e HTTP Basic Auth (Roles: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_COLETA`).
* **Validação:** Validação de DTOs (`@Valid`) em nível de Controller (ex: `@NotBlank`, `@Positive`).
* **Tratamento de Exceções:** Handler global (`@RestControllerAdvice`) para erros de negócio (400 - `BusinessException`), recursos não encontrados (404 - `ResourceNotFoundException`) e erros de validação.
* **Banco de Dados:** Conexão e persistência em um banco de dados Oracle, com criação automática de tabelas (via JPA/Hibernate).
* **Containerização (Requisito Mandatório):** Aplicação 100% containerizada com Docker e Docker Compose, incluindo o banco de dados.
* **Inicialização de Dados:** O sistema insere dados de teste (usuários e tipos de resíduos) ao iniciar (`DataInitializer.java`).

---

## 🛠️ Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Spring Data JPA (Hibernate)
* Spring Security
* Spring Web
* Spring Validation
* Maven
* Oracle Database (Imagem Docker `gvenzl/oracle-xe:latest`)
* Docker & Docker Compose
* Lombok

---

## 🚀 Como Executar

Este projeto é 100% containerizado e utiliza **Docker Compose** para orquestração.

### Pré-requisitos
* [Docker](https://www.docker.com/products/docker-desktop/) e Docker Compose instalados.

### Instruções de Execução Local

1.  **Configurar Variáveis de Ambiente:**
    Crie um arquivo `.env` na raiz do projeto baseado no `.env.example`:
    ```bash
    cp .env.example .env
    ```
    *(Edite o arquivo `.env` se desejar alterar senhas ou portas padrão).*

2.  **Iniciar o Ambiente:**
    Execute o comando para construir e subir os containers:
    ```bash
    docker-compose up --build -d
    ```

3.  **Verificar Saúde da Aplicação:**
    O ambiente agora possui **Health Checks**. A aplicação só será considerada "saudável" (UP) quando o Spring Boot estiver totalmente pronto e conectado ao banco. Verifique o status com:
    ```bash
    docker ps
    ```
    Aguarde até que a coluna `STATUS` mostre `(healthy)` para ambos os containers.

4.  A aplicação estará disponível em `http://localhost:8080/api`.

---

## 🚀 DevOps & CI/CD (GitHub Actions)

Este projeto implementa uma pipeline completa de **Integração e Entrega Contínua (CI/CD)** utilizando GitHub Actions.

### Fluxo de Trabalho
1.  **CI (Integração Contínua):** Disparado em `push` para `main` ou `develop`.
    *   Sobe um container Oracle XE temporário no Runner.
    *   Executa o build Maven (`mvn clean verify`).
    *   Roda testes unitários e de integração.
    *   Gera o artefato JAR.

2.  **CD (Entrega Contínua):** Disparado após o sucesso do CI.
    *   **Staging:** Deploys automáticos para o servidor de teste em `push` na branch `develop`.
    *   **Production:** Deploys automáticos para o servidor de produção em `push` na branch `main`.

### Configuração de Secrets no GitHub
Para habilitar o deploy automático, configure os seguintes **Secrets** em seu repositório (**Settings > Secrets and variables > Actions**):

| Secret | Descrição |
| :--- | :--- |
| `SSH_PRIVATE_KEY` | Chave privada SSH para acesso aos servidores. |
| `STAGING_HOST` / `PROD_HOST` | IP ou Hostname dos servidores de Staging/Produção. |
| `STAGING_USER` / `PROD_USER` | Usuário SSH dos servidores. |
| `DB_PASS` | Senha do banco de dados (será injetada no `.env` do servidor). |

---

## 🛡️ Segurança e Boas Práticas DevOps
* **Multi-stage Builds:** Dockerfile otimizado para imagens leves e seguras.
* **Redes Isoladas:** Comunicação entre App e Banco via rede privada `backend-network`.
* **Resource Limits:** Limites de memória (512MB para App, 1.5GB para Oracle) configurados via Docker Compose.
* **Health Checks:** Monitoramento ativo da saúde dos serviços via Spring Actuator.
