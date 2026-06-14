package br.com.portfolio.buscasemantica.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ModoBusca {

    TEXTO("texto"),
    EMBEDDING("embedding");

    private final String valor;

    ModoBusca(String valor) {
        this.valor = valor;
    }

    @JsonCreator
    public static ModoBusca fromString(String value) {
        for (ModoBusca modo : values()) {
            if (modo.valor.equalsIgnoreCase(value)) {
                return modo;
            }
        }
        String msg = "Modo de busca inválido: '%s'."
                + " Valores aceitos: texto, embedding";
        throw new IllegalArgumentException(msg.formatted(value));
    }

    @JsonValue
    public String getValor() {
        return valor;
    }
}