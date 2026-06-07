package br.com.portfolio.embeddings.dto;

import jakarta.validation.constraints.NotBlank;

public record EmbeddingRequest(
        @NotBlank(message = "texto é obrigatório")
        String texto
) {}
