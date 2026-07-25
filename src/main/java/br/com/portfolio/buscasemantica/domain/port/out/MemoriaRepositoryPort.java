package br.com.portfolio.buscasemantica.domain.port.out;

import java.util.List;
import java.util.UUID;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

public interface MemoriaRepositoryPort {

    List<ResultadoBusca> buscarPorTexto(UUID idCliente, String texto, int topK);

    List<ResultadoBusca> buscarPorEmbedding(UUID idCliente, float[] embedding, int topK);

    Memoria salvar(String texto, float[] embedding);
}
