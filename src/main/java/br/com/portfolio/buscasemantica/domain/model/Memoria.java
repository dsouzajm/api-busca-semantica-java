package br.com.portfolio.buscasemantica.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Memoria(
        UUID id,
        String texto,
        float[] embedding,
        LocalDateTime criadoEm
) {}