package br.com.portfolio.buscasemantica.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.portfolio.buscasemantica.application.strategy.BuscaStrategyFactory;
import br.com.portfolio.buscasemantica.domain.port.in.BuscaSemanticaUseCase;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Service
public class BuscaSemanticaService implements BuscaSemanticaUseCase {

    private final BuscaStrategyFactory strategyFactory;

    public BuscaSemanticaService(BuscaStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public List<ResultadoBusca> buscar(ModoBusca modoBusca, String texto, int topK) {
        return strategyFactory.getStrategy(modoBusca).buscar(texto, topK);
    }
}
