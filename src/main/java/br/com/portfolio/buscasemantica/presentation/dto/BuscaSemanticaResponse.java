package br.com.portfolio.buscasemantica.presentation.dto;

public record BuscaSemanticaResponse(
        double score,
        double similaridade,
        String texto,
        double significancia,
        int recorrencia
) {}
