package br.com.portfolio.buscasemantica.domain.port.in;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

import java.util.List;

public interface BuscaSemanticaUseCase {

    List<ResultadoBusca> buscar(ModoBusca modoBusca, String texto, int topK);
}
