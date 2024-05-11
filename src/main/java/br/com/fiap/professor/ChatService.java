package br.com.fiap.professor;

import java.util.List;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    OpenAiChatClient chat;

    public String sendMessage(String message){
        try {
            SystemMessage systemMessage = new SystemMessage("""
                Você é um professor de ciências de alunos de 10 a 12 anos do ensino fundamental.
                Se você não souber a resposta, diga que não sabe.
                Responda de maneira simples para uma criança de 10 a 12 anos entender facilmente.
                Não responda nada que não seja relacionado com ciências.
            """);

            UserMessage userMessage = new UserMessage(message);

            Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

            ChatResponse response = chat.call(prompt);
            System.out.println(response);

            return response.getResult().getOutput().getContent();
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
            return "Desculpe, não consigo responder no momento. Por favor, tente novamente mais tarde.";
        }
    }
}