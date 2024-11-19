package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores;

import lombok.Getter;

public class Enumeradores {

    @Getter
    public enum ModeloGPT {
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        GPT_4("gpt-4"),
        GPT_4_32K("gpt-4-32k");

        private final String nomeModelo;

        ModeloGPT(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }
    }

    @Getter
    public enum RolesGPT {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");

        private final String valorRole;

        RolesGPT(String nomeModelo) {
            this.valorRole = nomeModelo;
        }
    }

    @Getter
    public enum ModeloClaude {
        CLAUDE_3_5("claude-3-5-sonnet-20241022");

        private final String nomeModelo;

        ModeloClaude(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }
    }

    @Getter
    public enum RolesClaude {
        USER("user"),
        ASSISTANT("assistant");

        private final String valorRole;

        RolesClaude(String nomeModelo) {
            this.valorRole = nomeModelo;
        }
    }

}
