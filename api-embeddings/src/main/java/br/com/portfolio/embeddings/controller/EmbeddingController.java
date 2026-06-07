package br.com.portfolio.embeddings.controller;

import br.com.portfolio.embeddings.dto.EmbeddingRequest;
import br.com.portfolio.embeddings.dto.EmbeddingResponse;
import br.com.portfolio.embeddings.service.EmbeddingService;
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

    private final EmbeddingService embeddingService;

    @Value("${spring.ai.ollama.embedding.model:llama3.2:1b}")
    private String modelo;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping
    public ResponseEntity<EmbeddingResponse> gerar(@Valid @RequestBody EmbeddingRequest request) {
        float[] embedding = embeddingService.gerar(request.texto());
        return ResponseEntity.ok(new EmbeddingResponse(embedding, embedding.length, modelo));
    }
}
