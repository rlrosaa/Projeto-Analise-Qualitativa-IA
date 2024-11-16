package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.BodyRequestGPT;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;

public class ProcessamentoDadosServico {

    public ProcessamentoDadosServico (){

    }

    public String processaDadosAvaliacao(DadosAvaliacaoImagem dadosAvaliacaoImagem){
        BodyRequestGPT bodyRequestGPT = new BodyRequestGPT(
                dadosAvaliacaoImagem, Enumeradores.ModeloGPT.GPT_4);
        return null;
    }

}
