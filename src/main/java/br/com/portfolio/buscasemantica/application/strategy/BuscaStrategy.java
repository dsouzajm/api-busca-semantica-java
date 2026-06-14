package br.com.portfolio.buscasemantica.application.strategy;

import java.util.List;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

public interface BuscaStrategy {

    ModoBusca getModoBusca();

    List<ResultadoBusca> buscar(String texto, int topK);
}
