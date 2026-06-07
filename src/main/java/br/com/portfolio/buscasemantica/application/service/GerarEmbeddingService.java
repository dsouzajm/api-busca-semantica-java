package br.com.portfolio.buscasemantica.application.service;

import br.com.portfolio.buscasemantica.domain.port.in.GerarEmbeddingUseCase;
import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import org.springframework.stereotype.Service;

@Service
public class GerarEmbeddingService implements GerarEmbeddingUseCase {

    private final EmbeddingPort embeddingPort;

    public GerarEmbeddingService(EmbeddingPort embeddingPort) {
        this.embeddingPort = embeddingPort;
    }

    @Override
    public float[] gerar(String texto) {
        return embeddingPort.gerarEmbedding(texto);
    }
}
