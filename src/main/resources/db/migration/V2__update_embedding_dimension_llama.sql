-- Migra o embedding de vector(1536) OpenAI para halfvec(2048) Llama 3.2 1B.
--
-- Por que halfvec e não vector?
-- PGVector limita índices HNSW a 2000 dimensões para o tipo vector.
-- O tipo halfvec (float16) suporta HNSW até 4000 dimensões,
-- permitindo indexar os 2048 dims do Llama 3.2 1B sem perda funcional relevante.

DROP INDEX IF EXISTS idx_memorias_embedding_hnsw;

-- Converte a coluna para halfvec(2048); zera os valores pois
-- embeddings OpenAI (1536d) não são compatíveis com Llama (2048d).
ALTER TABLE memorias
    ALTER COLUMN embedding TYPE halfvec(2048) USING NULL;

CREATE INDEX idx_memorias_embedding_hnsw
    ON memorias USING hnsw (embedding halfvec_cosine_ops)
    WITH (m = 16, ef_construction = 64);
