package br.com.portfolio.buscasemantica.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SalvarMemoriaResponse(
        UUID id,
        String texto,
        LocalDateTime criadoEm
) {}
