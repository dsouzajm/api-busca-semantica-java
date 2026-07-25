package br.com.portfolio.buscasemantica.presentation.dto;

public record BuscaSemanticaResponse(
        double scoreBusca,
        String texto,
        double significancia,
        int recorrencia
) {}
