package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Component
public class BuscaEmbeddingStrategy implements BuscaStrategy {

    private final MemoriaRepositoryPort repositoryPort;

    public BuscaEmbeddingStrategy(MemoriaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public ModoBusca getModoBusca() {
        return ModoBusca.EMBEDDING;
    }

    @Override
    public List<ResultadoBusca> buscar(UUID idCliente, String texto, List<Float> embedding, int topK) {
        return repositoryPort.buscarPorEmbedding(idCliente, toFloatArray(embedding), topK);
    }

    private float[] toFloatArray(List<Float> embedding) {
        float[] array = new float[embedding.size()];
        for (int i = 0; i < embedding.size(); i++) {
            array[i] = embedding.get(i);
        }
        return array;
    }
}
