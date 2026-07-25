package br.com.portfolio.buscasemantica.domain.port.in;

import java.util.List;
import java.util.UUID;

import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

public interface BuscaSemanticaUseCase {

    List<ResultadoBusca> buscar(UUID idCliente, ModoBusca modoBusca, String texto, List<Float> embedding, int topK);
}
