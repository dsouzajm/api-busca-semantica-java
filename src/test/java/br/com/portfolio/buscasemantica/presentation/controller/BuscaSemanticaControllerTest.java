package br.com.portfolio.buscasemantica.presentation.controller;

import br.com.portfolio.buscasemantica.domain.port.in.BuscaSemanticaUseCase;
import br.com.portfolio.buscasemantica.domain.valueobject.ModoBusca;
import br.com.portfolio.buscasemantica.domain.valueobject.ResultadoBusca;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuscaSemanticaController.class)
class BuscaSemanticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BuscaSemanticaUseCase buscaSemanticaUseCase;

    @Test
    void deveBuscarPorTextoComSucesso() throws Exception {
        when(buscaSemanticaUseCase.buscar(any(ModoBusca.class), anyString(), anyInt()))
                .thenReturn(List.of(new ResultadoBusca(0.95, "Spring Boot é um framework Java")));

        mockMvc.perform(post("/busca-semanticas-memorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "modoBusca": "texto",
                                    "texto": "Spring Boot",
                                    "topK": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scoreBusca").value(0.95))
                .andExpect(jsonPath("$[0].texto").value("Spring Boot é um framework Java"));
    }

    @Test
    void deveRetornarBadRequestQuandoTextoVazio() throws Exception {
        mockMvc.perform(post("/busca-semanticas-memorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "modoBusca": "texto",
                                    "texto": "",
                                    "topK": 5
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoModoBuscaInvalido() throws Exception {
        mockMvc.perform(post("/busca-semanticas-memorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "modoBusca": "invalido",
                                    "texto": "teste",
                                    "topK": 5
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTopKExcedeLimite() throws Exception {
        mockMvc.perform(post("/busca-semanticas-memorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "modoBusca": "texto",
                                    "texto": "teste",
                                    "topK": 200
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
