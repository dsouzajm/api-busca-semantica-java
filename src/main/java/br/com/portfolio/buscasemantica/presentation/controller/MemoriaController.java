package br.com.portfolio.buscasemantica.presentation.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.port.in.SalvarMemoriaUseCase;
import br.com.portfolio.buscasemantica.presentation.dto.SalvarMemoriaRequest;
import br.com.portfolio.buscasemantica.presentation.dto.SalvarMemoriaResponse;

@RestController
@RequestMapping("/memorias")
public class MemoriaController {

    private final SalvarMemoriaUseCase salvarMemoriaUseCase;

    public MemoriaController(SalvarMemoriaUseCase salvarMemoriaUseCase) {
        this.salvarMemoriaUseCase = salvarMemoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<SalvarMemoriaResponse> salvar(@Valid @RequestBody SalvarMemoriaRequest request) {
        Memoria memoria = salvarMemoriaUseCase.salvar(request.texto());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SalvarMemoriaResponse(memoria.id(), memoria.texto(), memoria.criadoEm()));
    }
}
