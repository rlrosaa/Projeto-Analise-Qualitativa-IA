package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DadosAvaliacaoImagem {

    private String perguntaUsuario;

    private String urlImagem;

    private String respostaApp;

    public DadosAvaliacaoImagem(
            String perguntaUsuario, String urlImagem, String respostaApp) {
        this.perguntaUsuario = perguntaUsuario;
        this.urlImagem = urlImagem;
        this.respostaApp = respostaApp;
    }

    public String criaStringPerguntaEResposta() {
        return "Pergunta do Usuario: %s\nResposta gerada pelo App: %s"
                .formatted(perguntaUsuario,respostaApp);
    }

}