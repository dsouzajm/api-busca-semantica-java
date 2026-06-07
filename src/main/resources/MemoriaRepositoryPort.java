package br.com.portfolio.buscasemantica.domain.port.out;

import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

import java.util.List;

public interface MemoriaRepositoryPort {

    List<ResultadoBusca> buscarPorTexto(String texto, int topK);

    List<ResultadoBusca> buscarPorEmbedding(float[] embedding, int topK);
}