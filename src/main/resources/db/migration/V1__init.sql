-- Extensões necessárias
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Tabela de memórias com suporte a busca semântica por vetor e texto
CREATE TABLE IF NOT EXISTS memorias
(
    id        UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    texto     TEXT        NOT NULL,
    embedding vector(1536),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Índice HNSW para busca por embedding (cosine similarity) - melhor performance que IVFFlat em datasets menores
CREATE INDEX IF NOT EXISTS idx_memorias_embedding_hnsw
    ON memorias USING hnsw (embedding vector_cosine_ops)
    WITH (m = 16, ef_construction = 64);

-- Índice GIN para busca por similaridade de texto (trigrama)
CREATE INDEX IF NOT EXISTS idx_memorias_texto_trgm
    ON memorias USING gin (texto gin_trgm_ops);

-- Dados de exemplo para demonstração
INSERT INTO memorias (texto, embedding)
VALUES ('Spring Boot é um framework Java para criação de APIs REST de forma rápida e produtiva.', NULL),
       ('PGVector é uma extensão do PostgreSQL que adiciona suporte a vetores de alta dimensão.', NULL),
       ('Busca semântica permite encontrar informações por significado, não apenas por palavras-chave.', NULL),
       ('Virtual threads do Java 25 permitem alta concorrência com baixo consumo de memória.', NULL),
       ('Clean Architecture separa as responsabilidades em camadas bem definidas e independentes.', NULL),
       ('O padrão Strategy permite selecionar algoritmos em tempo de execução de forma transparente.', NULL),
       ('Docker Compose facilita orquestração de múltiplos contêineres em ambiente de desenvolvimento.', NULL),
       ('Nginx atua como load balancer distribuindo requisições entre instâncias da aplicação.', NULL),
       ('OpenAI embeddings transformam texto em vetores numéricos capturando significado semântico.', NULL),
       ('SOLID é um conjunto de princípios de design que tornam o software mais fácil de manter.', NULL);
