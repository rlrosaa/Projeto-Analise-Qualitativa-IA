package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores.Enumeradores;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.*;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ProcessamentoDadosImagemServico {

    private final ObjectMapper objectMapper;
    private final ConexaoAPIRepositorio conexaoAPIRepositorio;

    public ProcessamentoDadosImagemServico(ConexaoAPIRepositorio conexaoAPIRepositorio){

        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.conexaoAPIRepositorio = conexaoAPIRepositorio;

    }

    public ResultadoAvaliacaoImagem gerarAvaliacaoQualitativaDadosImagem(DadosAvaliacaoImagem dadosAvaliacaoImagem) throws Exception {

        var resultadoAvaliacaoImagem = new ResultadoAvaliacaoImagem();

        try{
            String stringBodyRequestGPT = geraBodyRequestGPT(dadosAvaliacaoImagem);
            resultadoAvaliacaoImagem.setResultadoAvaliacaoGPT(
                    conexaoAPIRepositorio.postRequestApiGPT(stringBodyRequestGPT));

            String stringBodyRequestClaude = gerarBodyRequestClaude(dadosAvaliacaoImagem);
            resultadoAvaliacaoImagem.setResultadoAvaliacaoClaude(
                    conexaoAPIRepositorio.postRequestApiClaude(stringBodyRequestClaude));

            String stringBodyRequestLlama = gerarBodyRequestLlama(dadosAvaliacaoImagem);
            resultadoAvaliacaoImagem.setResultadoAvaliacaoLlama(
                    conexaoAPIRepositorio.postRequestApiLlama(stringBodyRequestLlama));

            String stringBodyRequestCohere = gerarBodyRequestCohere(resultadoAvaliacaoImagem);
            resultadoAvaliacaoImagem.setResultadoAvaliacaoConsolidado(
                    conexaoAPIRepositorio.postRequestApiCohere(stringBodyRequestCohere));
        }
        catch (HttpClientErrorException e){
            throw new HttpClientErrorException(e.getStatusCode(), "Erro a acessar APIs externas: " + e.getMessage());
        }
        catch(HttpServerErrorException e){
            e.printStackTrace();
        }
        catch(Exception e){
            throw new Exception(e);
        }

        return resultadoAvaliacaoImagem;
    }

    private String gerarBodyRequestCohere(ResultadoAvaliacaoImagem resultadoAvaliacaoImagem) throws Exception {

        String stringBodyRequestCohere = null;

        try{
            BodyRequestCohere bodyRequestCohere = new BodyRequestCohere(
                    resultadoAvaliacaoImagem,Enumeradores.ModeloCohere.COHERE_CHAT_V2);

            stringBodyRequestCohere = objectMapper.writeValueAsString(bodyRequestCohere);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stringBodyRequestCohere;
    }

    private String gerarBodyRequestLlama(DadosAvaliacaoImagem dadosAvaliacaoImagem) {

        String stringBodyRequestLlama = null;

        try{
            BodyRequestLlama bodyRequestLlama = new BodyRequestLlama(
                    dadosAvaliacaoImagem,Enumeradores.ModeloLlama.LLAMA_3_2_11B_VISION);

            stringBodyRequestLlama = objectMapper.writeValueAsString(bodyRequestLlama);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stringBodyRequestLlama;

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
                    dadosAvaliacaoImagem, Enumeradores.ModeloGPT.GPT_4_MINI);

            stringBodyRequestGPT = objectMapper.writeValueAsString(bodyRequestGPT);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return stringBodyRequestGPT;
    }

}
