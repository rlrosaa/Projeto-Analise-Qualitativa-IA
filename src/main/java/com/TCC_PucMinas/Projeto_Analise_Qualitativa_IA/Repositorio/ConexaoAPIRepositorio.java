package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;


@Repository
public class ConexaoAPIRepositorio {

    private final RestTemplate restTemplate;

    public ConexaoAPIRepositorio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String postRequestApi (String url, String bodyRequest, HttpHeaders headers){

        HttpEntity<String> entity = new HttpEntity<>(bodyRequest, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public  String postRequestApiGPT(String bodyRequest){
        var url = Constantes.URL_BASE_API_GPT;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + Constantes.TOKEN_GPT);

        return extrairTextoAvaliacaoJsonGPT(postRequestApi(url, bodyRequest, headers));

    }

    public String postRequestApiClaude (String bodyRequest){
        var url = Constantes.URL_BASE_API_CLAUDE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", Constantes.TOKEN_CLAUDE);
        headers.set("anthropic-version", Constantes.API_VERSION_CLAUDE);

        return extrairTextoAvaliacaoJsonClaude(postRequestApi(url, bodyRequest, headers));

    }

    public String postRequestApiLlama (String bodyRequest){

        var url = Constantes.URL_BASE_API_LLAMA;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + Constantes.TOKEN_LLAMA);

        return extrairTextoAvaliacaoJsonLlama(postRequestApi(url, bodyRequest, headers));
    }

    public String postRequestApiCohere (String bodyRequest){

        var url = Constantes.URL_BASE_API_COHERE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + Constantes.TOKEN_COHERE);

        return extrairTextoAvaliacaoJsonCohere(postRequestApi(url, bodyRequest, headers));
    }

    private String extrairTextoAvaliacaoJsonGPT(String jsonRespostaGPT) {

        String resultadoAvaliacaoGPT;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonRespostaGPT);
            JsonNode textNode = rootNode
                    .path("choices").get(0).path("message").path("content");
            resultadoAvaliacaoGPT = textNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultadoAvaliacaoGPT;
    }

    private String extrairTextoAvaliacaoJsonClaude(String jsonRespostaClaude) {

        String resultadoAvaliacaoClaude;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonRespostaClaude);
            JsonNode textNode = rootNode.path("content").get(0).path("text");
            resultadoAvaliacaoClaude = textNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultadoAvaliacaoClaude;
    }

    private String extrairTextoAvaliacaoJsonLlama(String jsonRespostaLlama) {

        String resultadoAvaliacaoLlama;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonRespostaLlama);
            JsonNode textNode = rootNode.path("choices").get(0).path("message").path("content");;
            resultadoAvaliacaoLlama = textNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultadoAvaliacaoLlama;
    }

    private String extrairTextoAvaliacaoJsonCohere(String jsonRespostaCohere) {

        String resultadoAvaliacaoConsolidado;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonRespostaCohere);
            JsonNode textNode = rootNode.path("message").path("content").get(0).path("text");
            resultadoAvaliacaoConsolidado = textNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultadoAvaliacaoConsolidado;
    }
}
