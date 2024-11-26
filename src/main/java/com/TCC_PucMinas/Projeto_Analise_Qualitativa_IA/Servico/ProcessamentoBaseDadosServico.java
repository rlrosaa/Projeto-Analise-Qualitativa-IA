package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Servico;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosAvaliacaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Modelos.DadosExtracaoImagem;
import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Utils.Constantes;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProcessamentoBaseDadosServico {

    public static List<DadosAvaliacaoImagem> gerarListaDadosAvaliacaoImagem (
            String diretorioBaseDados){

        var listaDadosAvaliacaoImagem = new ArrayList<DadosAvaliacaoImagem>();

        try{
            var listaDadosExtracaoImagem = extrairDadosPlanilha(diretorioBaseDados);

            listaDadosExtracaoImagem.forEach(dado -> {
                var dadosAvaliacaoImagem = new DadosAvaliacaoImagem(
                        dado.getPergunta(), 
                        null,
                        gerarImagemBase64(diretorioBaseDados, dado.getNomeImagem()),
                        dado.getResposta()
                );
                listaDadosAvaliacaoImagem.add(dadosAvaliacaoImagem);
            } );

        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return listaDadosAvaliacaoImagem;

    }

    public static void gravarAvaliacoesQualitativasPlanilha(
            String diretorioBaseDados, List<String> listaAvaliacaoQualitativaImagem){

        String diretorioArquivo = diretorioBaseDados + File.separator + Constantes.NOME_ARQUIVO_PLANILHA;

        try (FileInputStream fileInputStream = new FileInputStream(diretorioArquivo);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(Constantes.NOME_ABA_PLANILHA);

            int indiceLinha = 0;
            for (String dado : listaAvaliacaoQualitativaImagem) {
                Row row = sheet.getRow(indiceLinha);

                if (row == null) {
                    row = sheet.createRow(indiceLinha);
                }

                row.createCell(6).setCellValue(dado);
                indiceLinha++;
            }

            try (FileOutputStream fileOut = new FileOutputStream(diretorioArquivo)) {
                workbook.write(fileOut);
                System.out.println("Coluna atualizada com sucesso em: " + diretorioArquivo);
            }

        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo Excel: " + e.getMessage());
        }
    }

    private static String gerarImagemBase64(String diretorioArquivo, String nomeImagem) {
        File file = new File(
        diretorioArquivo + File.separator + Constantes.DIRETORIO_IMAGENS
                    + File.separator + nomeImagem);

        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte[] imageBytes = new byte[(int) file.length()];
            imageInFile.read(imageBytes);

            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<DadosExtracaoImagem> extrairDadosPlanilha(String diretorioBaseDados) throws IOException {

        String diretorioArquivo = diretorioBaseDados + File.separator + Constantes.NOME_ARQUIVO_PLANILHA;

        FileInputStream arquivoPlanilha = new FileInputStream(diretorioArquivo);
        Workbook workbook = new XSSFWorkbook(arquivoPlanilha);

        List<DadosExtracaoImagem> listaDadosPlanilha = new ArrayList<>();

        Sheet sheet = workbook.getSheet(Constantes.NOME_ABA_PLANILHA);

        int numeroLinhas = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < numeroLinhas; i++) {
            Row row = sheet.getRow(i);

            if (row != null) {
                Cell pergunta = row.getCell(1);
                Cell resposta = row.getCell(4);
                Cell nomeImagem = row.getCell(5);

                var dadosExtracaoImagem = new DadosExtracaoImagem();
                dadosExtracaoImagem.setPergunta(getCellValueAsString(pergunta));
                dadosExtracaoImagem.setResposta(getCellValueAsString(resposta));
                dadosExtracaoImagem.setNomeImagem(getCellValueAsString(nomeImagem));

                listaDadosPlanilha.add(dadosExtracaoImagem);

            }
        }

        workbook.close();
        arquivoPlanilha.close();

        return listaDadosPlanilha;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}