package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Repositorio.ConexaoAPIRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConexaoAPIRepositorioTests {

    @Autowired
    private ConexaoAPIRepositorio conexaoAPIRepositorio;

    @Test
    void testPostRequestApiGPT() {
        try{
            String resposta = conexaoAPIRepositorio.postRequestApiGPT("");
            System.out.println(resposta);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    void testPostRequestApiClaude() {
        try{
            String resposta = conexaoAPIRepositorio.postRequestApiClaude("");
            System.out.println(resposta);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}