package br.com.lucca.screenmatch.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class ChatGPTApi {
    public static String obterTraducao(String texto) {
        try {
            OpenAiService service = new OpenAiService("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");
            ChatMessage mensagem = new ChatMessage("user", "traduza para o português o texto: " + texto);

            ChatCompletionRequest requisicao = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(mensagem))
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createChatCompletion(requisicao);
            return resposta.getChoices().get(0).getMessage().getContent();
        } catch (OpenAiHttpException e) {
            System.err.println("Erro ao comunicar com a API: " + e.getMessage());
            return "Erro ao obter tradução";
        }
    }
}