package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.PromptsIA;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyRequestClaude {

    @JsonProperty("model")
    private String model;

    @JsonProperty("max_tokens")
    private int MAX_TOKENS = 300;

    @JsonProperty("system")
    private String system;

    @JsonProperty("messages")
    private Message[] messages;

    private static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private Content[] content;

        private Message (DadosAvaliacaoImagem dadosAvaliacaoImagem){
            this.role = Enumeradores.RolesClaude.USER.getValorRole();
            this.content = new Content[2];

            this.content[0] = new Content();
            this.content[0].type = "text";
            this.content[0].text = dadosAvaliacaoImagem.criaStringPerguntaEResposta();

            this.content[1] = new Content();
            this.content[1].type = "image";
            this.content[1].source = new Source();
            this.content[1].source.type = "base64";
            this.content[1].source.mediaType = Constantes.MEDIA_TYPE_IMAGEM;
            this.content[1].source.data = dadosAvaliacaoImagem.getDadosImagem();
        }
    }

    private static class Content {

        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("source")
        private Source source;

    }

    private static class Source {

        @JsonProperty("type")
        private String type;

        @JsonProperty("media_type")
        private String mediaType;

        @JsonProperty("data")
        private String data;
    }

    public BodyRequestClaude(
            DadosAvaliacaoImagem dadosAvaliacaoImagem,
            @org.jetbrains.annotations.NotNull Enumeradores.ModeloClaude modelo
    )
    {
        this.model = modelo.getNomeModelo();
        this.system = PromptsIA.PROMPT_AVALIACAO_RESPOSTA;
        this.messages = new Message[1];
        this.messages[0] = new Message(dadosAvaliacaoImagem);
    }

}
