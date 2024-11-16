package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.BodyRequestGPT;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoDadosServico {

    private final ObjectMapper objectMapper;
    private final ConexaoAPIRepositorio conexaoAPIRepositorio;

    public ProcessamentoDadosServico (ConexaoAPIRepositorio conexaoAPIRepositorio){

        objectMapper = new ObjectMapper();
        this.conexaoAPIRepositorio = conexaoAPIRepositorio;

    }

    public String gerarAvaliacaoQualitativaDadosImagem(DadosAvaliacaoImagem dadosAvaliacaoImagem){

        String resultadoAvaliacaoGPT = null;

        try{
            String stringBodyRequestGPT = geraBodyRequestGPT(dadosAvaliacaoImagem);
            resultadoAvaliacaoGPT=
                    conexaoAPIRepositorio.postRequestApiGPT4(stringBodyRequestGPT);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return resultadoAvaliacaoGPT;
    }

    private String geraBodyRequestGPT(DadosAvaliacaoImagem dadosAvaliacaoImagem) {

        String stringBodyRequestGPT = null;

        try{
            BodyRequestGPT bodyRequestGPT = new BodyRequestGPT(
                    dadosAvaliacaoImagem, Enumeradores.ModeloGPT.GPT_4);

            stringBodyRequestGPT = objectMapper.writeValueAsString(bodyRequestGPT);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stringBodyRequestGPT;
    }

}
