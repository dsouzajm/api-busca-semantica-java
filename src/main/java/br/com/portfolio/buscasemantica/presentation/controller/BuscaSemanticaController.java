package br.com.portfolio.buscasemantica.presentation.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.buscasemantica.domain.port.in.BuscaSemanticaUseCase;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;
import br.com.portfolio.buscasemantica.presentation.dto.BuscaSemanticaRequest;
import br.com.portfolio.buscasemantica.presentation.dto.BuscaSemanticaResponse;

@RestController
@RequestMapping("/{idCliente}/buscas-semanticas")
public class BuscaSemanticaController {

    private final BuscaSemanticaUseCase buscaSemanticaUseCase;

    public BuscaSemanticaController(BuscaSemanticaUseCase buscaSemanticaUseCase) {
        this.buscaSemanticaUseCase = buscaSemanticaUseCase;
    }

    @PostMapping
    public ResponseEntity<List<BuscaSemanticaResponse>> buscar(
            @PathVariable UUID idCliente,
            @Valid @RequestBody BuscaSemanticaRequest request) {

        List<ResultadoBusca> resultados = buscaSemanticaUseCase.buscar(
                idCliente,
                request.modoBusca(),
                request.texto(),
                request.embedding(),
                request.topK()
        );

        List<BuscaSemanticaResponse> response = resultados.stream()
                .map(r -> new BuscaSemanticaResponse(r.scoreBusca(), r.texto(), r.significancia(), r.recorrencia()))
                .toList();

        return ResponseEntity.ok(response);
    }
}
