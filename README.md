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

* **Backend:** Java 17, Spring Boot 3
* **Persistência:** Spring Data JPA (Hibernate), Oracle Database (`gvenzl/oracle-xe:latest`)
* **Segurança:** Spring Security (HTTP Basic Auth, Roles: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_COLETA`)
* **DevOps:** GitHub Actions (CI/CD), Docker & Docker Compose
* **Monitoramento:** Spring Boot Actuator
* **Auxiliares:** Maven, Lombok, Jakarta Validation

---

## 🚀 Como Executar Localmente com o Docker

Este projeto utiliza **Docker Compose** para orquestrar a aplicação e o banco de dados. Siga os passos abaixo:

1.  **Clone o Repositório:**
    ```bash
    git clone <url-do-repositorio>
    cd gestao-residuos
    ```

2.  **Configure as Variáveis de Ambiente:**
    Crie um arquivo `.env` na raiz do projeto baseado no `.env.example`:
    ```bash
    cp .env.example .env
    ```

3.  **Inicie os Containers:**
    ```bash
    docker-compose up --build -d
    ```

4.  **Verifique a Saúde da Aplicação:**
    A aplicação utiliza **Health Checks** para garantir que o Spring Boot só esteja disponível após a conexão completa com o banco Oracle:
    ```bash
    docker ps
    ```
    Aguarde o status mudar para `(healthy)`. A API estará disponível em `http://localhost:8080/api`.

---

## 🐳 Containerização

A aplicação utiliza **Docker** para garantir consistência entre os ambientes de desenvolvimento, staging e produção.

### Estratégias Adotadas
* **Multi-stage Build:** Utilizamos uma etapa de `build` (Maven) e uma de `runtime` (JRE leve) para reduzir o tamanho da imagem final e aumentar a segurança.
* **Redes Isoladas:** App e Banco comunicam-se via uma rede bridge privada (`backend-network`).
* **Resource Limits:** Limites de memória (512MB para a App) configurados para evitar exaustão de recursos.

### Conteúdo do Dockerfile
```dockerfile
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🚀 Pipeline CI/CD

Utilizamos o **GitHub Actions** como ferramenta de DevOps para automatizar o ciclo de vida da aplicação.

### Funcionamento e Etapas
1.  **Integração Contínua (CI):**
    *   **Ferramenta:** GitHub Actions.
    *   **Trigger:** Push ou Pull Request em `main` e `develop`.
    *   **Etapas:** Setup do Java 17, levantamento de um Oracle XE temporário, execução de `mvn clean verify` (compilação e testes) e upload do artefato.
2.  **Entrega Contínua (CD):**
    *   **Ferramenta:** GitHub Actions via SSH.
    *   **Trigger:** Sucesso do pipeline de CI.
    *   **Staging:** Deploy automático na branch `develop`.
    *   **Production:** Deploy automático na branch `main`.
    *   **O que faz:** Conecta via SSH no servidor, atualiza o código, gera o `.env` e reinicia os containers via Docker Compose.

---

## 🖼️ Evidências de Funcionamento

Nesta seção, encontram-se as comprovações técnicas da execução e automação do projeto:

### 1. Orquestração com Docker Compose
Status dos containers rodando localmente, evidenciando o uso de volumes e o estado de saúde (`healthy`) do banco Oracle e da aplicação.
![Status dos Containers](<Captura de tela 2026-03-30 175106.png>)

### 2. Monitoramento de Saúde (Health Check)
Resposta do endpoint `/actuator/health` via navegador, confirmando que a aplicação está "UP" e integrada com o banco de dados.
![Endpoint Health Check](<Captura de tela 2026-03-30 175119.png>)

### 3. Integração Contínua (CI) com GitHub Actions
Pipeline de build e testes automatizados executada com sucesso no GitHub, validando a integridade do código Java/Maven.
![Pipeline de CI com Sucesso](<Captura de tela 2026-03-30 175241.png>)
![Pipeline de CI com Sucesso](<Captura de tela 2026-03-30 182543.png>)

### 4. Entrega Contínua (CD) e Infraestrutura
Registro da tentativa de deploy automático. O pipeline de CD foi configurado corretamente, porém, a conexão SSH final foi interrompida (`i/o timeout`) devido às restrições de firewall/NAT do roteador local, que impede o acesso externo ao ambiente doméstico. Este comportamento é esperado em ambientes de desenvolvimento que não possuem um IP público exposto.
Eu poderia configurar o servidor AWS como foi ensinado, porém, tive limitações no serviço e disseram que aquilo geraria cobranças.
![Log de Deploy via SSH](<Captura de tela 2026-03-30 182727.png>)
![Log de Deploy via SSH](<Captura de tela 2026-03-30 182809.png>)

---

## 🛡️ Segurança e Boas Práticas
* **Secrets:** Todas as credenciais sensíveis (senhas, chaves SSH) são gerenciadas via **GitHub Secrets**.
* **Health Monitoring:** Endpoint `/actuator/health` configurado para monitoramento ativo.

---

## ✅ Checklist de Entrega

- [x] **Containerização:** Dockerfile funcional com multi-stage build.
- [x] **Orquestração:** Docker Compose configurado com redes, volumes e limites de recursos.
- [x] **CI (Integração Contínua):** Pipeline no GitHub Actions realizando build e testes (Maven).
- [x] **CD (Entrega Contínua):** Pipeline no GitHub Actions configurada para deploy via SSH.
- [x] **Segurança:** Gerenciamento de credenciais via GitHub Secrets e .env.
- [x] **Monitoramento:** Health Checks configurados via Spring Actuator e integrados ao Docker.
- [x] **Documentação:** README.md detalhado com instruções de execução e arquitetura DevOps.
- [x] **Evidências:** Prints de funcionamento e execução do pipeline anexados.
