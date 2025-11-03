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

## 🚀 Como Executar (Requisito 6)

Este projeto é 100% containerizado. A forma correta de executá-lo é utilizando o Docker Compose.

**Pré-requisitos:**
* [Docker](https://www.docker.com/products/docker-desktop/) instalado e em execução.
* Docker Compose (geralmente já vem com o Docker Desktop).

### Instruções de Execução

1.  Clone este repositório para sua máquina local.
2.  Abra um terminal na pasta raiz do projeto (onde estão o `pom.xml` e o `docker-compose.yml`).
3.  Execute o seguinte comando para construir a imagem da aplicação e iniciar os containers (aplicação + banco de dados):

    ```bash
    docker-compose up --build
    ```

4.  **Aguarde.** O primeiro "up" pode demorar alguns minutos. O container do Oracle (`oracle-db`) precisa se autoconfigurar e o Maven (`build`) precisa baixar as dependências e construir o projeto.

5.  A aplicação estará disponível em `http://localhost:8080/api`.

### ⚠️ Aviso Importante: Startup do Oracle

O banco de dados Oracle XE pode demorar de **1 a 3 minutos** para estar totalmente pronto para aceitar conexões *após* o container `oracle-db` mostrar a mensagem "DATABASE IS READY TO USE!".

É possível que a aplicação Spring Boot (`gestao-residuos-app`) inicie mais rápido, tente se conectar ao banco, falhe (pois o banco ainda está "acordando") e o container da aplicação pare.

**Se isso acontecer (você verá "Connection refused" nos logs), é normal.**

**Solução:**
1.  Espere o log do `oracle-db` indicar que está pronto.
2.  Em **outro terminal**, simplesmente reinicie o container da aplicação:
    ```bash
    docker-compose restart app-springboot
    ```
    Isso fará com que a aplicação tente se conectar novamente, agora com o banco pronto.

### Parando o Ambiente

Para parar todos os containers, pressione `Ctrl + C` no terminal onde o `docker-compose` está rodando, ou execute em outro terminal:

```bash
docker-compose down
