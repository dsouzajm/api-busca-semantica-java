package br.com.portfolio.buscasemantica.domain.port.out;

public interface EmbeddingPort {

    float[] gerarEmbedding(String texto);
}
