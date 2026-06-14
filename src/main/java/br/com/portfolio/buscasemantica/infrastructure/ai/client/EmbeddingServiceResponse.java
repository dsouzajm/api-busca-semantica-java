package br.com.portfolio.buscasemantica.infrastructure.ai.client;

public record EmbeddingServiceResponse(
        float[] embedding,
        int dimensoes,
        String modelo
) {}
