package br.com.portfolio.buscasemantica.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record SalvarMemoriaRequest(
        @NotBlank(message = "texto é obrigatório")
        String texto
) {}
