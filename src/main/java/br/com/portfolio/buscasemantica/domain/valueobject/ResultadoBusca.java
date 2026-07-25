package br.com.portfolio.buscasemantica.domain.valueobject;

public record ResultadoBusca(
        double score,
        double similaridade,
        String texto,
        double significancia,
        int recorrencia
) {
    public ResultadoBusca {
        if (similaridade < 0.0 || similaridade > 1.0) {
            throw new IllegalArgumentException("similaridade deve estar entre 0.0 e 1.0");
        }
    }
}