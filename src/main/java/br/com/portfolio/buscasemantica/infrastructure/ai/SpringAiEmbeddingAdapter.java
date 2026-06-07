package br.com.portfolio.buscasemantica.infrastructure.ai;

import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

@Component
public class SpringAiEmbeddingAdapter implements EmbeddingPort {

    private final EmbeddingModel embeddingModel;

    public SpringAiEmbeddingAdapter(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public float[] gerarEmbedding(String texto) {
        return embeddingModel.embed(texto);
    }
}
