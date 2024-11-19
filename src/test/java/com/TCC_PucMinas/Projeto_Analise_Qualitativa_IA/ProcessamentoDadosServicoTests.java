package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico.ProcessamentoDadosServico;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessamentoDadosServicoTest {

    @Mock
    private ConexaoAPIRepositorio conexaoAPIRepositorio;

    @InjectMocks
    private ProcessamentoDadosServico processamentoDadosServico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
    }

    @Test
    void gerarAvaliacaoQualitativaDadosImagem_deveChamarConexaoAPIRepositorio() {

        String mockStringBodyRequestGPT= "{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"system\",\"content\":[{\"type\":\"text\",\"text\":\"\"}]},{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"Pergunta do Usuario: O que há na imagem?\\nResposta gerada pelo App: Passaros\"},{\"type\":\"image_url\",\"image_url\":\"url/test\"}]}],\"temperature\":1.0,\"max_tokens\":300,\"top_p\":1.0,\"frequency_penalty\":0.0,\"presence_penalty\":0.0}";
        String mockStringBodyRequestClaude= "{\"model\":\"claude-3-5-sonnet-20241022\",\"max_tokens\":300,\"system\":\"\",\"messages\":[{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"Pergunta do Usuario: O que há na imagem?\\nResposta gerada pelo App: Passaros\"},{\"type\":\"image\",\"source\":{\"type\":\"base64\",\"media_type\":\"image/jpeg\",\"data\":\"imagemEmBase64\"}}]}]}";

        DadosAvaliacaoImagem dadosAvaliacaoImagem =
                new DadosAvaliacaoImagem(
                        "O que há na imagem?",
                        "url/test",
                        "imagemEmBase64",
                        "Passaros"
                );

        String mockResponse = "Resultado esperado da API GPT";

        try {
            when(conexaoAPIRepositorio.postRequestApiGPT4(anyString()))
                    .thenReturn(mockResponse);
            when(conexaoAPIRepositorio.postRequestApiClaude(anyString()))
                    .thenReturn(mockResponse);
        } catch (Exception e) {
            fail("Exception inesperada: " + e.getMessage());
        }

        String resultado = processamentoDadosServico
                .gerarAvaliacaoQualitativaDadosImagem(dadosAvaliacaoImagem);

        assertNotNull(resultado, "O resultado não deve ser nulo");
        try {
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiGPT4(mockStringBodyRequestGPT);
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiClaude(mockStringBodyRequestClaude);
        } catch (Exception e) {
            fail("Exception inesperada ao verificar o repositório: " + e.getMessage());
        }
    }
}
