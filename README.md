# Projeto: Microserviço de Gestão de Resíduos e Reciclagem

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-success.svg)
![Docker](https://img.shields.io/badge/Docker-blue.svg)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazon-aws&logoColor=white)

Este é um microserviço RESTful desenvolvido em Spring Boot como parte de uma atividade acadêmica. O projeto implementa um sistema de gerenciamento de resíduos, focando no rastreamento, alertas e segurança, utilizando Docker, Spring Security, PostgreSQL e infraestrutura AWS.

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
* **Banco de Dados:** Conexão e persistência em um banco de dados PostgreSQL (AWS RDS), com criação automática de tabelas (via JPA/Hibernate).
* **Containerização (Requisito Mandatório):** Aplicação 100% containerizada com Docker e Docker Compose.
* **Inicialização de Dados:** O sistema insere dados de teste (usuários e tipos de resíduos) ao iniciar (`DataInitializer.java`).

---

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3
* **Persistência:** Spring Data JPA (Hibernate), PostgreSQL, AWS RDS (Relational Database Service)
* **Infraestrutura:** AWS EC2 (Elastic Compute Cloud), Docker & Docker Compose
* **Segurança:** Spring Security (HTTP Basic Auth, Roles: `ROLE_USER`, `ROLE_ADMIN`, `ROLE_COLETA`), AWS Security Groups
* **DevOps:** GitHub Actions (CI/CD)
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
    A aplicação utiliza **Health Checks** para garantir que o Spring Boot só esteja disponível após a conexão completa com o banco PostgreSQL:
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
* **Resource Limits:** Limites de memória configurados para garantir estabilidade em instâncias AWS Free Tier.

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

## 🚀 Pipeline CI/CD (AWS Cloud)

Utilizamos o **GitHub Actions** para automatizar o ciclo de vida da aplicação com foco em infraestrutura cloud.

### Funcionamento e Etapas
1.  **Integração Contínua (CI):**
    *   **Ferramenta:** GitHub Actions.
    *   **Trigger:** Push ou Pull Request em `main` e `develop`.
    *   **Etapas:** Setup do Java 17, levantamento de um PostgreSQL temporário via Docker, execução de `mvn clean verify` (compilação e testes) e upload do artefato.
2.  **Entrega Contínua (CD):**
    *   **Ferramenta:** GitHub Actions via SSH para AWS EC2.
    *   **Trigger:** Sucesso do pipeline de CI.
    *   **Infraestrutura:** O deploy é realizado em uma instância **EC2 (Ubuntu/Amazon Linux)** integrada a um banco **RDS PostgreSQL**.
    *   **O que faz:** Conecta via SSH no servidor AWS, realiza o `git pull`, atualiza as variáveis de ambiente baseadas nos **GitHub Secrets** e reinicia os containers via Docker Compose.

---

## 🖼️ Evidências de Funcionamento

Nesta seção, encontram-se as comprovações técnicas da execução e automação do projeto na nuvem:
![Visão Geral](<img/Captura de tela 2026-04-10 192720.png>)

### 1. Orquestração com Docker Compose
Status dos containers rodando localmente, evidenciando o uso de volumes e o estado de saúde (`healthy`) do banco PostgreSQL e da aplicação.
![Status dos Containers](<img/Captura de tela 2026-03-30 175106.png>)

### 2. Monitoramento de Saúde (Health Check)
Resposta do endpoint `/actuator/health` via navegador, confirmando que a aplicação está "UP" e integrada com o banco de dados RDS.
![Endpoint Health Check](<img/Captura de tela 2026-03-30 175119.png>)

### 3. Integração Contínua (CI) com GitHub Actions
Pipeline de build e testes automatizados executada com sucesso no GitHub, validando a integridade do código Java/Maven.
![Pipeline de CI com Sucesso](<img/Captura de tela 2026-03-30 175241.png>)
![Pipeline de CI com Sucesso](<img/Captura de tela 2026-03-30 182543.png>)

### 4. Entrega Contínua (CD) e Infraestrutura AWS
Registro do deploy automático realizado com sucesso na AWS. Diferente das versões anteriores, a infraestrutura foi migrada para **AWS EC2** e **AWS RDS**, resolvendo os problemas de conectividade e garantindo alta disponibilidade dentro do Free Tier.
![Log de Deploy via SSH AWS](<img/Captura de tela 2026-04-10 192457.png>)
![Log de Deploy via SSH AWS](<img/Captura de tela 2026-04-10 192733.png>)

---

## 🛡️ Segurança e Boas Práticas
* **Secrets:** Todas as credenciais sensíveis (senhas RDS, chaves SSH AWS) são gerenciadas via **GitHub Secrets**.
* **Infraestrutura:** Uso de **Security Groups** da AWS para restringir o acesso apenas às portas necessárias (22 para SSH e 8080 para a API).
* **Health Monitoring:** Endpoint `/actuator/health` configurado para monitoramento ativo.

---

## ✅ Checklist de Entrega

- [x] **Containerização:** Dockerfile funcional com multi-stage build.
- [x] **Orquestração:** Docker Compose configurado com redes, volumes e limites de recursos.
- [x] **CI (Integração Contínua):** Pipeline no GitHub Actions realizando build e testes (Maven).
- [x] **CD (Entrega Contínua):** Pipeline no GitHub Actions configurada para deploy via SSH pela AWS.
- [x] **Segurança:** Gerenciamento de credenciais via GitHub Secrets e .env.
- [x] **Monitoramento:** Health Checks configurados via Spring Actuator e integrados ao Docker.
- [x] **Documentação:** README.md detalhado com instruções de execução e arquitetura DevOps.
- [x] **Evidências:** Prints de funcionamento e execução do pipeline anexados.
- [x] **Testes Automatizados:** Implementação de testes de aceitação e de API com BDD (Gherkin/Cucumber) e RestAssured.

---

## 🧪 Testes Automatizados (BDD com Gherkin)

O projeto utiliza **Cucumber** e **RestAssured** para testes de aceitação e de API. Os testes validam:
*   Status codes.
*   Corpo das respostas (JSON).
*   Contratos da API (JSON Schema).
*   Regras de segurança (Basic Auth e Roles).

### Como rodar os testes localmente:

1.  Certifique-se de que o banco de dados está rodando (`docker-compose up -d`).
2.  No terminal, execute:
    ```bash
    mvn test -Dtest=CucumberTestRunner
    ```

Os cenários de teste estão localizados em `src/test/resources/features` e as implementações em `src/test/java/com/example/gestaoresiduos/cucumber/steps`.

