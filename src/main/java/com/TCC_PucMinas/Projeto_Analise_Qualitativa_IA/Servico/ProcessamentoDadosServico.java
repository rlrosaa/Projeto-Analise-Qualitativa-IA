package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.BodyRequestClaude;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.BodyRequestGPT;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoDadosServico {

    private final ObjectMapper objectMapper;
    private final ConexaoAPIRepositorio conexaoAPIRepositorio;

    public ProcessamentoDadosServico (ConexaoAPIRepositorio conexaoAPIRepositorio){

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.conexaoAPIRepositorio = conexaoAPIRepositorio;

    }

    public String gerarAvaliacaoQualitativaDadosImagem(DadosAvaliacaoImagem dadosAvaliacaoImagem){

        String resultadoAvaliacaoGPT = null;
        String resultadoAvaliacaoClaude = null;

        try{
            String stringBodyRequestGPT = geraBodyRequestGPT(dadosAvaliacaoImagem);
            resultadoAvaliacaoGPT=
                    conexaoAPIRepositorio.postRequestApiGPT4(stringBodyRequestGPT);

            String stringBodyRequestClaude = gerarBodyRequestClaude(dadosAvaliacaoImagem);
            resultadoAvaliacaoClaude =
                    conexaoAPIRepositorio.postRequestApiClaude(stringBodyRequestClaude);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return resultadoAvaliacaoGPT;
    }

    private String gerarBodyRequestClaude(DadosAvaliacaoImagem dadosAvaliacaoImagem) {

        String stringBodyRequestClaude = null;

        try{
            BodyRequestClaude bodyRequestClaude = new BodyRequestClaude(
                    dadosAvaliacaoImagem, Enumeradores.ModeloClaude.CLAUDE_3_5);

            stringBodyRequestClaude = objectMapper.writeValueAsString(bodyRequestClaude);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stringBodyRequestClaude;

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
