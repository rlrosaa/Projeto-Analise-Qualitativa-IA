package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
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

    public  String postRequestApiGPT4(String bodyRequest){
        var url = Constantes.URL_BASE_API_GPT;
        var tokenApiGPT = Constantes.TOKEN_GPT;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenApiGPT);

        return postRequestApi(url, bodyRequest, headers);

    }

    public  String postRequestApiGemini(String bodyRequest){
        var url = Constantes.URL_BASE_API_GEMINI + "?key=" + Constantes.TOKEN_GEMINI;

        return postRequestApi(url, bodyRequest, null);

    }

    public String postRequestApiClaude (String bodyRequest){
        var url = Constantes.URL_BASE_API_CLAUDE;
        var tokenClaude = Constantes.TOKEN_CLAUDE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", tokenClaude);
        headers.set("anthropic-version", Constantes.API_VERSION_CLAUDE);

        return postRequestApi(url, bodyRequest, headers);

    }
}
