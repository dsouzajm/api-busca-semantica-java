package br.com.portfolio.buscasemantica.application.usecase;

import org.springframework.stereotype.Service;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.port.in.SalvarMemoriaUseCase;
import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;

@Service
public class SalvarMemoriaUseCaseImpl implements SalvarMemoriaUseCase {

    private final EmbeddingPort embeddingPort;
    private final MemoriaRepositoryPort repositoryPort;

    public SalvarMemoriaUseCaseImpl(EmbeddingPort embeddingPort, MemoriaRepositoryPort repositoryPort) {
        this.embeddingPort = embeddingPort;
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Memoria salvar(String texto) {
        float[] embedding = embeddingPort.gerarEmbedding(texto);
        return repositoryPort.salvar(texto, embedding);
    }
}
