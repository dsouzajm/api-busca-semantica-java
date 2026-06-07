package br.com.portfolio.buscasemantica.domain.port.in;

import br.com.portfolio.buscasemantica.domain.model.Memoria;

public interface SalvarMemoriaUseCase {

    Memoria salvar(String texto);
}
