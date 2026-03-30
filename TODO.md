# Roadmap DevOps - Gestão de Resíduos

Este documento detalha as etapas para a implementação completa da cultura de Entrega Contínua (CD) e Infraestrutura como Código (IaC) no projeto.

## 1. Containerização e Orquestração 🐳
- [x] Criar Dockerfile funcional (Multi-stage build).
- [x] Criar docker-compose.yml para orquestração local.
- [x] Refatorar `docker-compose.yml` para suportar variáveis de ambiente externas.
- [x] Criar arquivo `.env.example` com as variáveis necessárias.
- [x] Configurar redes (networks) isoladas no Docker Compose para segurança.
- [x] Validar persistência de dados (volumes) para o banco Oracle.

## 2. CI/CD com GitHub Actions 🚀
- [x] **Workflow de CI (Integração Contínua):**
    - [x] Criar `.github/workflows/ci.yml`.
    - [x] Etapa: Checkout do código.
    - [x] Etapa: Configuração do Java 17.
    - [x] Etapa: Build e Testes Unitários (`mvn clean verify`).
    - [x] Etapa: Upload de artefatos (JAR) ou Imagem Docker.
- [x] **Workflow de CD (Entrega Contínua):**
    - [x] Configurar Secrets no GitHub (SSH_KEY, HOST, DB_CREDENTIALS).
    - [x] Etapa: Deploy para ambiente de **Staging** (triggered por push em `develop`).
    - [x] Etapa: Deploy para ambiente de **Produção** (triggered por push em `main`).
    - [ ] Implementar Rollback automático em caso de falha no deploy.

## 3. Segurança e Boas Práticas 🔐
- [x] Garantir que segredos não sejam expostos nos logs do pipeline.
- [x] Configurar limites de memória e CPU no Docker Compose.
- [x] Implementar Health Checks robustos para garantir que o deploy só finalize quando a app estiver 100% pronta.

## 4. Documentação e Entrega 📖
- [x] Atualizar o README.md com as instruções de deploy e variáveis de ambiente.
- [x] Finalizar entrega técnica.
