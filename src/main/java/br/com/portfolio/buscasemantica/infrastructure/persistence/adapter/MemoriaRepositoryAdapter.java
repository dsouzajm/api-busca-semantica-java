package br.com.portfolio.buscasemantica.infrastructure.persistence.adapter;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MemoriaRepositoryAdapter implements MemoriaRepositoryPort {

    private final JdbcClient jdbcClient;

    public MemoriaRepositoryAdapter(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ResultadoBusca> buscarPorTexto(String texto, int topK) {
        return jdbcClient.sql("""
                SELECT texto,
                       GREATEST(0.0, LEAST(1.0, similarity(texto, :query))) AS score
                FROM memorias
                WHERE similarity(texto, :query) > 0.05
                ORDER BY score DESC
                LIMIT :topK
                """)
                .param("query", texto)
                .param("topK", topK)
                .query((rs, rowNum) -> new ResultadoBusca(
                        rs.getDouble("score"),
                        rs.getString("texto")
                ))
                .list();
    }

    @Override
    public List<ResultadoBusca> buscarPorEmbedding(float[] embedding, int topK) {
        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcClient.sql("""
                SELECT texto,
                       GREATEST(0.0, LEAST(1.0, 1 - (embedding <=> :embedding::halfvec))) AS score
                FROM memorias
                WHERE embedding IS NOT NULL
                ORDER BY embedding <=> :embedding::halfvec
                LIMIT :topK
                """)
                .param("embedding", vectorLiteral)
                .param("topK", topK)
                .query((rs, rowNum) -> new ResultadoBusca(
                        rs.getDouble("score"),
                        rs.getString("texto")
                ))
                .list();
    }

    @Override
    public Memoria salvar(String texto, float[] embedding) {
        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcClient.sql("""
                INSERT INTO memorias (texto, embedding)
                VALUES (:texto, :embedding::halfvec)
                RETURNING id, criado_em
                """)
                .param("texto", texto)
                .param("embedding", vectorLiteral)
                .query((rs, rowNum) -> new Memoria(
                        rs.getObject("id", UUID.class),
                        texto,
                        embedding,
                        rs.getObject("criado_em", LocalDateTime.class)
                ))
                .single();
    }

    private String toVectorLiteral(float[] embedding) {
        var sb = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(embedding[i]);
        }
        return sb.append("]").toString();
    }
}
