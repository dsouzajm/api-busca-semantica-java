package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Component
public class BuscaTextoStrategy implements BuscaStrategy {

    private final MemoriaRepositoryPort repositoryPort;

    public BuscaTextoStrategy(MemoriaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public ModoBusca getModoBusca() {
        return ModoBusca.TEXTO;
    }

    @Override
    public List<ResultadoBusca> buscar(String texto, int topK) {
        return repositoryPort.buscarPorTexto(texto, topK);
    }
}
