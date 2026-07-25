package br.com.portfolio.buscasemantica.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import br.com.portfolio.buscasemantica.domain.model.Memoria;
import br.com.portfolio.buscasemantica.domain.port.out.MemoriaRepositoryPort;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;

@Component
public class MemoriaRepositoryAdapter implements MemoriaRepositoryPort {

    private final JdbcClient jdbcClient;

    public MemoriaRepositoryAdapter(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ResultadoBusca> buscarPorEmbedding(UUID idCliente, float[] embedding, int topK) {
        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcClient.sql("""
                SELECT texto,
                       1 - (embedding <=> :embedding::halfvec) / 2 AS similaridade,
                       significancia,
                       recorrencia
                FROM memorias
                WHERE id_cliente = :idCliente
                  AND embedding IS NOT NULL
                ORDER BY embedding <=> :embedding::halfvec
                LIMIT :topK
                """)
                .param("idCliente", idCliente)
                .param("embedding", vectorLiteral)
                .param("topK", topK)
                .query((rs, rowNum) -> new ResultadoBusca(
                        0.0,
                        rs.getDouble("similaridade"),
                        rs.getString("texto"),
                        rs.getDouble("significancia"),
                        rs.getInt("recorrencia")
                ))
                .list();
    }

    @Override
    public Memoria salvar(String texto, float[] embedding) {
        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcClient.sql("""
                INSERT INTO memorias (texto, embedding)
                VALUES (:texto, :embedding::halfvec)
                RETURNING id, criado_em, significancia, recorrencia
                """)
                .param("texto", texto)
                .param("embedding", vectorLiteral)
                .query((rs, rowNum) -> new Memoria(
                        rs.getObject("id", UUID.class),
                        texto,
                        embedding,
                        rs.getObject("criado_em", LocalDateTime.class),
                        rs.getDouble("significancia"),
                        rs.getInt("recorrencia")
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
