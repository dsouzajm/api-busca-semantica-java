package br.com.portfolio.buscasemantica.domain.port.in;

public interface GerarEmbeddingUseCase {

    float[] gerar(String texto);
}
