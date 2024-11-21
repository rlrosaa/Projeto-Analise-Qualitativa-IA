package com.TCC_PucMinas.Projeto_Analise_Qualitativa_IA.Enumeradores;

import lombok.Getter;

public class Enumeradores {

    @Getter
    public enum Role {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");

        private final String valorRole;

        Role(String valorRole) {
            this.valorRole = valorRole;
        }
    }

    @Getter
    public enum DataType {
        TEXT("text"),
        IMAGE_URL("image_url"),
        IMAGE("image"),
        BASE64("base64");

        private final String dataType;

        DataType(String dataType) {
            this.dataType = dataType;
        }
    }

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
    public enum ModeloClaude {
        CLAUDE_3_5("claude-3-5-sonnet-20241022");

        private final String nomeModelo;

        ModeloClaude(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }
    }

    @Getter
    public enum ModeloLlama {
        LLAMA_3_2_11B_VISION("llama3.2-11b-vision");

        private final String nomeModelo;

        ModeloLlama(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }
    }

    @Getter
    public enum ModeloCohere {
        COHERE_CHAT_V2("command-r-plus-08-2024");

        private final String nomeModelo;

        ModeloCohere(String nomeModelo) {
            this.nomeModelo = nomeModelo;
        }
    }
}
