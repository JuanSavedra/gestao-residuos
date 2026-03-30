# Roadmap DevOps - Gestão de Resíduos

Este documento detalha as etapas para a implementação completa da cultura de Entrega Contínua (CD) e Infraestrutura como Código (IaC) no projeto.

## 1. Containerização e Orquestração 🐳
- [x] Criar Dockerfile funcional (Multi-stage build). *(Já existente, mas passível de otimização)*
- [x] Criar docker-compose.yml para orquestração local. *(Já existente)*
- [ ] Refatorar `docker-compose.yml` para suportar variáveis de ambiente externas.
- [ ] Criar arquivo `.env.example` com as variáveis necessárias.
- [ ] Configurar redes (networks) isoladas no Docker Compose para segurança.
- [ ] Validar persistência de dados (volumes) para o banco Oracle.

## 2. CI/CD com GitHub Actions 🚀
- [ ] **Workflow de CI (Integração Contínua):**
    - [ ] Criar `.github/workflows/ci.yml`.
    - [ ] Etapa: Checkout do código.
    - [ ] Etapa: Configuração do Java 17.
    - [ ] Etapa: Build e Testes Unitários (`mvn clean verify`).
    - [ ] Etapa: Upload de artefatos (JAR) ou Imagem Docker.
- [ ] **Workflow de CD (Entrega Contínua):**
    - [ ] Configurar Secrets no GitHub (SSH_KEY, HOST, DB_CREDENTIALS).
    - [ ] Etapa: Deploy para ambiente de **Staging** (triggered por push em `develop`).
    - [ ] Etapa: Deploy para ambiente de **Produção** (triggered por push em `main`).
    - [ ] Implementar Rollback automático em caso de falha no deploy.

## 3. Segurança e Boas Práticas 🔐
- [ ] Garantir que segredos não sejam expostos nos logs do pipeline.
- [ ] Configurar limites de memória e CPU no Docker Compose.
- [ ] Implementar Health Checks robustos para garantir que o deploy só finalize quando a app estiver 100% pronta.

## 4. Documentação e Entrega 📖
- [ ] Atualizar o README.md com as instruções de deploy e variáveis de ambiente.
- [ ] Finalizar entrega técnica.
