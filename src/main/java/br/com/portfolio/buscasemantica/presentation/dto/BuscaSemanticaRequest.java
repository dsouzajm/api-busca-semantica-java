package br.com.portfolio.buscasemantica.presentation.dto;

import java.util.List;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;

public record BuscaSemanticaRequest(
        @NotNull(message = "modoBusca é obrigatório")
        ModoBusca modoBusca,

        String texto,

        List<Float> embedding,

        @Min(value = 1, message = "topK deve ser no mínimo 1")
        @Max(value = 100, message = "topK deve ser no máximo 100")
        int topK
) {

    @AssertTrue(message = "texto é obrigatório quando modoBusca for texto")
    public boolean isTextoValido() {
        return modoBusca != ModoBusca.TEXTO || (texto != null && !texto.isBlank());
    }

    @AssertTrue(message = "embedding é obrigatório quando modoBusca for embedding")
    public boolean isEmbeddingValido() {
        return modoBusca != ModoBusca.EMBEDDING || (embedding != null && !embedding.isEmpty());
    }
}
