package br.com.portfolio.buscasemantica.presentation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;

public record BuscaSemanticaRequest(
        @NotNull(message = "modoBusca é obrigatório")
        ModoBusca modoBusca,

        @NotBlank(message = "texto é obrigatório")
        String texto,

        @Min(value = 1, message = "topK deve ser no mínimo 1")
        @Max(value = 100, message = "topK deve ser no máximo 100")
        int topK
) {}
