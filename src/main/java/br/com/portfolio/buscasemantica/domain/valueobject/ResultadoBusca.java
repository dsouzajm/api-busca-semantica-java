package br.com.portfolio.buscasemantica.domain.valueobject;

public record ResultadoBusca(
        double scoreBusca,
        String texto,
        double significancia,
        int recorrencia
) {
    public ResultadoBusca {
        if (scoreBusca < 0.0 || scoreBusca > 1.0) {
            throw new IllegalArgumentException("scoreBusca deve estar entre 0.0 e 1.0");
        }
    }
}