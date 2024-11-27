package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.ResultadoAvaliacaoImagem;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico.ProcessamentoDadosImagemServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessamentoDadosServicoTest {

    @Spy
    private ConexaoAPIRepositorio conexaoAPIRepositorio = spy(new ConexaoAPIRepositorio(new RestTemplate()));

    @InjectMocks
    private ProcessamentoDadosImagemServico processamentoDadosServico = new ProcessamentoDadosImagemServico(conexaoAPIRepositorio);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void gerarAvaliacaoQualitativaDadosImagem_deveChamarConexaoAPIRepositorio() throws Exception {

        String mockStringBodyRequestGPT= "{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"system\",\"content\":[{\"type\":\"text\",\"text\":\"\"}]},{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"Pergunta do Usuario: O que há na imagem?\\nResposta gerada pelo App: Passaros\"},{\"type\":\"image_url\",\"image_url\":\"url/test\"}]}],\"temperature\":1.0,\"max_tokens\":300,\"top_p\":1.0,\"frequency_penalty\":0.0,\"presence_penalty\":0.0}";
        String mockStringBodyRequestClaude= "{\"model\":\"claude-3-5-sonnet-20241022\",\"max_tokens\":300,\"system\":\"\",\"messages\":[{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"Pergunta do Usuario: O que há na imagem?\\nResposta gerada pelo App: Passaros\"},{\"type\":\"image\",\"source\":{\"type\":\"base64\",\"media_type\":\"image/jpeg\",\"data\":\"imagemEmBase64\"}}]}]}";
        String mockStringBodyRequestLlama= "{\"model\":\"llama3.2-11b-vision\",\"max_token\":300,\"messages\":[{\"role\":\"system\",\"content\":[{\"type\":\"text\",\"text\":\"\"}]},{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"Pergunta do Usuario: O que há na imagem?\\nResposta gerada pelo App: Passaros\"},{\"type\":\"image_url\",\"image_url\":{\"url\":\"url/test\"}}]}]}";
        String mockStringBodyRequestCohere = "{\"model\":\"command-r-plus-08-2024\",\"max_tokens\":300,\"messages\":[{\"role\":\"system\",\"content\":\"\"},{\"role\":\"user\",\"content\":\"Avaliação 1: Resultado esperado da API \\n Avaliação 2: Resultado esperado da API \\n Avaliação 3: Resultado esperado da API\"}]}";

        DadosAvaliacaoImagem dadosAvaliacaoImagem =
                new DadosAvaliacaoImagem(
                        "Qual a cor da lampada que aparece na imagem?",
                        "https://miro.medium.com/v2/resize:fit:720/format:webp/1*SdXRP8f2Lhin89Tht_GRIA.jpeg",
                        "imagemEmBase64",
                        "A lampada é roxa, fazendo uma alusão a cor do mês de novembro."
                );

        String mockResponse = "Não consegui avaliar.";
        String mockResponseConsolidado = "Resultado Consolidado esperado da API";

        try {
            doReturn(mockResponse).when(conexaoAPIRepositorio).postRequestApiGPT(anyString());
            doReturn(mockResponse).when(conexaoAPIRepositorio).postRequestApiClaude(anyString());
            // doReturn(mockResponse).when(conexaoAPIRepositorio).postRequestApiLlama(anyString());
            // doReturn(mockResponseConsolidado).when(conexaoAPIRepositorio).postRequestApiCohere(anyString());
        } catch (Exception e) {
            fail("Exception inesperada: " + e.getMessage());
        }

        ResultadoAvaliacaoImagem resultado = processamentoDadosServico
                .gerarAvaliacaoQualitativaDadosImagem(dadosAvaliacaoImagem);

        assertNotNull(resultado, "O resultado não deve ser nulo");
        try {
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiGPT(mockStringBodyRequestGPT);
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiClaude(mockStringBodyRequestClaude);
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiLlama(mockStringBodyRequestLlama);
            verify(conexaoAPIRepositorio, times(1))
                    .postRequestApiCohere(mockStringBodyRequestCohere);
        } catch (Exception e) {
            fail("Exception inesperada ao verificar o repositório: " + e.getMessage());
        }
    }
}
