package br.com.portfolio.buscasemantica.infrastructure.ai;

import br.com.portfolio.buscasemantica.domain.port.out.EmbeddingPort;
import br.com.portfolio.buscasemantica.infrastructure.ai.client.EmbeddingServiceRequest;
import br.com.portfolio.buscasemantica.infrastructure.ai.client.EmbeddingServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class HttpEmbeddingAdapter implements EmbeddingPort {

    private final RestClient restClient;

    public HttpEmbeddingAdapter(
            @Value("${app.embedding-service.url:http://api-embeddings:8080}") String embeddingServiceUrl) {

        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(120));

        this.restClient = RestClient.builder()
                .baseUrl(embeddingServiceUrl)
                .requestFactory(factory)
                .build();
    }

    @Override
    public float[] gerarEmbedding(String texto) {
        EmbeddingServiceResponse response = restClient.post()
                .uri("/embeddings")
                .body(new EmbeddingServiceRequest(texto))
                .retrieve()
                .body(EmbeddingServiceResponse.class);

        return response != null ? response.embedding() : new float[0];
    }
}
