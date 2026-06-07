package br.com.portfolio.buscasemantica.presentation.dto;

public record EmbeddingResponse(
        float[] embedding,
        int dimensoes,
        String modelo
) {}
