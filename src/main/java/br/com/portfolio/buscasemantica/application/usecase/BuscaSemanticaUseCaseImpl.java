package br.com.portfolio.buscasemantica.application.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.portfolio.buscasemantica.application.strategy.BuscaStrategyFactory;
import br.com.portfolio.buscasemantica.domain.port.in.BuscaSemanticaUseCase;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Service
public class BuscaSemanticaUseCaseImpl implements BuscaSemanticaUseCase {

    private final BuscaStrategyFactory strategyFactory;

    public BuscaSemanticaUseCaseImpl(BuscaStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public List<ResultadoBusca> buscar(UUID idCliente, ModoBusca modoBusca, String texto, int topK) {
        return strategyFactory.getStrategy(modoBusca).buscar(idCliente, texto, topK);
    }
}
