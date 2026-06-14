package br.com.portfolio.buscasemantica.domain.port.in;

import java.util.List;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

public interface BuscaSemanticaUseCase {

    List<ResultadoBusca> buscar(ModoBusca modoBusca, String texto, int topK);
}
