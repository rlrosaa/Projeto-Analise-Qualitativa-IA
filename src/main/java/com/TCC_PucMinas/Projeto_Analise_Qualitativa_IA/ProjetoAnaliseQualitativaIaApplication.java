package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA;

import com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Funcao.ProcessamentoDadosImagemFuncao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
public class ProjetoAnaliseQualitativaIaApplication implements CommandLineRunner {

	private final ProcessamentoDadosImagemFuncao processamentoDadosImagemFuncao;

	@Autowired
	public ProjetoAnaliseQualitativaIaApplication(
			ProcessamentoDadosImagemFuncao processamentoDadosImagemFuncao) {
		this.processamentoDadosImagemFuncao = processamentoDadosImagemFuncao;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAnaliseQualitativaIaApplication.class, args);
	}

	public void run(String... args) {

		Scanner scanner = new Scanner(System.in);

		try {

			System.out.println("\nAtenção: É importante que os arquivos para avaliação estejam no diretorio correto!" +
					"\nDeseja executar a avaliação qualitativa? Y/N");

			String inputUsuario = scanner.nextLine();

			while (!"N".equalsIgnoreCase(inputUsuario)) {

				if ("Y".equalsIgnoreCase(inputUsuario)) {
					System.out.println("Executando aplicação...");
					processamentoDadosImagemFuncao.gerarAnaliseQualitativa();
					System.out.println("Execução da aplicação concluida.");
					break;
				} else {
					System.out.println("Insira um valor válido: (S/N)");
				}
				inputUsuario = scanner.nextLine();
			}
		} catch (Exception e) {
			System.out.println("Erro gerado: " + e.getMessage());
		}

		System.out.println("Pressione tecla Enter para finalizar...");
		scanner.nextLine();
		System.exit(0);
	}

}