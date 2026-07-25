package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Component
public class BuscaTextoStrategy implements BuscaStrategy {

    private final MemoriaRepositoryPort repositoryPort;
    private final Optional<EmbeddingPort> embeddingPort;

    public BuscaTextoStrategy(MemoriaRepositoryPort repositoryPort, Optional<EmbeddingPort> embeddingPort) {
        this.repositoryPort = repositoryPort;
        this.embeddingPort = embeddingPort;
    }

    @Override
    public ModoBusca getModoBusca() {
        return ModoBusca.TEXTO;
    }

    @Override
    public List<ResultadoBusca> buscar(UUID idCliente, String texto, List<Float> embedding, int topK) {
        EmbeddingPort port = embeddingPort.orElseThrow(() ->
                new UnsupportedOperationException(
                        "Modo texto indisponível: configure a API de embeddings"
                )
        );
        float[] embeddingGerado = port.gerarEmbedding(texto);
        return repositoryPort.buscarPorEmbedding(idCliente, embeddingGerado, topK);
    }
}
