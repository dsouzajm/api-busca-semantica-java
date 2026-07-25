package br.com.portfolio.buscasemantica.application.usecase;

import org.springframework.stereotype.Service;

import br.com.portfolio.buscasemantica.domain.port.in.GerarEmbeddingUseCase;
import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;

@Service
public class GerarEmbeddingUseCaseImpl implements GerarEmbeddingUseCase {

    private final EmbeddingPort embeddingPort;

    public GerarEmbeddingUseCaseImpl(EmbeddingPort embeddingPort) {
        this.embeddingPort = embeddingPort;
    }

    @Override
    public float[] gerar(String texto) {
        return embeddingPort.gerarEmbedding(texto);
    }
}
