<div align="center">

# API Busca Semântica

**API REST de busca semântica em memórias usando PGVector, Spring AI e Java 25**

[![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/25/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL_17-PGVector-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://github.com/pgvector/pgvector)
[![Spring AI](https://img.shields.io/badge/Spring_AI-1.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docs.docker.com/compose/)

</div>

---

## Sobre o Projeto

Esta API demonstra busca semântica em coleções de texto usando dois modos distintos:

- **Modo Texto** — busca por similaridade textual usando trigramas do PostgreSQL (`pg_trgm`), ideal para correspondências exatas e parciais
- **Modo Embedding** — converte o texto em vetor semântico via OpenAI e realiza busca por proximidade vetorial usando PGVector (cosine similarity), capturando o **significado** da consulta

A aplicação roda com **duas instâncias redundantes** atrás de um **Nginx como load balancer**, conectadas ao mesmo banco PGVector.

---

## Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                        Nginx :80                            │
│                  (Least Connections LB)                     │
└───────────────────────┬─────────────────────────────────────┘
                        │
          ┌─────────────┴─────────────┐
          │                           │
   ┌──────▼──────┐             ┌──────▼──────┐
   │   API #1    │             │   API #2    │
   │  :8080      │             │  :8080      │
   └──────┬──────┘             └──────┬──────┘
          └─────────────┬─────────────┘
                        │
               ┌────────▼────────┐
               │  PostgreSQL 17  │
               │  + PGVector     │
               │  :5432          │
               └─────────────────┘
```

### Clean Architecture

```
┌────────────────────────────────────────────────────────────┐
│  presentation/   Controllers · DTOs · Exception Handlers   │
├────────────────────────────────────────────────────────────┤
│  application/    Use Cases · Strategy Pattern              │
├────────────────────────────────────────────────────────────┤
│  domain/         Entities · Value Objects · Ports          │
├────────────────────────────────────────────────────────────┤
│  infrastructure/ JDBC Adapter · Spring AI · Config         │
└────────────────────────────────────────────────────────────┘
         Dependências apontam sempre para dentro ↑
```

---

## Design Patterns

| Pattern | Onde | Propósito |
|---------|------|-----------|
| **Strategy** | `BuscaTextoStrategy`, `BuscaEmbeddingStrategy` | Algoritmos de busca intercambiáveis |
| **Factory** | `BuscaStrategyFactory` | Seleciona strategy em runtime via `ModoBusca` |
| **Port & Adapter** | `MemoriaRepositoryPort`, `EmbeddingPort` | Inversão de dependência (SOLID) |
| **Record** | `ResultadoBusca`, `Memoria`, DTOs | Value Objects imutáveis (Java 25) |

---

## Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Linguagem | Java 25 LTS com Virtual Threads |
| Framework | Spring Boot 3.5.14 |
| IA / Embeddings | Spring AI 1.1.7 + Ollama (`llama3.2:1b` — 100% local) |
| Banco de Dados | PostgreSQL 17 + PGVector |
| Busca Textual | `pg_trgm` (trigram similarity) |
| Busca Vetorial | PGVector HNSW index (cosine similarity) |
| Migração DB | Flyway |
| Load Balancer | Nginx (least_conn) |
| Contêineres | Docker + Docker Compose |
| Testes | JUnit 5 + MockMvc |

---

## Endpoint

### `POST /busca-semanticas-memorias`

**Request:**
```json
{
    "modoBusca": "texto",
    "texto": "busca por significado semântico",
    "topK": 5
}
```

| Campo | Tipo | Valores | Descrição |
|-------|------|---------|-----------|
| `modoBusca` | string | `"texto"` \| `"embedding"` | Algoritmo de busca |
| `texto` | string | qualquer | Consulta a ser buscada |
| `topK` | int | 1–100 | Número máximo de resultados |

**Response (`200 OK`):**
```json
[
    {
        "scoreBusca": 0.924,
        "texto": "Busca semântica permite encontrar informações por significado..."
    },
    {
        "scoreBusca": 0.871,
        "texto": "PGVector é uma extensão do PostgreSQL que adiciona suporte a vetores..."
    }
]
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `scoreBusca` | decimal (0–1) | Pontuação de similaridade |
| `texto` | string | Conteúdo da memória encontrada |

**Erros:**
```json
// 400 Bad Request
{ "title": "Requisição inválida", "detail": "modoBusca é obrigatório" }

// 422 Unprocessable Entity
{ "title": "Operação não suportada", "detail": "Modo de busca não configurado: embedding" }
```

---

## Como Executar

### Pré-requisitos

- Docker e Docker Compose
- (Opcional) Chave de API da OpenAI para modo `embedding`

### 1. Configurar variáveis de ambiente

```bash
cp .env.example .env
```

Editar `.env`:
```env
DB_USER=postgres
DB_PASSWORD=minha_senha_segura
```

> **Sem dependências externas** — o Ollama com Llama 3.2 sobe junto no Docker Compose.
> Na primeira execução o modelo `llama3.2:1b` (~1.3 GB) é baixado automaticamente.

### 2. Subir com Docker Compose

```bash
docker compose up --build
```

A API ficará disponível em `http://localhost:80`.

### 3. Testar

```bash
# Busca por texto (sem OpenAI)
curl -X POST http://localhost/busca-semanticas-memorias \
  -H "Content-Type: application/json" \
  -d '{"modoBusca": "texto", "texto": "Spring Boot", "topK": 3}'

# Busca por embedding (requer OpenAI)
curl -X POST http://localhost/busca-semanticas-memorias \
  -H "Content-Type: application/json" \
  -d '{"modoBusca": "embedding", "texto": "framework para construir APIs", "topK": 3}'
```

---

## Desenvolvimento Local

```bash
# 1. Subir apenas o banco de dados
docker compose up postgres -d

# 2. Exportar variáveis
export OPENAI_API_KEY=sk-proj-...

# 3. Rodar a aplicação
mvn spring-boot:run

# 4. Rodar os testes
mvn test
```

---

## Adicionar Memórias ao Banco

```sql
-- Conectar ao banco
psql -h localhost -U postgres -d buscasemantica

-- Inserir memórias de texto (embedding preenchido via OpenAI separadamente)
INSERT INTO memorias (texto) VALUES 
('Seu conteúdo de memória aqui'),
('Outra memória para busca semântica');
```

---

## Virtual Threads

Virtual Threads (Project Loom, GA desde Java 21) são habilitadas globalmente via:

```yaml
# application.yml
spring:
  threads:
    virtual:
      enabled: true
```

Isso configura automaticamente:
- **Tomcat** para aceitar requisições em virtual threads
- **Spring MVC** para despachar handlers em virtual threads
- Alta concorrência com baixíssimo uso de memória (sem thread pool fixo)

---

## Estrutura do Projeto

```
src/main/java/br/com/portfolio/buscasemantica/
├── BuscaSemanticaApplication.java
├── domain/
│   ├── model/Memoria.java
│   ├── valueobject/
│   │   ├── ModoBusca.java
│   │   └── ResultadoBusca.java
│   └── port/
│       ├── in/BuscaSemanticaUseCase.java
│       └── out/
│           ├── MemoriaRepositoryPort.java
│           └── EmbeddingPort.java
├── application/
│   ├── service/BuscaSemanticaService.java
│   └── strategy/
│       ├── BuscaStrategy.java
│       ├── BuscaTextoStrategy.java
│       ├── BuscaEmbeddingStrategy.java
│       └── BuscaStrategyFactory.java
├── infrastructure/
│   ├── persistence/adapter/MemoriaRepositoryAdapter.java
│   ├── ai/SpringAiEmbeddingAdapter.java
│   └── config/AppConfig.java
└── presentation/
    ├── controller/BuscaSemanticaController.java
    ├── dto/
    │   ├── BuscaSemanticaRequest.java
    │   └── BuscaSemanticaResponse.java
    └── exception/GlobalExceptionHandler.java
```

---

## Sobre o Autor

Projeto desenvolvido como portfólio pessoal demonstrando:
- Arquitetura limpa e escalável em Java
- Integração com IA generativa (OpenAI + Spring AI)
- Busca semântica com banco vetorial (PGVector)
- Infraestrutura containerizada com redundância e load balancing
