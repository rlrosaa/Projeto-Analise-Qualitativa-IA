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

    private String postRequestApi (String url, String bodyRequest, String tokenApi){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenApi);

        HttpEntity<String> entity = new HttpEntity<>(bodyRequest, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public  String postRequestApiGPT4(String bodyRequest){
        var url = Constantes.urlBaseApiGPT;
        var tokenApiGPT4 = Constantes.tokenGPT;

        return postRequestApi(url, bodyRequest, tokenApiGPT4);

    }
}
