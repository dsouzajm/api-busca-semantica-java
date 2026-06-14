package br.com.portfolio.buscasemantica.domain.port.out;

import java.util.List;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

public interface MemoriaRepositoryPort {

    List<ResultadoBusca> buscarPorTexto(String texto, int topK);

    List<ResultadoBusca> buscarPorEmbedding(float[] embedding, int topK);

    Memoria salvar(String texto, float[] embedding);
}
