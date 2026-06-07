package br.com.portfolio.buscasemantica.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record EmbeddingRequest(
        @NotBlank(message = "texto é obrigatório")
        String texto
) {}
