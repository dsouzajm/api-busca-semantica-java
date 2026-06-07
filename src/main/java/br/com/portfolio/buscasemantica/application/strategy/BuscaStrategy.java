package br.com.portfolio.buscasemantica.application.strategy;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

import java.util.List;

public interface BuscaStrategy {

    ModoBusca getModoBusca();

    List<ResultadoBusca> buscar(String texto, int topK);
}
