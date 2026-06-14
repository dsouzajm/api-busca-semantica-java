package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;

@Component
public class BuscaStrategyFactory {

    private final Map<ModoBusca, BuscaStrategy> strategies;

    public BuscaStrategyFactory(List<BuscaStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(BuscaStrategy::getModoBusca, Function.identity()));
    }

    public BuscaStrategy getStrategy(ModoBusca modoBusca) {
        return Optional.ofNullable(strategies.get(modoBusca))
                .orElseThrow(() -> new UnsupportedOperationException(
                        ("Modo de busca não configurado: %s."
                                + " Verifique as credenciais de IA.")
                                .formatted(modoBusca.getValor())));
    }
}
