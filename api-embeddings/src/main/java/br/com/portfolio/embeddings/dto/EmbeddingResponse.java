package br.com.portfolio.embeddings.dto;

public record EmbeddingResponse(
        float[] embedding,
        int dimensoes,
        String modelo
) {}
