package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.PromptsIA;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyRequestGPT {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private Message[] messages;

    @JsonProperty("temperature")
    private final double TEMPERATURE = 1;

    @JsonProperty("max_tokens")
    private final int MAX_TOKENS = 300;

    @JsonProperty("top_p")
    private final double TOP_P = 1;

    @JsonProperty("frequency_penalty")
    private final double FREQUENCY_PENALTY = 0;

    @JsonProperty("presence_penalty")
    private final double PRESENCE_PENALTY = 0;

    private static class Message {
        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private Content[] content;
    }
    private static class Content {
        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("image_url")
        private String imageUrl;
    }

    public BodyRequestGPT(
            DadosAvaliacaoImagem dadosAvaliacaoImagem, Enumeradores.ModeloGPT modelo) {

        this.criaBodyRequestGPT(dadosAvaliacaoImagem,modelo);

    }

    private void criaBodyRequestGPT(
            DadosAvaliacaoImagem dadosAvaliacaoImagem, Enumeradores.ModeloGPT modelo) {

        this.model = modelo.getNomeModelo();
        this.messages = new Message[2];

        this.messages[0] = new Message();
        this.messages[0].role = Enumeradores.RolesGPT.SYSTEM.getValorRole();
        this.messages[0].content = new Content[1];
        this.messages[0].content[0] = new Content();
        this.messages[0].content[0].type = "text";
        this.messages[0].content[0].text = PromptsIA.PROMPT_AVALIACAO_RESPOSTA;

        this.messages[1] = new Message();
        this.messages[1].role = Enumeradores.RolesGPT.USER.getValorRole();
        this.messages[1].content = new Content[2];
        this.messages[1].content[0] = new Content();
        this.messages[1].content[0].type = "text";
        this.messages[1].content[0].text = dadosAvaliacaoImagem.criaStringPerguntaEResposta();
        this.messages[1].content[1] = new Content();
        this.messages[1].content[1].type = "image_url";
        this.messages[1].content[1].imageUrl = dadosAvaliacaoImagem.getUrlImagem();
    }
}