package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
public class ProjetoAnaliseQualitativaIaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAnaliseQualitativaIaApplication.class, args);
	}
}

@Component
class ConsoleInputHandler implements CommandLineRunner {

	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite um comando ou texto:");

		while (scanner.hasNextLine()) {
			String input = scanner.nextLine();
			handleInput(input);

			if ("sair".equalsIgnoreCase(input)) {
				System.out.println("Encerrando aplicação...");
				break;
			}

			System.out.println("Digite outro comando ou texto:");
		}

		scanner.close();
	}

	private void handleInput(String input) {
		// Aqui você pode adicionar lógica para processar a entrada
		System.out.println("Você digitou: " + input);
	}
}
