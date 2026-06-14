package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Component
public class BuscaEmbeddingStrategy implements BuscaStrategy {

    private final MemoriaRepositoryPort repositoryPort;
    private final Optional<EmbeddingPort> embeddingPort;

    public BuscaEmbeddingStrategy(MemoriaRepositoryPort repositoryPort,
                                   Optional<EmbeddingPort> embeddingPort) {
        this.repositoryPort = repositoryPort;
        this.embeddingPort = embeddingPort;
    }

    @Override
    public ModoBusca getModoBusca() {
        return ModoBusca.EMBEDDING;
    }

    @Override
    public List<ResultadoBusca> buscar(String texto, int topK) {
        EmbeddingPort port = embeddingPort.orElseThrow(() ->
                new UnsupportedOperationException(
                        "Modo embedding indisponível: configure a variável OPENAI_API_KEY"
                )
        );
        float[] embedding = port.gerarEmbedding(texto);
        return repositoryPort.buscarPorEmbedding(embedding, topK);
    }
}
