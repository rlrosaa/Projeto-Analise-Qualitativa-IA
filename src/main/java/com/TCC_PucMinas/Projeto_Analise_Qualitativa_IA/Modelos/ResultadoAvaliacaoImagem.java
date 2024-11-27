package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoAvaliacaoImagem {
    private String ResultadoAvaliacaoGPT ;
    private String ResultadoAvaliacaoClaude;
    private String ResultadoAvaliacaoLlama;
    private String ResultadoAvaliacaoConsolidado;
}
