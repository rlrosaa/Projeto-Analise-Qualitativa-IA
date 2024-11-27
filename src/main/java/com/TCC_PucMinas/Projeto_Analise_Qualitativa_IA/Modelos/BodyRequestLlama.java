package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.PromptsIA;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BodyRequestLlama {

    @JsonProperty("model")
    private String model;

    @JsonProperty("max_token")
    private int MAX_TOKENS = 300;

    @JsonProperty("messages")
    private Message[] messages;

    private static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private Content[] content;

        private Message(int tamanhoArrayContent){
            content = new Content[tamanhoArrayContent];
            for (int i = 0; i < tamanhoArrayContent; i++){
                content[i] = new Content();
            }
        }

    }

    private static class Content {

        @JsonProperty("type")
        private String type;

        @JsonProperty("text")
        private String text;

        @JsonProperty("image_url")
        private ImageUrl imageUrl;

    }

    public static class ImageUrl {
        @JsonProperty("url")
        private String url;
    }

    public BodyRequestLlama (
            DadosAvaliacaoImagem dadosAvaliacaoImagem, Enumeradores.ModeloLlama modelo) {

        this.model = modelo.getNomeModelo();

        this.messages = new Message [2];

        this.messages[0] = new Message(1);
        this.messages[0].role = Enumeradores.Role.SYSTEM.getValorRole();
        this.messages[0].content[0].type = "text";
        this.messages[0].content[0].text = PromptsIA.PROMPT_AVALIACAO_RESPOSTA;

        this.messages[1] = new Message(2);
        this.messages[1].role = Enumeradores.Role.USER.getValorRole();
        this.messages[1].content[0].type = Enumeradores.DataType.TEXT.getDataType();
        this.messages[1].content[0].text = dadosAvaliacaoImagem.criaStringPerguntaEResposta();
        this.messages[1].content[1].type = Enumeradores.DataType.IMAGE_URL.getDataType();
        this.messages[1].content[1].imageUrl = new ImageUrl();
        this.messages[1].content[1].imageUrl.url = "data:%s;base64,%s"
                .formatted(Constantes.MEDIA_TYPE_IMAGEM,dadosAvaliacaoImagem.getDadosImagem());
    }
}
