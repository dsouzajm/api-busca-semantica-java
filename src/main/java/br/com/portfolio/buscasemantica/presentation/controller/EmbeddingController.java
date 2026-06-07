package br.com.portfolio.buscasemantica.presentation.controller;

import br.com.portfolio.buscasemantica.domain.port.in.GerarEmbeddingUseCase;
import br.com.portfolio.buscasemantica.presentation.dto.EmbeddingRequest;
import br.com.portfolio.buscasemantica.presentation.dto.EmbeddingResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/embeddings")
public class EmbeddingController {

    private final GerarEmbeddingUseCase gerarEmbeddingUseCase;

    @Value("${spring.ai.ollama.embedding.model:llama3.2:1b}")
    private String modelo;

    public EmbeddingController(GerarEmbeddingUseCase gerarEmbeddingUseCase) {
        this.gerarEmbeddingUseCase = gerarEmbeddingUseCase;
    }

    @PostMapping
    public ResponseEntity<EmbeddingResponse> gerar(@Valid @RequestBody EmbeddingRequest request) {
        float[] embedding = gerarEmbeddingUseCase.gerar(request.texto());
        return ResponseEntity.ok(new EmbeddingResponse(embedding, embedding.length, modelo));
    }
}
