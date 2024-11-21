package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.PromptsIA;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class BodyRequestCohere {

    @JsonProperty("model")
    private String model;

    @JsonProperty("max_tokens")
    private final int MAX_TOKENS = 300;

    @JsonProperty("messages")
    private Message[] messages;

    public static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;
    }

    public BodyRequestCohere (
            String resultadoAvaliacao1, String resultadoAvaliacao2, String  resultadoAvaliacao3,
            Enumeradores.@NotNull ModeloCohere modelo) {

        String resultadosAvaliacaoAgrupados = geraResultadosAgrupados(
                resultadoAvaliacao1,resultadoAvaliacao2,resultadoAvaliacao3);

        this.model = modelo.getNomeModelo();

        this.messages = new Message[2];

        this.messages[0] = new Message();
        this.messages[0].role = Enumeradores.Role.SYSTEM.getValorRole();
        this.messages[0].content = PromptsIA.PROMPT_CONSOLIDACAO_RESPOSTA_FINAL;

        this.messages[1] = new Message();
        this.messages[1].role = Enumeradores.Role.USER.getValorRole();
        this.messages[1].content = resultadosAvaliacaoAgrupados;

    }

    private String geraResultadosAgrupados(
            String resultadoAvaliacao1, String resultadoAvaliacao2, String resultadoAvaliacao3) {

        return "Avaliação 1: %s \n Avaliação 2: %s \n Avaliação 3: %s"
                .formatted(resultadoAvaliacao1,resultadoAvaliacao2,resultadoAvaliacao3);

    }

}
