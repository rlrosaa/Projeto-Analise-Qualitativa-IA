package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Funcao;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.ResultadoAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico.ProcessamentoDadosImagemServico;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

import static com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico.ProcessamentoBaseDadosServico.gerarListaDadosAvaliacaoImagem;
import static com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico.ProcessamentoBaseDadosServico.gravarAvaliacoesQualitativasPlanilha;

@Component
public class ProcessamentoDadosImagemFuncao {

    private final ProcessamentoDadosImagemServico processamentoDadosImagemServico;

    public ProcessamentoDadosImagemFuncao (ProcessamentoDadosImagemServico processamentoDadosImagemServico) {

        this.processamentoDadosImagemServico = processamentoDadosImagemServico;
    }

    public void gerarAnaliseQualitativa() throws  Exception {

        var listaAvaliacaoQualitativaImagem = new ArrayList<ResultadoAvaliacaoImagem>();
        ResultadoAvaliacaoImagem avaliacaoQualitativa;

        var listaDadosAvaliacaoImagem =
                gerarListaDadosAvaliacaoImagem(Constantes.DIRETORIO_BASE_DADOS);

        for (var dados : listaDadosAvaliacaoImagem) {
            avaliacaoQualitativa = processamentoDadosImagemServico.gerarAvaliacaoQualitativaDadosImagem(dados);
            listaAvaliacaoQualitativaImagem.add(avaliacaoQualitativa);
        }

        gravarAvaliacoesQualitativasPlanilha(
                Constantes.DIRETORIO_BASE_DADOS ,listaAvaliacaoQualitativaImagem);

    }
}
